package com.gustavo.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavo.cursomc.domain.Categoria;
import com.gustavo.cursomc.repositories.CategoriaRepository;
import com.gustavo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoriaObj = repo.findById(id);
		
		return categoriaObj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()
		));
	}

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
		return repo.save(categoria);
    }
}
