package com.gesoft.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cidade;
import com.gesoft.food.domain.model.Permissao;
import com.gesoft.food.domain.reposiory.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;

	public Permissao salvarAtualizar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}

	public void excluir(Long permissaoId) {
		try { // Cozinha cozinhaBD =estadoRepository.buscarPorId(estadoId);

			permissaoRepository.deleteById(permissaoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Permissão de código %d não pode ser removida, pois está em uso.", permissaoId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Permissão com código %d não encontrada.", permissaoId));
		}
	}

	public List<Permissao> listar() {
		// TODO Auto-generated method stub
		return permissaoRepository.findAll();
	}
	
	public Optional<Permissao> buscarPorId(Long permissaoId) {
		return permissaoRepository.findById(permissaoId);
	}

}
