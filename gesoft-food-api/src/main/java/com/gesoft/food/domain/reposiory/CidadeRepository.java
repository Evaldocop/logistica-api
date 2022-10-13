package com.gesoft.food.domain.reposiory;

import java.util.List;

import com.gesoft.food.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listar();
	Cidade buscarPorId(Long id);
	Cidade salvarAtualizar(Cidade cidade);
	void remover(Long estadoId);
	
}
