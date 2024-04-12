package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.AulaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.SesionRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.AulaEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.adapters.jpa.clases.entities.SesionEntity;
import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.persistance_ports.clases.SesionPersistance;

@Repository("sesionPersistance")
public class SesionPersistanceJPA implements SesionPersistance {
	
	private final SesionRepositoryJPA sesionRepositoryJPA;
	private final MateriaRepositoryJPA materiaRepositoryJPA;
	private final GrupoRepositoryJPA grupoRepositoryJPA;
	private final ProfesorRepositoryJPA profesorRepositoryJPA;
	private final AulaRepositoryJPA aulaRepositoryJPA;
	

	public SesionPersistanceJPA(SesionRepositoryJPA sesionRepositoryJPA, MateriaRepositoryJPA materiaRepositoryJPA,
			GrupoRepositoryJPA grupoRepositoryJPA, ProfesorRepositoryJPA profesorRepositoryJPA,
			AulaRepositoryJPA aulaRepositoryJPA) {
		super();
		this.sesionRepositoryJPA = sesionRepositoryJPA;
		this.materiaRepositoryJPA = materiaRepositoryJPA;
		this.grupoRepositoryJPA = grupoRepositoryJPA;
		this.profesorRepositoryJPA = profesorRepositoryJPA;
		this.aulaRepositoryJPA = aulaRepositoryJPA;
	}

	@Override
	public Sesion create(Sesion sesion) {
		if(existReferencia(sesion.getReferencia())) {
			throw new ConflictException(getMessageErrorExist(sesion.getReferencia()));
		}
		
		SesionEntity sesionEntity = new SesionEntity(sesion);
		changeDataToPersistData(sesionEntity);
		
		return sesionRepositoryJPA.save(sesionEntity).toSesion();
	}

	@Override
	public Sesion update(String referencia, Sesion sesion) {
		SesionEntity sesionEntity = sesionRepositoryJPA.findByReferencia(referencia)
				.orElseThrow( ()-> new NotFoundException(getMessageErrorNotFound(referencia)) );
		
		if(!referencia.equals(sesion.getReferencia()) && existReferencia(sesion.getReferencia())) {
			throw new ConflictException(String.format("La sesion con la referencia '%s' a la que quieres cambiar ya existe.", sesion.getReferencia()));
		}
		
		// CAMBIAMOS DATOS
		sesionEntity.fromSesion(sesion);
		changeDataToPersistData(sesionEntity);
		
		return sesionRepositoryJPA.save(sesionEntity)
				.toSesion();
	}

	@Override
	public Stream<Sesion> readAll() {
		return sesionRepositoryJPA.findAll().stream()
				.map(SesionEntity::toSesion);
	}

	@Override
	public Sesion readByReferencia(String referencia) {
		return sesionRepositoryJPA.findByReferencia(referencia)
				.orElseThrow( ()-> new NotFoundException(getMessageErrorNotFound(referencia)) )
				.toSesion();
	}

	@Override
	public void delete(String referencia) {
		if(!existReferencia(referencia)) {
			throw new NotFoundException(getMessageErrorNotFound(referencia));
		}
		sesionRepositoryJPA.deleteByReferencia(referencia);
	}

	@Override
	public boolean existReferencia(String referencia) {
		return sesionRepositoryJPA.findByReferencia(referencia).isPresent();
	}
	
	private String getMessageErrorNotFound(String referencia) {
		return "La sesion con la referencia "+referencia+ " no existe";
	}
	
	private String getMessageErrorExist(String referencia) {
		return  "La sesion con la referencia "+referencia+ " ya existe";
	}
	
	private void changeDataToPersistData(SesionEntity sesionEntity) {
		String lastPartPersistMessage=" introducida al crear la sesion";
		// Persist
		// AULA OPCIONAL
		AulaEntity aulaPersist = sesionEntity.getAula()==null || sesionEntity.getReferenciaAula()==null? null : aulaRepositoryJPA.findByReferencia(sesionEntity.getReferenciaAula())
				.orElseThrow(()-> new NotFoundException("No existe la referencia de aula "+sesionEntity.getReferenciaAula()+lastPartPersistMessage) );
		
		MateriasEntity materiaPersist = materiaRepositoryJPA.findByReferencia(sesionEntity.getReferenciaMateria())
				.orElseThrow(()-> new NotFoundException("No existe la referencia de materia "+sesionEntity.getReferenciaMateria()+lastPartPersistMessage));
		
		ProfesorEntity profesorPersist = profesorRepositoryJPA.findByReferencia(sesionEntity.getReferenciaProfesor())
				.orElseThrow(()-> new NotFoundException("No existe la referencia de profesor "+sesionEntity.getReferenciaProfesor()+lastPartPersistMessage));
		
		Set<GrupoEntity> gruposPersist = sesionEntity.getGrupos().stream()
				.map(grupo-> grupoRepositoryJPA.findByNombreEquals(grupo.getNombre())
						.orElseThrow(()-> new NotFoundException("No existe el nombre del grupo introducido al crear la sesion")))
				.collect(Collectors.toSet());
		
		// Change data persist
		sesionEntity.setAula(aulaPersist);
		sesionEntity.setMateria(materiaPersist);
		sesionEntity.setProfesor(profesorPersist);
		sesionEntity.setGrupos(gruposPersist);
	}
	

}
