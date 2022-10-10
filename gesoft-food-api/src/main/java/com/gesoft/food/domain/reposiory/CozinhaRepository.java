package com.gesoft.food.domain.reposiory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gesoft.food.domain.model.Cozinha;

@Component
public class CozinhaRepository  {
	
	
	@PersistenceContext
	private  EntityManager manager;
	
	public List<Cozinha> listar(){
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Transactional
	public Cozinha adicionarAtualizar(Cozinha cozinha) {
		//insere ou atualiza se já existir uma pk correspondente
		return manager.merge(cozinha);
	}
	
	public Cozinha buscarPorId(Long id) {
		return manager.find(Cozinha.class, id);
	}
	@Transactional
	public void remover(Cozinha cozinha) {
		cozinha = buscarPorId(cozinha.getId()); 
		manager.remove(cozinha);
	}
}
