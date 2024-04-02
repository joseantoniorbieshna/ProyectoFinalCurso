package com.faltasproject.adapters.jpa.profesorado.persistence;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.persistance_ports.profesorado.ProfesorPersistance;

@Repository("profesorPersistance")
public class ProfesorPersistanceJPA implements ProfesorPersistance {
	
	ProfesorRepositoryJPA profesorRepositoryJPA;
	
	public ProfesorPersistanceJPA(ProfesorRepositoryJPA profesorRepositoryJPA) {
		super();
		this.profesorRepositoryJPA = profesorRepositoryJPA;
	}

	@Override
	public Profesor create(Profesor profesor) {
		if(existReferencia(profesor.getReferencia())) {
			throw new ConflictExceptions(profesor.getReferencia());
		}
		
		return profesorRepositoryJPA.save(new ProfesorEntity(profesor)).toProfesor();
	}

	@Override
	public Profesor update(String referencia, Profesor profesor) {
		ProfesorEntity profesorEntiy=profesorRepositoryJPA.findByReferencia(referencia)
		.orElseThrow(()-> new NotFoundException(getMessageErrorExist(referencia)));

		//OBLIGAMOS A TENER REFERENCIA
		if(profesor.getReferencia()==null) {
			profesor.setReferencia(referencia);
		}else if( !referencia.equals(profesor.getReferencia()) &&
				existReferencia(profesor.getReferencia())) {
			throw new ConflictExceptions(getMessageErrorExist(profesor.getReferencia()));
		}
		// actualizamos
		profesorEntiy.fromProfesor(profesor);
		
		return profesorRepositoryJPA.save(profesorEntiy).toProfesor();
	}

	@Override
	public Stream<Profesor> readAll() {
		return profesorRepositoryJPA.findAll().stream()
				.map(profesorEntity->profesorEntity.toProfesor());
	}

	@Override
	public boolean delete(String referencia) {
		if(!existReferencia(referencia)) {
			throw new NotFoundException(getMessageErrorNotExist(referencia));
		}
		profesorRepositoryJPA.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Profesor readByReferencia(String referencia) {
		return profesorRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(()-> new NotFoundException(getMessageErrorNotExist(referencia)))
				.toProfesor();
	}

	@Override
	public boolean existReferencia(String referencia) {
		return profesorRepositoryJPA.findByReferencia(referencia).isPresent();
	}
	
	private String getMessageErrorNotExist(String referencia) {
		return "El profesor con la referencia '"+referencia+"' no existe";
	}
	
	private String getMessageErrorExist(String referencia) {
		return "El profesor con la referencia '"+referencia+"' ya existe";
	}

}
