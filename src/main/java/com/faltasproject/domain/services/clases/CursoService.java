package com.faltasproject.domain.services.clases;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.persistence.CursoPersistanceJPA;
import com.faltasproject.domain.models.clases.Curso;

@Service
public class CursoService {
	private final CursoPersistanceJPA cursoPersistanceJPA;
	
	public CursoService(CursoPersistanceJPA cursoPersistanceJPA) {
		this.cursoPersistanceJPA=cursoPersistanceJPA;
	}
	
	public Curso create(Curso curso) {
		return cursoPersistanceJPA.create(curso);
	}
	
}
