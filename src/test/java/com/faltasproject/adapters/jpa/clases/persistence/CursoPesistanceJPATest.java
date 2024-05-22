package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Materia;

import jakarta.transaction.Transactional;

@SpringBootTest
class CursoPesistanceJPATest {

	@Autowired
	CursoPersistanceJPA cursoPersistanceJPA;
	@Autowired
	MateriaPersistenceJPA materiaPersistenceJPA;
	@Autowired
	GrupoPersistanceJPA grupoPersistanceJPA;

	@Test
	void testReadNotFound() {
		 assertThrows(NotFoundException.class, () -> this.cursoPersistanceJPA.readByReferencia("0"));
	}
	
	@Test
    void update() {
		final String referenciaUpdate = "500";
		final Curso updateCurso = new Curso(referenciaUpdate,"Curso 1");
    	assertThrows( NotFoundException.class, () -> cursoPersistanceJPA.update(referenciaUpdate,updateCurso ) );

    	String referencia="1";
    	Curso curso = cursoPersistanceJPA.readByReferencia(referencia);
    	assertNotEquals(0, curso.getMaterias().size());
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso(referencia,"44º E.S.O"));
    	assertEquals("44º E.S.O", curso.getNombre());
    	assertEquals(0, curso.getMaterias().size());
    	
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso(referencia,"4º E.S.O", Sets.set( new Materia("01"),new Materia("02"),new Materia("03") )));
    	assertEquals("4º E.S.O", curso.getNombre());
    	assertEquals(3, curso.getMaterias().size());
    }
	
	@Test
	void create() {	
		String referencia="1000";
		String nombreCurso = "CURSO TEMPORAL";
		Curso curso = new Curso(referencia,nombreCurso);
		Set<Materia> materias = new HashSet<>();
		materias.add( materiaPersistenceJPA.readByReferencia("01") );
		materias.add( materiaPersistenceJPA.readByReferencia("02") );
		materias.add( materiaPersistenceJPA.readByReferencia("05") );
		curso.setMaterias(materias);
		
		cursoPersistanceJPA.create(curso);
		assertTrue(cursoPersistanceJPA.existReferencia(referencia));
		Curso cursoResult = cursoPersistanceJPA.readByReferencia(referencia);
		assertEquals(referencia, cursoResult.getReferencia());
		assertEquals(nombreCurso, cursoResult.getNombre());
		assertEquals(curso, cursoResult);
		
		//VOLVER AL ESTADO DE ANTES
		cursoPersistanceJPA.delete(referencia);
		assertFalse(cursoPersistanceJPA.existReferencia(referencia));
		
		//COMPROBAMOS QUE NO SE HAYA BORRADO EN CASCADA
		assertTrue(materiaPersistenceJPA.existReferencia("01"));
		assertTrue(materiaPersistenceJPA.existReferencia("02"));
		assertTrue(materiaPersistenceJPA.existReferencia("03"));
	}
	
	@Test
	void readAll() {
		Stream<Curso> cursos = cursoPersistanceJPA.readAll();
		assertEquals(5, cursos.count());
		
	}
	
	@Test
	void readContainInCompleteName() {
	Stream<Curso> containInCompleteName = cursoPersistanceJPA.readContainInCompleteName("E.S.O");
	Long expected=3L;
	assertEquals(expected, containInCompleteName.count());
	
	cursoPersistanceJPA.readContainInCompleteName("E.S.O")
		.forEach(curso -> assertTrue( curso.getNombre().contains("E.S.O") ) );
	
	}
	
	@Test
	void delete() {
		// EXCEPCION
		assertThrows(NotFoundException.class, ()->cursoPersistanceJPA.delete("1000"));
		//TODO
		String referenciaCurso="1000";
		String nombreCurso = "CURSO TEMPORAL";
		Curso cursoCreate = new Curso(referenciaCurso,nombreCurso);
		Set<Materia> materias = new HashSet<>();
		String referenciaMateria1="101";
		String referenciaMateria2="102";
		String referenciaMateria3="103";
		
		Materia materia1Persist = materiaPersistenceJPA.create(new Materia(referenciaMateria1,"nombreabreviado1","nombrecompleto1"));
		Materia materia2Persist = materiaPersistenceJPA.create(new Materia(referenciaMateria2,"nombreabreviado2","nombrecompleto2"));
		Materia materia3persist = materiaPersistenceJPA.create(new Materia(referenciaMateria3,"nombreabreviado3","nombrecompleto3"));
		
		materias.add( materia1Persist );
		materias.add( materia2Persist );
		materias.add( materia3persist );
		cursoCreate.setMaterias(materias);
		cursoPersistanceJPA.create(cursoCreate);
		
		
		
		// PROBAMOS A BORRAR MATERIA Y QUE NO SE BORRE CURSO
		Curso curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		int materiaExpected=3;
		assertEquals(materiaExpected, curso.getMaterias().size());
		//BORRAMOS UNA MATERIA
		materiaPersistenceJPA.delete(referenciaMateria1);
		assertFalse(materiaPersistenceJPA.existReferencia(referenciaMateria1));
		
		materiaExpected=2;
		curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		assertEquals(materiaExpected, curso.getMaterias().size());
		
		
		
		
		//PROBAMOS A BORRAR CURSO
		assertTrue(cursoPersistanceJPA.existReferencia(referenciaCurso));
		materias = cursoPersistanceJPA.readByReferencia(referenciaCurso).getMaterias();
		
		boolean result = cursoPersistanceJPA.delete(referenciaCurso);
		assertTrue(result);
		assertFalse(cursoPersistanceJPA.existReferencia(referenciaCurso));
		
		// Comprobamos si existe las referencias de materias y no se hayan borrado
		assertTrue(materiaPersistenceJPA.existReferencia(referenciaMateria2));
		assertTrue(materiaPersistenceJPA.existReferencia(referenciaMateria3));
		
		
		//BORRAMOS LAS MATERIAS
		materiaPersistenceJPA.delete(referenciaMateria2);
		assertFalse(materiaPersistenceJPA.existReferencia(referenciaMateria2));
		materiaPersistenceJPA.delete(referenciaMateria3);
		assertFalse(materiaPersistenceJPA.existReferencia(referenciaMateria3));
		
		assertFalse(materiaPersistenceJPA.existReferencia(referenciaMateria1));
		assertFalse(materiaPersistenceJPA.existReferencia(referenciaMateria2));
		assertFalse(materiaPersistenceJPA.existReferencia(referenciaMateria3));
		
	}
	
	@Test
	void readByReferencia() {
		assertThrows(NotFoundException.class, ()->cursoPersistanceJPA.readByReferencia("199"));
		
		//1º
		Curso curso=cursoPersistanceJPA.readByReferencia("1");
		String expected = "4º E.S.O";
		Integer sizeMateriaExpected =3;
		assertEquals(expected, curso.getNombre());
		assertEquals(sizeMateriaExpected, curso.getMaterias().size());
		
		//2º
		curso=cursoPersistanceJPA.readByReferencia("3");
		expected = "3º E.S.O";
		sizeMateriaExpected =1;
		assertEquals(expected, curso.getNombre());
		assertEquals(sizeMateriaExpected, curso.getMaterias().size());
		
	}

	@Test
	void existReferencia() {
		assertTrue(cursoPersistanceJPA.existReferencia("1"));
		assertTrue(cursoPersistanceJPA.existReferencia("5"));
		
		assertFalse(cursoPersistanceJPA.existReferencia("0"));
		assertFalse(cursoPersistanceJPA.existReferencia("6"));
	}

}
