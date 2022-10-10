package com.gesoft.food.domain.reposiory;


import java.util.List;

import com.gesoft.food.domain.model.Permissao;



public interface PermissaoRepository {

	List<Permissao> listar();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);
	
}