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
		if(existId(aula.getId())) {
			throw new ConflictExceptions("El Aula con el id '"+aula.getId()+"' ya existe");
		}
		return aulaRepositoryJPA.save(new AulaEntity(aula)).toAula();
	}

	@Override
	public Aula update(Long id, Aula aula) {
		AulaEntity aulaEntity=aulaRepositoryJPA.findById(id)
		.orElseThrow(() -> new NotFoundException("El Aula con el id '"+aula.getId()+"' no existe"));
		
		aulaEntity.fromAula(aula);
		aulaEntity.setId(id);
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
	public Boolean delete(Long id) {
		if(!existId(id)) {
			throw new NotFoundException("El Aula con el id '"+id+"' no existe");
		}
		aulaRepositoryJPA.deleteById(id);
		return !existId(id);
	}

	@Override
	public Aula readById(Long id) {
		return aulaRepositoryJPA.findById(id)
				.orElseThrow(() -> new NotFoundException("El Aula con el id '"+id+"' no existe"))
				.toAula();
	}

	@Override
	public Boolean existId(Long id) {
		return aulaRepositoryJPA.findById(id).isPresent();
	}

}
