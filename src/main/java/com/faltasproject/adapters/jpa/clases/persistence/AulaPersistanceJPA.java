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
			throw new ConflictExceptions(getMessageErrorExist(aula.getReferencia()));
		}
		return aulaRepositoryJPA.save(new AulaEntity(aula)).toAula();
	}

	@Override
	public Aula update(Long referencia, Aula aula) {
		AulaEntity aulaEntity=aulaRepositoryJPA.findByReferencia(referencia)
		.orElseThrow(() -> new NotFoundException( getMessageErrorNotExist(referencia) ));
		
		//TRATAMOS LA REFERENCIA
		if(aula.getReferencia()==null) {
			aula.setReferencia(referencia);
		}else if(existReferencia(aula.getReferencia())){
			throw new ConflictExceptions("La referencia a la que quieres cambiar el Aula ya existe");
		}
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
	public boolean delete(Long referencia) {
		if(!existReferencia(referencia)) {
			throw new NotFoundException( getMessageErrorNotExist(referencia) );
		}
		aulaRepositoryJPA.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Aula readByReferencia(Long referencia) {
		return aulaRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException( getMessageErrorNotExist(referencia) ))
				.toAula();
	}

	@Override
	public boolean existReferencia(Long referencia) {
		return aulaRepositoryJPA.findByReferencia(referencia).isPresent();
	}
	
	private String getMessageErrorExist(Long referencia) {
		return "El Aula con la referencia '"+referencia+"' ya existe";
	}
	
	private String getMessageErrorNotExist(Long referencia) {
		return "El Aula con la referencia '"+referencia+"' no existe";
	}

}
