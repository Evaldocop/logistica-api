package com.gesoft.food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gesoft.food.Constants.ConstantesGesoft;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException /*ResponseStatusException */{
	private static final long serialVersionUID = 1L;
	
	private static String NAME_CLASS_FORMA_PAGAMENTO=" FORMA PAGAMENTO ";
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status,String message) {
	 * super(status,message); }
	 * 
	 * public EntidadeNaoEncontradaException(String message) {
	 * super(HttpStatus.CONFLICT,message); }
	 */
	public FormaPagamentoNaoEncontradaException(String message) {
		super(message);
	}
	
	public FormaPagamentoNaoEncontradaException(Long classId) {
		this(String.format(ConstantesGesoft.ENTIDADE_NAO_ENCONTRADA, NAME_CLASS_FORMA_PAGAMENTO, classId));
	}
	
}