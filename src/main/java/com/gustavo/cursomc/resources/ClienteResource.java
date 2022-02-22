package com.gustavo.cursomc.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.dto.ClienteDTO;
import com.gustavo.cursomc.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping(value="/{id}")
    public ResponseEntity<Cliente> find(@PathVariable("id") Integer id) {
        Cliente cliente = clienteService.find(id);
		return ResponseEntity.ok().body(cliente);
    }

    @GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> categorias = clienteService.findAll();
		List<ClienteDTO> listDto = categorias.stream().map(c -> new ClienteDTO(c)).toList();

		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
	) {
		Page<Cliente> categorias = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> categoriasDTO = categorias.map(c -> new ClienteDTO(c));
		
		return ResponseEntity.ok().body(categoriasDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO ClienteDTO, @PathVariable("id") Integer id) {
		Cliente cliente = clienteService.fromDTO(ClienteDTO);
		
		cliente.setId(id);
		cliente = clienteService.update(cliente);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}
