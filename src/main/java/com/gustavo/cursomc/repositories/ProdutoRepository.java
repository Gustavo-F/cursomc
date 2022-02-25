package com.gustavo.cursomc.repositories;

import java.util.List;

import com.gustavo.cursomc.domain.Categoria;
import com.gustavo.cursomc.domain.Produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    public Page<Produto> findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(String nome, List<Categoria> categorias,
            Pageable pageRequest);

}
