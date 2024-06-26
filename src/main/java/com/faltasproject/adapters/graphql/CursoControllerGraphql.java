package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.services.clases.CursoService;

@Controller
public class CursoControllerGraphql {
	
	private final CursoService cursoService;

	public CursoControllerGraphql(CursoService cursoService) {
		super();
		this.cursoService = cursoService;
	}
	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<Curso> cursos(){
		return cursoService.findAll();
	}

}
