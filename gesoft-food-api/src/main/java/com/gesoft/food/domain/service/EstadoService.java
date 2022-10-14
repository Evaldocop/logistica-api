package com.gesoft.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cozinha;
import com.gesoft.food.domain.model.Estado;
import com.gesoft.food.domain.reposiory.CozinhaRepository;
import com.gesoft.food.domain.reposiory.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvarAtualizar(Estado estado) {
		return estadoRepository.salvarAtualizar(estado);
	}

	public void excluir(Long estadoId) {
		try { // Cozinha cozinhaBD =estadoRepository.buscarPorId(estadoId);

			estadoRepository.remover(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, pois está em uso.", estadoId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Estado com código %d não encontrada.", estadoId));
		}
	}

	public List<Estado> listar() {
		// TODO Auto-generated method stub
		return estadoRepository.listar();
	}
	
	public Estado buscarPorId(Long estadoId) {
		return estadoRepository.buscarPorId(estadoId);
	}

}
