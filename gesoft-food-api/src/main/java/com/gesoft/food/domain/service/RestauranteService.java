package com.gesoft.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cozinha;
import com.gesoft.food.domain.model.Restaurante;
import com.gesoft.food.domain.reposiory.CozinhaRepository;
import com.gesoft.food.domain.reposiory.RestauranteRepository;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository RestauranteRepository;
	@Autowired
	private CozinhaRepository cozinhaRepozitory;
	
	public Restaurante salvarAtualizar(Restaurante restaurante) {
		Long cozinhaId=restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepozitory.buscarPorId(cozinhaId);
		if(cozinha==null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe conzinha com id %d", cozinhaId));
		}
		
		restaurante.setCozinha(cozinha);
		return RestauranteRepository.salvarAtualizar(restaurante);
	}
	
	public void excluir(Long RestauranteId) {
		try {
			//Restaurante RestauranteBD = RestauranteRepository.buscarPorId(RestauranteId);
		
				RestauranteRepository.remover(RestauranteId);
		} catch (DataIntegrityViolationException e) {
				throw new EntidadeEmUsoException(
						String.format("Restaurante de código %d não pode ser removida, pois está em uso.", RestauranteId));
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
					"Restaurante com código %d não encontrada.", RestauranteId));
		}
	}


}
