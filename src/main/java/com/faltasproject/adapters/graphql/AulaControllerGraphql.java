package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.services.clases.AulaService;


@Controller
public class AulaControllerGraphql{

	private final AulaService aulaService;

	public AulaControllerGraphql(AulaService aulaService) {
		super();
		this.aulaService = aulaService;
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<Aula> aulas(){
		return aulaService.findAll();
	} 
	
	
}
