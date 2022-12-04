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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	
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
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
	
	}
	
	
	protected void configure(HttpSecurity http) throws Exception{
		
		
		// linguagem fluida 
			http.httpBasic()
			.and()///.formLogin() como é ume api n precisa de login page
			.authorizeHttpRequests()
			    .antMatchers("/cozinhas/**").permitAll() 
			    .anyRequest().authenticated()
			.and()
			   .sessionManagement()
			      ///sevidor nao mantem session, cookes
			      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			/**   This is activated by default when using EnableWebSecurity's 
			      CSFR-Cross-Site Request Forgery -  Falsificação de solicitação entre sites
			      csfr tipo de ataque em requisições entre sites tentando alcançar usuarios autenticados
			      e usar essa credenciais de maneira frauduluenta
			*/
			      .csrf().disable();
	}

}
