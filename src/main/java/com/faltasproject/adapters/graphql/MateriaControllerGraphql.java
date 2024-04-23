package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.services.clases.MateriaService;


@Controller
public class MateriaControllerGraphql{

	private final MateriaService materiaService;

	public MateriaControllerGraphql(MateriaService materiaService) {
		super();
		this.materiaService = materiaService;
	}
	
	@QueryMapping
	public List<Materia> materias(){
		return materiaService.findAll();
	} 
	
	@QueryMapping
	public Materia findMateriaByReferencia(@Argument String referencia){
		return this.materiaService.findMateriaByReferencia(referencia);
	} 
	
	
}
