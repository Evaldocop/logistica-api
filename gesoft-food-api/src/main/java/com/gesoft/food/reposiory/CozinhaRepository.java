package com.gesoft.food.reposiory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.gesoft.food.domain.model.Cozinha;

@Component
public class CozinhaRepository  {
	
	
	@PersistenceContext
	private  EntityManager manager;
	
	public List<Cozinha> listar(){
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

}
