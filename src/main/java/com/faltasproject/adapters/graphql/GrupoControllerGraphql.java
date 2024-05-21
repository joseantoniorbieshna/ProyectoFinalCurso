package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.services.clases.GrupoService;


@Controller
public class GrupoControllerGraphql{

	private final GrupoService grupoService;

	public GrupoControllerGraphql( GrupoService grupoService) {
		super();
		this.grupoService = grupoService;
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<Grupo> grupos(){
		return grupoService.findAll();
	} 
	
	
}
