package com.gesoft.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	/* 06-12 - Nao precisa quem faz a autenticacao eh o AuthorizationServer
	
	// configuração de usuários em memoria
	//buider
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		    .withUser("evaldo")
		        .password(passwordEncoder().encode("20131show"))
		        .roles("ADMIN")
		    .and()
		    .withUser("gustavo")
	        .password(passwordEncoder().encode("20131show"))
	        .roles("ADMIN");
	}
	
	*/

	//OpaqueToken
	protected void configure(HttpSecurity http) throws Exception{
		
		
		// linguagem fluida 
			http
			.authorizeRequests()
			 
			    .anyRequest().authenticated()
			.and()
			   .oauth2ResourceServer().opaqueToken();
	}
	
	/* 06-12 - Nao precisa quem faz a autenticacao eh o AuthorizationServer
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
	
	}
	*/
	
}
