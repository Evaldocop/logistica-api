package com.gesoft.food.reposiory;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.gesoft.food.GesoftFoodApiApplication;
import com.gesoft.food.domain.model.Cozinha;

public class ConsultaMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext= new SpringApplicationBuilder(GesoftFoodApiApplication.class)
				          .web(WebApplicationType.NONE)
				           .run(args);
		
		CozinhaRepository cozinhaRepository=	applicationContext.getBean(CozinhaRepository.class);
		List<Cozinha> listCoz = cozinhaRepository.listar();
		for(Cozinha cozinha:listCoz) {
			System.out.println(cozinha.getNome());
		}
	}
}
