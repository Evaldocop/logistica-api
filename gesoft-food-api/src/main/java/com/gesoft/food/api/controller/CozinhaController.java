package com.gesoft.food.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.gesoft.food.domain.service.CozinhaService;

//GET /cozinhas HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController /// posssui o controller e response body
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Cozinha> listar() {
		return cozinhaService.listar();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{cozinhaId}")
	private ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
		Optional<Cozinha> cozinha = cozinhaService.buscarPorId(cozinhaId);
		if (cozinha.isPresent())
			return ResponseEntity.ok(cozinha.get());
		else
			return ResponseEntity.notFound().build();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	private Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cozinhaService.save(cozinha);

	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{cozinhaId}")
	private ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaAtual = cozinhaService.buscarPorId(cozinhaId);

		if (cozinhaAtual.isPresent()) {
			// param >3 inabilita a mudança
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			Cozinha cozinhaSalva=cozinhaService.save(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);
		} else
			return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{cozinhaId}")
	private ResponseEntity<?> remover(@PathVariable("cozinhaId") Long cozinhaId) {
		try {
		   cozinhaService.excluir(cozinhaId);
		   return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}

}
