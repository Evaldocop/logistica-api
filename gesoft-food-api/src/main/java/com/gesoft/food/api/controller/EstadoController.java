package com.gesoft.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gesoft.food.domain.model.Estado;
import com.gesoft.food.domain.reposiory.EstadoRepository;

//GET /cozinhas HTTP 1.1

//@Controller
//@ResponseBody ///responde a requisicao http
@RestController ///posssui o controller e response body
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepozitory;
	
	
	//por padrao retorna json, mas retorna tbm xml. o op
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Estado> listar(){
		return estadoRepozitory.listar();
	}
	
	

}
