package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.services.clases.SesionService;


@Controller
public class SesionControllerGraphql{

	private final SesionService sesionService;

	public SesionControllerGraphql(SesionService sesionService) {
		super();
		this.sesionService = sesionService;
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<Sesion> sesiones(){
		return sesionService.findAll();
	} 
	
	
}
