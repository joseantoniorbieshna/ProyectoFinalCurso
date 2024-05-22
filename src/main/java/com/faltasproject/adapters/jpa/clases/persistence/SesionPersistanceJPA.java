package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Hibernate;
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

import jakarta.transaction.Transactional;

@Repository("sesionPersistance")
@Transactional
public class SesionPersistanceJPA implements SesionPersistance {
	
	private final SesionRepositoryJPA sesionRepositoryJPA;
	private final MateriaRepositoryJPA materiaRepositoryJPA;
	private final GrupoRepositoryJPA grupoRepositoryJPA;
	private final ProfesorRepositoryJPA profesorRepositoryJPA;
	private final AulaRepositoryJPA aulaRepositoryJPA;
	private final String LAST_PART_MESSAGE=" introducida al crear la sesion";
	

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
		//Persist Data
		AulaEntity aula = getAulaPersist(sesion);
		MateriasEntity materia = materiaPersist(sesion.getReferenciaMateria());
		ProfesorEntity profesor= profesorPersist(sesion.getReferenciaProfesor());
		Set<GrupoEntity> grupos = grupoPersist(sesion);
		
		SesionEntity sesionEntity = new SesionEntity(sesion);
		
		sesionEntity.setAula(aula);
		sesionEntity.setMateria(materia);
		sesionEntity.setGrupos(grupos);
		sesionEntity.setProfesor(profesor);
		
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
		AulaEntity aula = getAulaPersist(sesion);
		MateriasEntity materia = materiaPersist(sesion.getReferenciaMateria());
		ProfesorEntity profesor= profesorPersist(sesion.getReferenciaProfesor());
		Set<GrupoEntity> grupos = grupoPersist(sesion);
		
		sesionEntity.fromSesion(sesion);
		
		sesionEntity.setAula(aula);
		sesionEntity.setMateria(materia);
		sesionEntity.setGrupos(grupos);
		sesionEntity.setProfesor(profesor);
		
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
	
	
	private AulaEntity getAulaPersist(Sesion sesion) {
		if(sesion.getAula()!=null && sesion.getReferenciaAula()!=null) {
			return aulaRepositoryJPA.findByReferencia(sesion.getReferenciaAula())
					.orElseThrow(()-> new NotFoundException("No existe la referencia de aula "+sesion.getReferenciaAula()+LAST_PART_MESSAGE) );
		} 
		return null;
	}
	private MateriasEntity materiaPersist(String referenciaMateria) {
		return materiaRepositoryJPA.findByReferencia(referenciaMateria)
				.orElseThrow(()-> new NotFoundException("No existe la referencia de materia "+referenciaMateria+LAST_PART_MESSAGE));
	}
	
	private ProfesorEntity profesorPersist(String referenciaProfesor) {
		return profesorRepositoryJPA.findByReferencia(referenciaProfesor)
				.orElseThrow(()-> new NotFoundException("No existe la referencia de profesor "+referenciaProfesor+LAST_PART_MESSAGE));
		
	}
	private Set<GrupoEntity>  grupoPersist(Sesion sesion) {
		return sesion.getGrupos().stream()
				.map(grupo-> grupoRepositoryJPA.findByNombreEquals(grupo.getNombre())
						.orElseThrow(()-> new NotFoundException("No existe el nombre del grupo introducido al crear la sesion")))
				.collect(Collectors.toSet());
	}
	

}
