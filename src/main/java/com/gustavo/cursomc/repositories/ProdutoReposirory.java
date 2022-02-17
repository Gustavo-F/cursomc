package com.gustavo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.cursomc.domain.Produto;

public interface ProdutoReposirory extends JpaRepository<Produto, Integer> {

}
