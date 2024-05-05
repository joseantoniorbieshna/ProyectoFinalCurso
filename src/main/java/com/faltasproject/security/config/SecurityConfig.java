package com.faltasproject.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.faltasproject.security.config.filters.JwtTokenValidator;
import com.faltasproject.security.usuarios.service.UserDetailsServiceImpl;
import com.faltasproject.security.utils.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtUtils jwtUtils;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf->csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class) // es muy importante que se ejecute antes del basicAuthentication
				.build();
	}
	
	@Bean
	public AuthenticationManager  authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
		/*El provider DAO en especifico necesita de 2 partes (passwordEncoder y UserDetailService)
		 * PasswordEncoder: es el que vailda y encripta las contraseñas.
		 * UserDetailService: Es el que hace el llama a la base de datos*/
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder()); //llama al metodo de esta clase
		provider.setUserDetailsService(userDetailsService); //llama al metodo de esta clase
		return provider;
	}
	
	/*Es el que vailda y encripta las contraseñas*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		/*Este no encripta las contraseñas*/
		//return NoOpPasswordEncoder.getInstance(); // para pruebas
		
		return new BCryptPasswordEncoder();
	}
	
}
