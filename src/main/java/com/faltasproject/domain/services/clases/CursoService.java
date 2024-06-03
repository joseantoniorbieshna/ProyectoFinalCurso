package com.faltasproject.domain.services.clases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.persistence.CursoPersistanceJPA;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.dtos.CursoCreateDTO;
import com.faltasproject.domain.models.clases.mapper.CursoCreateMapper;

@Service
public class CursoService {
	private final CursoPersistanceJPA cursoPersistanceJPA;
	private final CursoCreateMapper cursoCreateMapper;
	
	public CursoService(CursoPersistanceJPA cursoPersistanceJPA,
						CursoCreateMapper cursoCreateMapper) {
		this.cursoPersistanceJPA=cursoPersistanceJPA;
		this.cursoCreateMapper=cursoCreateMapper;
	}
	
	public Curso create(CursoCreateDTO curso) {
		return cursoPersistanceJPA.create(cursoCreateMapper.toEntity(curso));
	}

	public List<Curso> findAll() {
		return cursoPersistanceJPA.readAll().toList();
	}
	
}
