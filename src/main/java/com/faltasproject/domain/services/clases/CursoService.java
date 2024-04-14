package com.faltasproject.domain.services.clases;

import java.util.List;

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

	public List<Curso> findAll() {
		return cursoPersistanceJPA.readAll().toList();
	}
	
}
