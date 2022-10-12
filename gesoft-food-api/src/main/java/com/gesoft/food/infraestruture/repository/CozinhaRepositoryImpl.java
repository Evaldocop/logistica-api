package com.gesoft.food.infraestruture.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gesoft.food.domain.model.Cozinha;
import com.gesoft.food.domain.reposiory.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl  implements CozinhaRepository{
	 
	@PersistenceContext
	private  EntityManager manager;
	
	@Override
	public List<Cozinha> listar(){
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	@Transactional
	public Cozinha salvarAtualizar(Cozinha cozinha) {
		//insere ou atualiza se já existir uma pk correspondente
		return manager.merge(cozinha);
	}
	@Override
	public Cozinha buscarPorId(Long id) {
		return manager.find(Cozinha.class, id);
	}
	@Override
	@Transactional
	public void remover(Long cozinhaId) {
		Cozinha cozinha = buscarPorId(cozinhaId); 
		if (cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cozinha);
	}

}
