package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.AulaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.AulaEntity;
import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.persistance_ports.clases.AulaPersistance;

@Repository("aulaPersistance")
public class AulaPersistanceJPA implements AulaPersistance {

	private final AulaRepositoryJPA aulaRepositoryJPA;
	
	public AulaPersistanceJPA(AulaRepositoryJPA aulaRepositoryJPA) {
		super();
		this.aulaRepositoryJPA = aulaRepositoryJPA;
	}

	@Override
	public Aula create(Aula aula) {
		if(existReferencia(aula.getReferencia())) {
			throw new ConflictExceptions("El Aula con la referencia '"+aula.getReferencia()+"' ya existe");
		}
		return aulaRepositoryJPA.save(new AulaEntity(aula)).toAula();
	}

	@Override
	public Aula update(Long referencia, Aula aula) {
		AulaEntity aulaEntity=aulaRepositoryJPA.findByReferencia(referencia)
		.orElseThrow(() -> new NotFoundException("El Aula con la referencia '"+aula.getReferencia()+"' no existe"));
		
		Long id = aulaEntity.getId();
		aulaEntity.fromAula(aula);
		//LA REFERENCIA NO PUEDEN CAMBIAR
		aulaEntity.setReferencia(referencia);
		
		return aulaEntity.toAula();
	}

	@Override
	public Stream<Aula> readAll() {
		return aulaRepositoryJPA.findAll().stream().map(aulaEntity -> aulaEntity.toAula());
	}

	@Override
	public Stream<Aula> readContainInName(String search) {
		return aulaRepositoryJPA.findByNombreContainingIgnoreCase(search)
				.stream().map(aulaEntity -> aulaEntity.toAula());
	}

	@Override
	public Boolean delete(Long referencia) {
		if(!existReferencia(referencia)) {
			throw new NotFoundException("El Aula con la referencia '"+referencia+"' no existe");
		}
		aulaRepositoryJPA.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Aula readByReferencia(Long referencia) {
		return aulaRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException("El Aula con la referencia '"+referencia+"' no existe"))
				.toAula();
	}

	@Override
	public Boolean existReferencia(Long referencia) {
		return aulaRepositoryJPA.findByReferencia(referencia).isPresent();
	}

}
