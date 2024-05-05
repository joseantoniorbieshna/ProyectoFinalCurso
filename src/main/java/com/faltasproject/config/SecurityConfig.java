package com.faltasproject.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf->csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}
	
	@Bean
	public AuthenticationManager  authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	public AuthenticationProvider authenticationProvider() {
		/*El provider DAO en especifico necesita de 2 partes (passwordEncoder y UserDetailService)
		 * PasswordEncoder: es el que vailda y encripta las contraseñas.
		 * UserDetailService: Es el que hace el llama a la base de datos*/
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder()); //llama al metodo de esta clase
		provider.setUserDetailsService(userDetailsService()); //llama al metodo de esta clase
		return provider;
	}
	
	/*Es el que vailda y encripta las contraseñas*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		/*Este no encripta las contraseñas*/
		return NoOpPasswordEncoder.getInstance();
	}
	
	/*Spring security entiende/valida los usuarios a partir de este objeto,
	 * es decir, si viene de la base de datos habría que convertirlo a userDetails*/
	@Bean
	public UserDetailsService userDetailsService() {
		List<UserDetails> usersDetails = new ArrayList<>();
		
		usersDetails.add(User.withUsername("jose")
				.password("1234")
				.authorities("ROLE_ADMIN")
				.build());
		
		usersDetails.add(User.withUsername("pepe")
				.password("1234")
				.authorities("ROLE_USER")
				.build());
		/*Aquí le pasamos un usuario, pero se puede pasar una lista*/
		return new InMemoryUserDetailsManager(usersDetails);
		
	}
}
