package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.services.profesorado.ProfesorService;


@Controller
public class ProfesorControllerGraphql{

	private final ProfesorService profesorService;

	public ProfesorControllerGraphql(ProfesorService profesorService) {
		super();
		this.profesorService = profesorService;
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<Profesor> profesores(){
		return profesorService.findAll();
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<Profesor> profesoresWithoutRegistrationUser(){
		return profesorService.findAllWithoutRegistrationUser();
	}
	
	
}
