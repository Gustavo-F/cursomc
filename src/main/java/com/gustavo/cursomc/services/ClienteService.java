package com.gustavo.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.awt.image.BufferedImage;

import com.gustavo.cursomc.domain.Cidade;
import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.domain.Endereco;
import com.gustavo.cursomc.domain.enums.Perfil;
import com.gustavo.cursomc.domain.enums.TipoCliente;
import com.gustavo.cursomc.dto.ClienteDTO;
import com.gustavo.cursomc.dto.ClienteNewDTO;
import com.gustavo.cursomc.repositories.ClienteRepository;
import com.gustavo.cursomc.repositories.EnderecoRepository;
import com.gustavo.cursomc.security.UserSS;
import com.gustavo.cursomc.services.exceptions.AuthorizationException;
import com.gustavo.cursomc.services.exceptions.DataIntegrityException;
import com.gustavo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	public List<Cliente> findAll() {
		return repo.findAll();
	}

    public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId()))
			throw new AuthorizationException("Acesso negado");

		Optional<Cliente> clienteObj = repo.findById(id);
		
		return clienteObj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
		));
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	return repo.findAll(pageRequest);
    }

	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		
		cliente = repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());

		return repo.save(cliente);
	}

	public Cliente update(Cliente cliente) {
        Cliente newCliente = find(cliente.getId());
		updateData(newCliente, cliente);
		
		return repo.save(newCliente);
    }

    public void delete(Integer id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados.");
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
    }
    
    public Cliente fromDTO(ClienteDTO clienteDTO) {
    	return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
    }
    
    public Cliente fromDTO(ClienteNewDTO clienteDTO) {
    	Cliente cliente = new Cliente(
			null, clienteDTO.getNome(),
			clienteDTO.getEmail(),
			pe.encode(clienteDTO.getSenha()),
			clienteDTO.getCpfOuCnpj(),
			TipoCliente.toEnum(clienteDTO.getTipo()));

		Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(
			null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(),
			clienteDTO.getBairro(), clienteDTO.getCep(), cliente, cidade);

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteDTO.getTelefone1());
		
		if (clienteDTO.getTelefone2() != null) 
			cliente.getTelefones().add(clienteDTO.getTelefone2());
		
		if (clienteDTO.getTelefone3() != null)
			cliente.getTelefones().add(clienteDTO.getTelefone3());

		return cliente;
    }

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();

		if (user == null)
			throw new AuthorizationException("Acesso negado");

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = (prefix + user.getId() + ".jpg"); 

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
