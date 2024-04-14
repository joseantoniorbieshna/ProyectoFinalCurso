package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.services.clases.CursoService;

@Controller
public class CursoQuery {
	
	private final CursoService cursoService;

	public CursoQuery(CursoService cursoService) {
		super();
		this.cursoService = cursoService;
	}
	@QueryMapping
	public List<Curso> cursos(){
		return cursoService.findAll();
	}

}
