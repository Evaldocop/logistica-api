package com.gesoft.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.Constants.ConstantesGesoft;
import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cidade;
import com.gesoft.food.domain.model.Estado;
import com.gesoft.food.domain.reposiory.CidadeRepository;
import com.gesoft.food.domain.reposiory.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private  EstadoService estadoService;

	public Cidade salvarAtualizar(Cidade cidade) {
		Long estadoId= cidade.getEstado().getId();
		Estado estado = estadoService.buscarPorId(estadoId);
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);
	}

	public List<Cidade> listar() {
		// TODO Auto-generated method stub
		return cidadeRepository.findAll();
	}

	public Cidade buscarPorId(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(ConstantesGesoft.ENTIDADE_NAO_ENCONTRADA, "Cidade", id)));
	}

	public void excluir(Long cidadeId) {
		try { // Cozinha cozinhaBD =estadoRepository.buscarPorId(estadoId);

			cidadeRepository.deleteById(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(ConstantesGesoft.ENTIDADE_NAO_ENCONTRADA, "Cidade", cidadeId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(ConstantesGesoft.ENTIDADE_NAO_ENCONTRADA, "Cidade", cidadeId));
		}
	}

}
