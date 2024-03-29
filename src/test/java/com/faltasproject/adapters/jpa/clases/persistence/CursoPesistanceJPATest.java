package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;

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
		 assertThrows(NotFoundException.class, () -> this.cursoPersistanceJPA.readByReferencia(0L));
	}
	
	@Test
    void update() {
		final Long referenciaUpdate = 500L;
		final Curso updateCurso = new Curso("Curso 1");
    	assertThrows( NotFoundException.class, () -> cursoPersistanceJPA.update(referenciaUpdate,updateCurso ) );

    	Long referencia=1L;
    	Curso curso = cursoPersistanceJPA.readByReferencia(referencia);
    	assertNotEquals(0, curso.getMaterias().size());
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso("44º E.S.O"));
    	assertEquals("44º E.S.O", curso.getNombre());
    	assertEquals(0, curso.getMaterias().size());
    	
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso("4º E.S.O", Sets.set( new Materia("01"),new Materia("02"),new Materia("03") )));
    	assertEquals("4º E.S.O", curso.getNombre());
    	assertEquals(3, curso.getMaterias().size());
    }
	
	@Test
	void create() {
		//TODO
		Long referencia=1000L;
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
		assertThrows(NotFoundException.class, ()->cursoPersistanceJPA.delete(1000L));
		// PROBAMOS A BORRAR MATERIA Y QUE NO SE BORRE CURSO
		Long referenciaCurso=2L;
		Curso curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		int materiaExpected=2;
		assertEquals(materiaExpected, curso.getMaterias().size());
		//BORRAMOS UNA MATERIA
		Boolean response=materiaPersistenceJPA.delete("05");
		materiaExpected=1;
		assertTrue(response);
		
		curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		assertEquals(materiaExpected, curso.getMaterias().size());
		
		// VOLVEMOS AL ESTADO ANTERIOR
		Materia materia = materiaPersistenceJPA.create(new Materia("05","Fi","Filosofia"));
		materiaExpected=2;
		Set<Materia> materiasSave = curso.getMaterias();
		materiasSave.add(materia);
		cursoPersistanceJPA.update(referenciaCurso,curso);
		curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		assertEquals(materiaExpected, curso.getMaterias().size());
		
		
		
		//PROBAMOS A BORRAR CURSO
		Long referencia=5L;
		assertTrue(cursoPersistanceJPA.existReferencia(referencia));
		Set<Materia> materias = cursoPersistanceJPA.readByReferencia(referencia).getMaterias();
		
		boolean result = cursoPersistanceJPA.delete(referencia);
		assertTrue(result);
		assertFalse(cursoPersistanceJPA.existReferencia(referencia));
		
		//VOLVEMOS AL ESTADO ANTERIOR
		curso=cursoPersistanceJPA.create( new Curso(referencia,"2º Bachillerato",
				materias.stream()
				.map(materiaIterator -> materiaPersistenceJPA.readByReferencia(materiaIterator.getReferencia()))
				.collect(Collectors.toSet()))
				);
		
		grupoPersistanceJPA.create(new Grupo("E4D",curso));
		assertTrue(cursoPersistanceJPA.existReferencia(referencia));
		assertEquals(curso,grupoPersistanceJPA.readByNombre("E4D").getCurso());
		
	}
	
	@Test
	void readByReferencia() {
		assertThrows(NotFoundException.class, ()->cursoPersistanceJPA.readByReferencia(199L));
		
		//1º
		Curso curso=cursoPersistanceJPA.readByReferencia(1L);
		String expected = "4º E.S.O";
		Integer sizeMateriaExpected =3;
		assertEquals(expected, curso.getNombre());
		assertEquals(sizeMateriaExpected, curso.getMaterias().size());
		
		//2º
		curso=cursoPersistanceJPA.readByReferencia(3L);
		expected = "3º E.S.O";
		sizeMateriaExpected =1;
		assertEquals(expected, curso.getNombre());
		assertEquals(sizeMateriaExpected, curso.getMaterias().size());
		
	}

	@Test
	void existReferencia() {
		assertTrue(cursoPersistanceJPA.existReferencia(1L));
		assertTrue(cursoPersistanceJPA.existReferencia(5L));
		
		assertFalse(cursoPersistanceJPA.existReferencia(0L));
		assertFalse(cursoPersistanceJPA.existReferencia(6L));
	}

}
