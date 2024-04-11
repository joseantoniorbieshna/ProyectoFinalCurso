package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.persistance_ports.clases.SesionPersistance;

@SpringBootTest
class SesionPersistanceJPATest {
	
	@Autowired
	private SesionPersistance sesionPersistance;

	@Test
	void create() {
		String referenciaSesion="100";
		Materia materia = new Materia("03");
		Profesor profesor = new Profesor("02");
		Aula aula = new Aula("2");
		Set<Grupo> grupos = Sets.set(new Grupo("1A"),new Grupo("1B"));
		Sesion sesion = new Sesion(referenciaSesion,materia,profesor,grupos,aula);
		
		sesion = sesionPersistance.create(sesion);
		
		assertTrue(sesionPersistance.existReferencia(referenciaSesion));
		
		
		//VOLVER AL ESTADO ANTERIOR
		sesionPersistance.delete(referenciaSesion);
		assertFalse(sesionPersistance.existReferencia(referenciaSesion));
		
		
		//VOLVEMOS A INTENTAR PARA VER SI NO SE HA BORRADO EN CASCADA
		
		referenciaSesion="100";
		materia = new Materia("03");
		profesor = new Profesor("02");
		aula = new Aula("2");
		grupos = Sets.set(new Grupo("1A"),new Grupo("1B"));
		sesion = new Sesion(referenciaSesion,materia,profesor,grupos,aula);
		
		sesion = sesionPersistance.create(sesion);
		
		assertTrue(sesionPersistance.existReferencia(referenciaSesion));
		
		
		//VOLVER AL ESTADO ANTERIOR
		sesionPersistance.delete(referenciaSesion);
		assertFalse(sesionPersistance.existReferencia(referenciaSesion));
		
		// VOLVEMOS A CREAR PERO SIN AULA
		sesion = new Sesion(referenciaSesion,materia,profesor,grupos,null);
		sesion = sesionPersistance.create(sesion);
		assertNull(sesion.getAula());
		sesionPersistance.delete(referenciaSesion);
		
		// VOLVEMOS A CREAR PERO SIN REFERENCIA AULA
		sesion = new Sesion(referenciaSesion,materia,profesor,grupos,new Aula(null));
		sesion = sesionPersistance.create(sesion);
		assertNull(sesion.getAula());
		sesionPersistance.delete(referenciaSesion);
		
		
		//EXCEPCIONES
		String referenciaSesionExistente = "01";
		final Sesion sesionException1 = new Sesion(referenciaSesionExistente, materia, profesor, grupos, aula);
		assertThrows(ConflictException.class,()->sesionPersistance.create(sesionException1));
		
		//EXCEPCIONES MATERIA
		referenciaSesion="100";
		Materia materiaNoExistente = new Materia("ZZZZ");
		Sesion sesionException2 = new Sesion(referenciaSesion,materiaNoExistente,profesor,grupos,aula);
		assertThrows(NotFoundException.class,()->sesionPersistance.create(sesionException2));
		
		//EXCEPCION PROFESOR
		Profesor profesorNoExistente = new Profesor("ZZZZ");
		Sesion sesionException3 = new Sesion(referenciaSesion,materia,profesorNoExistente,grupos,aula);
		assertThrows(NotFoundException.class,()->sesionPersistance.create(sesionException3));
		
		//EXCEPCION AULA
		Aula aulaNoExistente = new Aula("ZZZZ");
		Sesion sesionException4 = new Sesion(referenciaSesion,materia,profesor,grupos,aulaNoExistente);
		assertThrows(NotFoundException.class,()->sesionPersistance.create(sesionException4));
		
		//EXCEPCION GRUPO
		Set<Grupo> gruposAnOneNotExist = Sets.set(new Grupo("1A"),new Grupo("FFFFFFFF"));
		Sesion sesionException5 = new Sesion(referenciaSesion,materia,profesor,gruposAnOneNotExist,aula);
		assertThrows(NotFoundException.class,()->sesionPersistance.create(sesionException5));
		
		
	}
	
