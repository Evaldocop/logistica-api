package com.gesoft.food.api.controller;

import java.util.List;

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
import com.gesoft.food.domain.model.Cidade;
import com.gesoft.food.domain.service.CidadeService;

//GET /cozinhas HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController /// posssui o controller e response body
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	// por padrao retorna json, mas retorna tbm xml. o op
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cidade> listar() {
		return cidadeService.listar();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		cidade = cidadeService.salvarAtualizar(cidade);
		return cidade;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{cidadeId}")
	public ResponseEntity<?> alterar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = cidadeService.buscarPorId(cidadeId);

		if (cidadeAtual != null) {
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			cidadeService.salvarAtualizar(cidadeAtual);
			return ResponseEntity.ok(cidadeAtual);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> remover(@PathVariable("cidadeId") Long cidadeId) {
		try {
			
				cidadeService.excluir(cidadeId);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

}