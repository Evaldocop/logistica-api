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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Restaurante;
import com.gesoft.food.domain.reposiory.RestauranteRepository;
import com.gesoft.food.domain.service.RestauranteService;

//GET /Restaurantes HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController /// posssui o controller e response body
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteRepository restauranteRepozitory;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Restaurante> listar() {
		return restauranteRepozitory.listar();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{RestauranteId}")
	private ResponseEntity<Restaurante> buscar(@PathVariable("RestauranteId") Long RestauranteId) {
		Restaurante Restaurante = restauranteRepozitory.buscarPorId(RestauranteId);
		// return ResponseEntity.status(HttpStatus.CREATED).body(Restaurante);
		// return ResponseEntity.ok(Restaurante);
		if (Restaurante != null)
			return ResponseEntity.ok(Restaurante);
		else
			return ResponseEntity.notFound().build();
	}

	@PostMapping
	private ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {

		try {
			restaurante = restauranteService.salvarAtualizar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{restauranteId}")
	private ResponseEntity<?> alterar(@PathVariable("restauranteId") Long restauranteId,
			@RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteRepozitory.buscarPorId(restauranteId);

		if (restauranteAtual != null) {
			try {
				// param >3 inabilita a mudança
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
				restauranteService.salvarAtualizar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}
	/*
	 * @ResponseStatus(value = HttpStatus.OK)
	 * 
	 * @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value =
	 * "/{RestauranteId}") private ResponseEntity<Restaurante>
	 * atualizar(@PathVariable("RestauranteId") Long RestauranteId,
	 * 
	 * @RequestBody Restaurante Restaurante) { Restaurante RestauranteAtual =
	 * restauranteRepozitory.buscarPorId(RestauranteId);
	 * 
	 * if (RestauranteAtual != null) { // param >3 inabilita a mudança
	 * BeanUtils.copyProperties(Restaurante, RestauranteAtual, "id");
	 * restauranteService.salvarAtualizar(RestauranteAtual); return
	 * ResponseEntity.ok(RestauranteAtual); } else return
	 * ResponseEntity.notFound().build();
	 * 
	 * }
	 */

	@DeleteMapping("/{RestauranteId}")
	private ResponseEntity<Restaurante> remover(@PathVariable("RestauranteId") Long RestauranteId) {
		try {
			restauranteService.excluir(RestauranteId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

	}

}
