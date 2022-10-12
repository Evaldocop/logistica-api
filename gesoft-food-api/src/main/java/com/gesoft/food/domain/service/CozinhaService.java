package com.gesoft.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cozinha;
import com.gesoft.food.domain.reposiory.CozinhaRepository;

@Service
public class CozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvarAtualizar(Cozinha cozinha) {
		return cozinhaRepository.salvarAtualizar(cozinha);
	}
	
	public void excluir(Long cozinhaId) {
		try {
			//Cozinha cozinhaBD = cozinhaRepository.buscarPorId(cozinhaId);
		
				cozinhaRepository.remover(cozinhaId);
		} catch (DataIntegrityViolationException e) {
				throw new EntidadeEmUsoException(
						String.format("Cozinha de código %d não pode ser removida, pois está em uso.", cozinhaId));
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
					"Cozinha com código %d não encontrada.", cozinhaId));
		}
	}


}
