package com.gesoft.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gesoft.food.domain.model.Cozinha;
import com.gesoft.food.domain.reposiory.CozinhaRepository;

//GET /cozinhas HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController ///posssui o controller e response body
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepozitory;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Cozinha> listar(){
		return cozinhaRepozitory.listar();
	}
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{cozinhaId}")
	private ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId){
		Cozinha  cozinha = cozinhaRepozitory.buscarPorId(cozinhaId);	
	//	return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
	//	return ResponseEntity.ok(cozinha);
		return ResponseEntity.ok(cozinha);
	}


}
