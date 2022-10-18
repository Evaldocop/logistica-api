package com.gesoft.food.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gesoft.food.domain.exception.EntidadeEmUsoException;
import com.gesoft.food.domain.exception.EntidadeNaoEncontradaException;
import com.gesoft.food.domain.model.Cozinha;
import com.gesoft.food.domain.model.Restaurante;
import com.gesoft.food.domain.reposiory.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CozinhaService cozinhaService;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public List<Restaurante> findByTaxaBetween(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaBetween(taxaInicial, taxaFinal);
	}

	/*
	 * public List<Restaurante> findByNomeContainsAndCozinhaId(String nome, Long
	 * cozinhaId) { return
	 * restauranteRepository.findByNomeContainsAndCozinhaId(nome, cozinhaId); }
	 */
	
	public List<Restaurante> consultarLikeAndCozinhaId(String nome, Long cozinhaId) {
		return restauranteRepository.findByNomeContainsAndCozinhaId(nome, cozinhaId);
	}

	public Restaurante salvarAtualizar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaService.buscarPorId(cozinhaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Não existe conzinha com id %d", cozinhaId)));

		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

	public Optional<Restaurante> buscarPorId(Long restauranteId) {
		return restauranteRepository.findById(restauranteId);
	}

	public void excluir(Long restauranteId) {
		try {
			// Restaurante RestauranteBD = RestauranteRepository.buscarPorId(RestauranteId);

			restauranteRepository.deleteById(restauranteId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, pois está em uso.", restauranteId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante com código %d não encontrada.", restauranteId));
		}
	}

	public Optional <Restaurante> findFirstRestauranteByNomeContains(String nome){
		return restauranteRepository.findFirstRestauranteByNomeContains(nome);
	}
	
	public  List<Restaurante> findTop2ByByNomeContains(String nome) {
		return restauranteRepository.findTop2ByNomeContains(nome);
	}
	
	public int countByCozinhaId(Long cozinhaId) {
		return  restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	public boolean existsByNome(String nome) {
		return restauranteRepository.existsByNome(nome);
	}
	

}
