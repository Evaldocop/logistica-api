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
import com.gesoft.food.domain.model.FormaPagamento;
import com.gesoft.food.domain.service.FormaPagamentoService;

//GET /formaPagamentos HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController /// posssui o controller e response body
@RequestMapping("/formapagamentos")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoService formaPagamentoService;
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<FormaPagamento> listar() {
		return formaPagamentoService.listar();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{formaPagamentoId}")
	private ResponseEntity<?> buscar(@PathVariable("formaPagamentoId") Long formaPagamentoId) {
		Optional<FormaPagamento> formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);
		if (formaPagamento.isPresent())
			return ResponseEntity.ok(formaPagamento.get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					String.format("Forma pagamento com id %d não encontrada", formaPagamentoId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	private FormaPagamento adicionar(@RequestBody FormaPagamento formaPagamento) {
		return formaPagamentoService.save(formaPagamento);

	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{formaPagamentoId}")
	private ResponseEntity<?> atualizar(@PathVariable("formaPagamentoId") Long formaPagamentoId, @RequestBody FormaPagamento formaPagamento) {
		Optional<FormaPagamento> formaPagamentoAtual = formaPagamentoService.buscarPorId(formaPagamentoId);

		if (formaPagamentoAtual.isPresent()) {
			// param >3 inabilita a mudança
			BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual.get(), "id");
			FormaPagamento formaPagamentoSalva=formaPagamentoService.save(formaPagamentoAtual.get());
			return ResponseEntity.ok(formaPagamentoSalva);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					String.format("Forma pagamento com id %d não encontrada", formaPagamentoId));
	
	}

	@DeleteMapping("/{formaPagamentoId}")
	private ResponseEntity<?> remover(@PathVariable("formaPagamentoId") Long formaPagamentoId) {
		try {
		   formaPagamentoService.excluir(formaPagamentoId);
		   return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}

}
