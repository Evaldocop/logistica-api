package com.gesoft.food.domain.reposiory;

import java.util.List;

import com.gesoft.food.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	Cozinha salvarAtualizar(Cozinha cozinha);
	Cozinha buscarPorId(Long id);
	void remover(Long cozinhaId);

}