	@Test
	void update() {
		//TODO
		String referencia="01";
		//DEFAULT SESION PARA HACER RESET
		Sesion sesionDefault = sesionPersistance.readByReferencia(referencia);
		
		//Cambaimos cosas
		String referenciaNew = "10000F";
		String referenciaMateria="03";
		Materia materia = new Materia(referenciaMateria);
		String referenciaProfesor="03";
		Profesor profesor = new Profesor(referenciaProfesor);
		String referenciaAula="3";
		Aula aula = new Aula(referenciaAula);
		String[] nombresGrupo= {"1A"};
		Set<Grupo> grupos = Sets.set(new Grupo(nombresGrupo[0]));
		Sesion sesionUpdate = new Sesion(referenciaNew,materia,profesor,grupos,aula);
		sesionUpdate = sesionPersistance.update(referencia, sesionUpdate);
		
		//Comprobamos
		assertFalse(sesionPersistance.existReferencia(referencia));
		assertTrue(sesionPersistance.existReferencia(referenciaNew));
		
		assertEquals(referenciaMateria, sesionUpdate.getReferenciaMateria());
		assertEquals("Lenguaje castellano",sesionUpdate.getMateria().getNombreCompleto());
		assertEquals(referenciaProfesor, sesionUpdate.getReferenciaProfesor());
		assertEquals("Paco", sesionUpdate.getProfesor().getNombre());
		assertEquals(referenciaAula, sesionUpdate.getReferenciaAula());
		assertEquals("AULA C", sesionUpdate.getAula().getNombre());
		
		assertTrue(sesionUpdate.getGrupos().stream().anyMatch( grupo -> grupo.getNombre().equals(nombresGrupo[0]) ));
		
		
		//VOLVER AL ESTADO DE ANTES
		sesionUpdate=sesionPersistance.update(referenciaNew, sesionDefault);
		referenciaMateria="01";
		referenciaProfesor="01";
		referenciaAula="2";
		assertEquals(referenciaMateria, sesionUpdate.getReferenciaMateria());
		assertEquals(referenciaProfesor, sesionUpdate.getReferenciaProfesor());
		assertEquals(referenciaAula, sesionUpdate.getReferenciaAula());
		
	}
	
	@Test
	void readAll(){
		long total = sesionPersistance.readAll().count();
		long expected=3;
		assertEquals(3, total);
		
	}
	
	@Test
	void readByReferencia() {
		String referencia="01";
		Sesion sesion = sesionPersistance.readByReferencia(referencia);
		
		String referenciaMateria="01";
		String nombreCompletoMateria = "Fisica y quimica";
		
		String referenciaProfesor="01";
		String nombreProfesor="Jose";
		
		String referenciaAula = "2";
		String nombreAula = "AULA B";
		
		String nombreGrupo1="1A";
		String nombreGrupo2="1B";
		String nombreGrupo3="1C";
		
		assertEquals(referencia, sesion.getReferencia());
		assertEquals(referenciaMateria, sesion.getReferenciaMateria());
		assertEquals(referenciaAula, sesion.getReferenciaAula());
		assertEquals(referenciaProfesor, sesion.getReferenciaProfesor());
		
		assertEquals(nombreCompletoMateria, sesion.getMateria().getNombreCompleto());
		assertEquals(nombreProfesor, sesion.getProfesor().getNombre());
		assertEquals(nombreAula, sesion.getAula().getNombre());
		
		Set<Grupo> grupos = sesion.getGrupos();
		assertTrue(grupos.stream().anyMatch(grupo-> grupo.getNombre().equals(nombreGrupo1)));
		assertTrue(grupos.stream().anyMatch(grupo-> grupo.getNombre().equals(nombreGrupo2)));
		assertTrue(grupos.stream().anyMatch(grupo-> grupo.getNombre().equals(nombreGrupo3)));
		
		//EXCEPCION
		String referenciaNoExiste = "PADFDSF";
		assertThrows(NotFoundException.class, ()-> sesionPersistance.readByReferencia(referenciaNoExiste));
	}
	
	@Test
	void delete() {
		String referenciaSesion="FFF";
		Materia materia = new Materia("01");
		Profesor profesor = new Profesor("03");
		Aula aula = new Aula("3");
		Set<Grupo> grupos = Sets.set(new Grupo("1C"),new Grupo("1B"));
		Sesion sesion = new Sesion(referenciaSesion,materia,profesor,grupos,aula);
		
		sesion = sesionPersistance.create(sesion);
		
		assertTrue(sesionPersistance.existReferencia(referenciaSesion));
		
		
		//VOLVER AL ESTADO ANTERIOR BORRANDO
		sesionPersistance.delete(referenciaSesion);
		assertFalse(sesionPersistance.existReferencia(referenciaSesion));
		
		
		//VOLVEMOS A INTENTAR PARA VER SI NO SE HA BORRADO EN CASCADA
		
		referenciaSesion="FFF";
		materia = new Materia("01");
		profesor = new Profesor("03");
		aula = new Aula("3");
		grupos = Sets.set(new Grupo("1C"),new Grupo("1B"));
		sesion = new Sesion(referenciaSesion,materia,profesor,grupos,aula);
		
		sesion = sesionPersistance.create(sesion);
		
		assertTrue(sesionPersistance.existReferencia(referenciaSesion));
		
		
		//VOLVER AL ESTADO ANTERIOR BORRANDO
		sesionPersistance.delete(referenciaSesion);
		assertFalse(sesionPersistance.existReferencia(referenciaSesion));
		
		//EXCEPCION
		final String referenciaExpcepcion1 = "ABCD";
		assertThrows(NotFoundException.class, ()-> sesionPersistance.delete(referenciaExpcepcion1));
		
		
	}
	
	@Test
	void existReferencia() {
		String referencia="01";
		assertTrue(sesionPersistance.existReferencia(referencia));

		referencia="03";
		assertTrue(sesionPersistance.existReferencia(referencia));
		
		referencia="00";
		assertFalse(sesionPersistance.existReferencia(referencia));
		
		referencia="04";
		assertFalse(sesionPersistance.existReferencia(referencia));
		
		
	}

}
