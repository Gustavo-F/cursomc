package com.gustavo.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gustavo.cursomc.domain.Categoria;
import com.gustavo.cursomc.dto.CategoriaDTO;
import com.gustavo.cursomc.repositories.CategoriaRepository;
import com.gustavo.cursomc.services.exceptions.DataIntegrityException;
import com.gustavo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> findAll() {
        return repo.findAll();
    }

	public Categoria find(Integer id) {
		Optional<Categoria> categoriaObj = repo.findById(id);
		
		return categoriaObj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()
		));
	}

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
		return repo.save(categoria);
    }

	public Categoria update(Categoria categoria) {
        Categoria newCategoria = find(categoria.getId());
		updateData(newCategoria, categoria);
		
		return repo.save(newCategoria);
    }

    public void delete(Integer id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
    }
    
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	return repo.findAll(pageRequest);
    }
    
    public Categoria fromDTO(CategoriaDTO categoriaDTO) {
    	return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());	
    }

	private void updateData(Categoria newCategoria, Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}
}























