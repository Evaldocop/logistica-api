package com.gesoft.food.implementation.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.gesoft.food.domain.model.Restaurante;
import com.gesoft.food.domain.reposiory.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl  implements RestauranteRepositoryQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
    @Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

		var jpql = new  StringBuilder();
		jpql.append(" from Restaurante where 1=1 ");
		var parametros = new HashMap<String, Object>();
		
		if(StringUtils.hasLength(nome)) {
			jpql.append(" and nome like :nome ");
			parametros.put("nome", "%"+nome+"%");		
		}
		if(taxaFreteInicial!=null) {
			jpql.append(" and taxaFrete >= :taxaFreteInicial ");
			parametros.put("taxaFreteInicial",taxaFreteInicial);
		}
		if(taxaFreteFinal!=null) {
			jpql.append(" and taxaFrete <= :taxaFreteFinal ");
			parametros.put("taxaFreteFinal",taxaFreteFinal);
		}
		
		
		TypedQuery<Restaurante> query =  manager.createQuery(jpql.toString(),Restaurante.class);
		parametros.forEach((chave,valor)->query.setParameter(chave, valor));
 
		return query.getResultList();
		
	}

}
