package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Grupo;

@SpringBootTest
class GrupoPersistanceJPATest {
	
	@Autowired
	CursoPersistanceJPA cursoPersistanceJPA;
	@Autowired
	GrupoPersistanceJPA grupoPersistanceJPA;
	
	@Test
	void update() {
		String grupoReferencia = "1A";
		Grupo grupoDefault = grupoPersistanceJPA.readByNombre(grupoReferencia);
		
		// UPDATE
		String referenciaCursoExistente = "3";
		
		String grupoReferenciaActualizada = "1Z";
		Grupo grupo =grupoPersistanceJPA.update(grupoReferencia, new Grupo(grupoReferenciaActualizada, new Curso(referenciaCursoExistente)));
		
		assertFalse(grupoPersistanceJPA.existNombre(grupoReferencia));
		assertTrue(grupoPersistanceJPA.existNombre(grupoReferenciaActualizada));
		
		assertEquals("3", grupo.getCurso().getReferencia());
		assertEquals("3º E.S.O", grupo.getCurso().getNombre());
		
		// VOLVEMOS AL ESTADO DE ANTES
		grupo =grupoPersistanceJPA.update(grupoReferenciaActualizada, grupoDefault);
		
		
	}
	
	@Test
	void readAll() {
		long totalGrupos = grupoPersistanceJPA.readAll().count();
		long expected = 7;
		assertEquals(expected, totalGrupos);
	}
	
	@Test
	void readContainInName() {
		long totalCount=grupoPersistanceJPA.readContainInName("C").count();
		long expected=2;
		assertEquals(expected, totalCount);
		
	}
	
	@Test
	void createAndDelete() {

		// CREAMOS CURSO
		String referenciaCurso = "100C";
		Curso cursoCreate = new Curso(referenciaCurso, "CURSO 100");
		cursoCreate = cursoPersistanceJPA.create(cursoCreate);
		
		// GRUPO CONFLICT
		final String nombreGrupoConflict = "1A";
		final Grupo grupoConflict = new Grupo(nombreGrupoConflict,cursoCreate);
		assertThrows(ConflictException.class, ()-> grupoPersistanceJPA.create(grupoConflict));

		// GRUPO CREATION
		String nombreGrupo = "100G";
		Grupo grupo = new Grupo(nombreGrupo,cursoCreate);
		grupo = grupoPersistanceJPA.create(grupo);
		assertEquals(nombreGrupo, grupo.getNombre());
		
		// COMPROBAMOS QUE SE BORRE
		boolean response = grupoPersistanceJPA.delete(nombreGrupo);
		assertTrue(response);
		assertFalse(grupoPersistanceJPA.existNombre(nombreGrupo));
		// COMPROBAMOS QUE NO SE HAYA BORRADO EN CASCADA
		response = cursoPersistanceJPA.existReferencia(referenciaCurso);
		assertTrue(response);
		
		// BORRAMOS
		assertTrue(cursoPersistanceJPA.existReferencia(referenciaCurso));
		response = cursoPersistanceJPA.delete(referenciaCurso);
		assertTrue(response);
		assertFalse(cursoPersistanceJPA.existReferencia(referenciaCurso));
		
	}
	
	@Test
	void readByNombre() {
		// GRUPO E4D
		String nombreGrupo = "E4D";
		Grupo grupo=grupoPersistanceJPA.readByNombre(nombreGrupo);
		assertEquals(nombreGrupo, grupo.getNombre());
		
		String referenciaCursoExpected="5";
		String nombreCursoExpected="2º Bachillerato";
		assertEquals(referenciaCursoExpected, grupo.getCurso().getReferencia());
		assertEquals(nombreCursoExpected, grupo.getCurso().getNombre());
		
		// GRUPO 4C
		nombreGrupo = "4C";
		grupo=grupoPersistanceJPA.readByNombre(nombreGrupo);
		assertEquals(nombreGrupo, grupo.getNombre());
		
		referenciaCursoExpected="1";
		nombreCursoExpected="4º E.S.O";
		assertEquals(referenciaCursoExpected, grupo.getCurso().getReferencia());
		assertEquals(nombreCursoExpected, grupo.getCurso().getNombre());
		
		// ERROR
		final String nombreGrupoNotFound = "10000F";
		assertThrows(NotFoundException.class, ()->grupoPersistanceJPA.readByNombre(nombreGrupoNotFound));
		
		
	}
	
	@Test
	void existNombre() {
		assertTrue( grupoPersistanceJPA.existNombre("1A") );
		assertTrue( grupoPersistanceJPA.existNombre("4C") );
		
		assertFalse( grupoPersistanceJPA.existNombre("1") );
		assertFalse( grupoPersistanceJPA.existNombre("4") );
		
	}

}
