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
import com.gesoft.food.domain.model.Permissao;
import com.gesoft.food.domain.service.PermissaoService;

//GET /cozinhas HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController /// posssui o controller e response body
@RequestMapping("/permissoes")
public class PermissaoController {

	@Autowired
	private PermissaoService permissaoService;

	// por padrao retorna json, mas retorna tbm xml. o op
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Permissao> listar() {
		return permissaoService.listar();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Permissao adicionar(@RequestBody Permissao permissao) {
		permissao = permissaoService.salvarAtualizar(permissao);
		return permissao;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{permissaoId}")
	public ResponseEntity<?> alterar(@PathVariable("permissaoId") Long permissaoId, @RequestBody Permissao permissao) {
		Optional<Permissao> permissaoAtual = permissaoService.buscarPorId(permissaoId);

		if (permissaoAtual.isPresent()) {
			BeanUtils.copyProperties(permissao, permissaoAtual.get(), "id");
			permissaoService.salvarAtualizar(permissaoAtual.get());
			return ResponseEntity.ok(permissaoAtual.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					String.format("Permissão com id %d não foi localizada", permissaoId));
		}
	}

	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<?> remover(@PathVariable("permissaoId") Long permissaoId) {
		try {
			
				permissaoService.excluir(permissaoId);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

}
