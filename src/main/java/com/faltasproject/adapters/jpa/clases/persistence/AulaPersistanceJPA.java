package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.AulaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.AulaEntity;
import com.faltasproject.domain.exceptions.ConflictException;
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
			throw new ConflictException(getMessageErrorExist(aula.getReferencia()));
		}
		return aulaRepositoryJPA.save(new AulaEntity(aula)).toAula();
	}

	@Override
	public Aula update(String referencia, Aula aula) {
		AulaEntity aulaEntity=aulaRepositoryJPA.findByReferencia(referencia)
		.orElseThrow(() -> new NotFoundException( getMessageErrorNotExist(referencia) ));
		
		//CAMBIAMOS DATOS
		aulaEntity.fromAula(aula);
		
		return aulaRepositoryJPA.save(aulaEntity).toAula();
	}

	@Override
	public Stream<Aula> readAll() {
		return aulaRepositoryJPA.findAll().stream().map(AulaEntity::toAula);
	}

	@Override
	public Stream<Aula> readContainInName(String search) {
		return aulaRepositoryJPA.findByNombreContainingIgnoreCase(search)
				.stream().map(AulaEntity::toAula);
	}

	@Override
	public boolean delete(String referencia) {
		if(!existReferencia(referencia)) {
			throw new NotFoundException( getMessageErrorNotExist(referencia) );
		}
		aulaRepositoryJPA.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Aula readByReferencia(String referencia) {
		return aulaRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException( getMessageErrorNotExist(referencia) ))
				.toAula();
	}

	@Override
	public boolean existReferencia(String referencia) {
		return aulaRepositoryJPA.findByReferencia(referencia).isPresent();
	}
	
	private String getMessageErrorExist(String referencia) {
		return "El Aula con la referencia '"+referencia+"' ya existe";
	}
	
	private String getMessageErrorNotExist(String referencia) {
		return "El Aula con la referencia '"+referencia+"' no existe";
	}

}
