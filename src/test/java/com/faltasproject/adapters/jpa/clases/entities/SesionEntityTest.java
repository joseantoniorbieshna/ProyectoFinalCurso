package com.faltasproject.adapters.jpa.clases.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.SesionRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.persistence.SesionPersistanceJPA;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.persistance_ports.clases.SesionPersistance;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class SesionEntityTest {

	@Autowired
	private SesionPersistanceJPA sesionPersistance;
	@Autowired 
	private SesionRepositoryJPA sesionRepositoryJPA;
	@Autowired
	private GrupoRepositoryJPA grupoRepositoryJPA;
	
	@Test
	void removeGrupoEntity() {
		//CREAMOS
		String referenciaSesion="100";
		Materia materia = new Materia("03");
		Profesor profesor = new Profesor("02");
		Aula aula = new Aula("2");
		
		String grupoNombre1="1A";
		String grupoNombre2="1B";
		Set<Grupo> grupos = Sets.set(new Grupo(grupoNombre1),new Grupo(grupoNombre2));
		Sesion sesion = new Sesion(referenciaSesion,materia,profesor,grupos,aula);
		
		sesion = sesionPersistance.create(sesion);
		
		//EMPEZAMOS A COMPROBAR
		assertTrue(sesionPersistance.existReferencia(referenciaSesion));
		
		Optional<SesionEntity> sesionEntity = sesionRepositoryJPA.findByReferencia(referenciaSesion);
		assertTrue(sesionEntity.isPresent());
		SesionEntity sesionActual = sesionEntity.get();
		
		//COMRPOBAMOS QUE EXISTA LOS GRUPOS
		Optional<GrupoEntity> grupo1 = grupoRepositoryJPA.findByNombreEquals(grupoNombre1);
		Optional<GrupoEntity> grupo2 = grupoRepositoryJPA.findByNombreEquals(grupoNombre2);
		assertTrue(grupo1.isPresent());
		assertTrue(grupo2.isPresent());
		
		assertTrue( sesionActual.removeGrupoEntity(grupo1.get()) );
		sesionRepositoryJPA.save(sesionActual);
		assertFalse( sesionActual.removeGrupoEntity(grupo1.get()) );
		
		//SIGUE EXISTIENDO LOS GRUPO
		grupo1 = grupoRepositoryJPA.findByNombreEquals(grupoNombre1);
		grupo2 = grupoRepositoryJPA.findByNombreEquals(grupoNombre2);
		assertTrue(grupo1.isPresent());
		assertTrue(grupo2.isPresent());
		
		//NO EXISTE GRUPO EN SESION
		sesionEntity = sesionRepositoryJPA.findByReferencia(referenciaSesion);
		assertTrue(sesionEntity.isPresent());
		sesionActual = sesionEntity.get();
		
		//COMRPOBAMOS DESAPARECE DE SESION
		Optional<GrupoEntity> grupoDeleteProve1 = grupoRepositoryJPA.findByNombreEquals(grupoNombre1);
		Optional<GrupoEntity> grupoDeleteProve2 = grupoRepositoryJPA.findByNombreEquals(grupoNombre2);
		assertFalse( sesionActual.getGrupos().stream().anyMatch(t -> t.equals(grupoDeleteProve1.get())) );
		assertTrue( sesionActual.getGrupos().stream().anyMatch(t -> t.equals(grupoDeleteProve2.get())) );
		
		//BORRAMOS SESION Y NOS QUEDAMOS COMO ANTES
		sesionPersistance.delete(referenciaSesion);
		assertFalse(sesionPersistance.existReferencia(referenciaSesion));
		grupo1 = grupoRepositoryJPA.findByNombreEquals(grupoNombre1);
		grupo2 = grupoRepositoryJPA.findByNombreEquals(grupoNombre2);
		assertTrue(grupo1.isPresent());
		assertTrue(grupo2.isPresent());
		
	}

	@Test
	void fromSesion() {
		String referencia="9999";
		Sesion sesion = new Sesion(referencia);
		SesionEntity SesionEntity = new SesionEntity(sesion);
		assertNull(sesion.getAula());
		assertNull(sesion.getGrupos());
		assertNull(sesion.getMateria());
		assertNull(sesion.getProfesor());
	}
	@Test
	void allSave() {
		
		//CREAMOS
		String referenciaSesion="100";
		String referenciaMateria="03";
		String referenciaProfesor="02";
		Materia materia = new Materia(referenciaMateria);
		Profesor profesor = new Profesor(referenciaProfesor);
		Aula aula = new Aula("2");
		
		String grupoNombre1="1A";
		String grupoNombre2="1B";
		Set<Grupo> grupos = Sets.set(new Grupo(grupoNombre1),new Grupo(grupoNombre2));
		Sesion sesion = new Sesion(referenciaSesion,materia,profesor,grupos,aula);
		
		sesion = sesionPersistance.create(sesion);
		
		assertTrue(sesionPersistance.existReferencia(referenciaSesion));
		
		Optional<SesionEntity> sesionEntity = sesionRepositoryJPA.findByReferencia(referenciaSesion);
		assertTrue(sesionEntity.isPresent());
		SesionEntity sesionActual = sesionEntity.get();
		
		//EMPEZAMOS A COMPROBAR
		assertEquals(referenciaMateria,sesionActual.getMateria().getReferencia());
		assertEquals(referenciaProfesor,sesionActual.getProfesor().getReferencia());
		
		
		
		//BORRAMOS SESION Y NOS QUEDAMOS COMO ANTES
		sesionPersistance.delete(referenciaSesion);
		assertFalse(sesionPersistance.existReferencia(referenciaSesion));
		
	}
}
