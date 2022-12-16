package com.gesoft.gesoftauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	

	
	@Autowired(required = false)
	private UserDetailsService userDetailsService;
	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients
		      .inMemory()
				/*
				 * credenciais da appLogin se comuinicar com o AuthorizatioServer Não confundir
				 * com as crdenciais do owner(prtado do acesso ao resource server)
				 * 
				 */
					.withClient("gesoftfood-web")
				    .secret(passwordEncoder.encode("20131show"))
					.authorizedGrantTypes("password", "refresh_token").scopes("write", "read")
					/// inspira em 1h
					.accessTokenValiditySeconds(60 * 60 * 6).and().withClient("gesoftfood-mob")
					.secret(passwordEncoder.encode("20131show")).authorizedGrantTypes("password").scopes("write", "read")
				.and()
					.withClient("foodnanalytics")
				 .secret(passwordEncoder.encode("20131show"))
					.authorizedGrantTypes("authorization_code")
					.scopes("write", "read")
					.redirectUris("http://aplicacao-cliente")
					.and()
					.withClient("webadmin")
     		  /*>>*/.authorizedGrantTypes("implicit")
					.scopes("write", "read")
					.redirectUris("http://aplicacao-cliente");

	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/*
		 * expression spring sec(isAuthenticated()) - confirma validade do token
		 *  entre o appClient e recursos
		 */
		//security.checkTokenAccess("isAuthenticated()");
		/*
		 * expression spring sec(permitedAll()) - descarta atutenticação de validade do token
		 *  entre o appClient e recursos
		 */
		security.checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
	}

	/*
	 * @Override public void configure(AuthorizationServerEndpointsConfigurer
	 * endpoints) throws Exception {
	 * endpoints.authenticationManager(authenticationManager); }
	 */
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.reuseRefreshTokens(false)
			.accessTokenConverter(jwtAccessTokenConverter())
			.tokenGranter(tokenGranter(endpoints));
	}
	
	@Bean
	public JwtAccessTokenConverter  jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("20131show20131show20131show20131show20131show");
		return jwtAccessTokenConverter;
		
	}	
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
	

}
