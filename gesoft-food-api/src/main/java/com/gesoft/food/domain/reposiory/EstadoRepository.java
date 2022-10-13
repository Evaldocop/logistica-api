package com.gesoft.food.domain.reposiory;

import java.util.List;

import com.gesoft.food.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado buscarPorId(Long id);
	Estado salvarAtualizar(Estado estado);
	void remover(Long estadoId);
	
}
