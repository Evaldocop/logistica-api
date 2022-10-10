package com.gesoft.food.domain.reposiory;

import java.util.List;

import com.gesoft.food.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Restaurante salvarAtualizar(Restaurante restaurante);
	Restaurante buscarPorId(Long id);
	void remover(Restaurante restaurante);

}
