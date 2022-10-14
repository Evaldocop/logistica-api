package com.gesoft.food.infraestruture.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gesoft.food.domain.model.Restaurante;
import com.gesoft.food.domain.reposiory.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl  implements RestauranteRepository{
	 
	@PersistenceContext
	private  EntityManager manager;
	
	@Override
	public List<Restaurante> listar(){
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	@Transactional
	public Restaurante salvarAtualizar(Restaurante restaurante) {
		//insere ou atualiza se já existir uma pk correspondente
		return manager.merge(restaurante);
	}
	@Override
	public Restaurante buscarPorId(Long id) {
		return manager.find(Restaurante.class, id);
	}
	@Override
	@Transactional
	public void remover(Long restauranteID) {
		
		manager.remove(restauranteID);
	}

}
