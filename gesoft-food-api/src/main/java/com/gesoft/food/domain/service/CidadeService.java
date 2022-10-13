package com.gesoft.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cidade;
import com.gesoft.food.domain.model.Estado;
import com.gesoft.food.domain.reposiory.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	public Cidade salvarAtualizar(Cidade cidade) {
		return cidadeRepository.salvarAtualizar(cidade);
	}

	public void excluir(Long cidadeId) {
		try { // Cozinha cozinhaBD =estadoRepository.buscarPorId(estadoId);

			cidadeRepository.remover(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso.", cidadeId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com código %d não encontrada.", cidadeId));
		}
	}

	public List<Cidade> listar() {
		// TODO Auto-generated method stub
		return cidadeRepository.listar();
	}
	
	public Cidade buscarPorId(Long cidadeId) {
		return cidadeRepository.buscarPorId(cidadeId);
	}

}
