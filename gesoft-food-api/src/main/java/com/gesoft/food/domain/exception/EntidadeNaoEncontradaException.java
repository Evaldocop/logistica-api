package com.gesoft.food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException /*ResponseStatusException */{
	private static final long serialVersionUID = 1L;
	
	/*
	 * public EntidadeNaoEncontradaException(HttpStatus status,String message) {
	 * super(status,message); }
	 * 
	 * public EntidadeNaoEncontradaException(String message) {
	 * super(HttpStatus.CONFLICT,message); }
	 */
	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}
	
}
