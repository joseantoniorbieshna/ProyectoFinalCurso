package com.faltasproject.domain.models.clases.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.clases.dtos.CursoCreateDTO;

@Component
public class CursoCreateMapper {
	public Curso toEntity(CursoCreateDTO cursoDTO){
		return new Curso(cursoDTO.getReferencia(),cursoDTO.getNombre(),
				cursoDTO.getReferenciaMaterias().stream().map(s->new Materia(s)).collect(Collectors.toSet())
				);
	}

}
