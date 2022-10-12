package com.gesoft.food.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cozinha;
import com.gesoft.food.domain.reposiory.CozinhaRepository;
import com.gesoft.food.domain.service.CozinhaService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

//GET /cozinhas HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController /// posssui o controller e response body
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaRepository cozinhaRepozitory;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Cozinha> listar() {
		return cozinhaRepozitory.listar();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{cozinhaId}")
	private ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
		Cozinha cozinha = cozinhaRepozitory.buscarPorId(cozinhaId);
		// return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
		// return ResponseEntity.ok(cozinha);
		if (cozinha != null)
			return ResponseEntity.ok(cozinha);
		else
			return ResponseEntity.notFound().build();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	private Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cozinhaRepozitory.salvarAtualizar(cozinha);

	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{cozinhaId}")
	private ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepozitory.buscarPorId(cozinhaId);

		if (cozinhaAtual != null) {
			// param >3 inabilita a mudança
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			cozinhaService.salvarAtualizar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		} else
			return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{cozinhaId}")
	private ResponseEntity<Cozinha> remover(@PathVariable("cozinhaId") Long cozinhaId) {
		try {
		   cozinhaService.excluir(cozinhaId);
		   return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		
	}

}
