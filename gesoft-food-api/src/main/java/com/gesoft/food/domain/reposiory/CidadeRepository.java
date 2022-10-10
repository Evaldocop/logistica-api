package com.gesoft.food.domain.reposiory;

import java.util.List;

import com.gesoft.food.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listar();
	Cidade buscar(Long id);
	Cidade salvar(Cidade cidade);
	void remover(Cidade cidade);
	
}
