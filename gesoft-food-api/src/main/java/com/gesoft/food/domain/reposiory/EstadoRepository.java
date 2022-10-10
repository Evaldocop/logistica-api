package com.gesoft.food.domain.reposiory;

import java.util.List;

import com.gesoft.food.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);
	
}
