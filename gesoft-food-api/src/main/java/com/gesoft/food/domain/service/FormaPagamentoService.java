package com.gesoft.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.FormaPagamento;
import com.gesoft.food.domain.reposiory.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	
	public List<FormaPagamento> listar() {
		return formaPagamentoRepository.findAll();
	}
	
	
	public Optional<FormaPagamento> buscarPorId(Long id){
		return formaPagamentoRepository.findById(id);
	}

	
	public FormaPagamento save(FormaPagamento formaPagamento){
		return  formaPagamentoRepository.save(formaPagamento);
	}
	
	public FormaPagamento salvarAtualizar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public void excluir(Long formaPagamentoId) {
		try {
			//FormaPagamento formaPagamentoBD = formaPagamentoRepository.buscarPorId(formaPagamentoId);
		
				formaPagamentoRepository.deleteById(formaPagamentoId);
		} catch (DataIntegrityViolationException e) {
				throw new EntidadeEmUsoException(
						String.format("Forma Pagamento de código %d não pode ser removida, pois está em uso.", formaPagamentoId));
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
					"Forma Pagamento com código %d não encontrada.", formaPagamentoId));
		}
	}


}
