package com.faltasproject.domain.services.clases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.persistence.GrupoPersistanceJPA;
import com.faltasproject.domain.models.clases.Grupo;

@Service
public class GrupoService {
	
	private final GrupoPersistanceJPA grupoPersistanceJPA;
	
	public GrupoService(GrupoPersistanceJPA grupoPersistanceJPA) {
		this.grupoPersistanceJPA = grupoPersistanceJPA;
	}

	public Grupo create(Grupo grupo) {
		return grupoPersistanceJPA.create(grupo);
	}
	
	public List<Grupo> findAll(){
		return grupoPersistanceJPA.readAll().collect(Collectors.toList());
	}
}
