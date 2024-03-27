package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Materia;

@SpringBootTest
public class CursoPersistanceJpaTest {
	
	@Autowired
	CursoPersistanceJPA cursoPersistanceJPA;
	@Autowired
	MateriaPersistenceJPA materiaPersistenceJPA;

	@Test
	void testReadNotFound() {
		 assertThrows(NotFoundException.class, () -> this.cursoPersistanceJPA.readByReferencia(0L));
	}
	
	@Test
    void update() {
    	assertThrows( NotFoundException.class, () -> cursoPersistanceJPA.update(500L, new Curso("Curso 1")) );

    	Long referencia=1L;
    	Curso curso = cursoPersistanceJPA.readByReferencia(referencia);
    	assertNotEquals(0, curso.getMaterias().size());
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso("44º E.S.O"));
    	assertEquals("44º E.S.O", curso.getNombre());
    	assertEquals(0, curso.getMaterias().size());
    	
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso("4º E.S.O", Arrays.asList( new Materia("01"),new Materia("02"),new Materia("03") )));
    	assertEquals("4º E.S.O", curso.getNombre());
    	assertEquals(3, curso.getMaterias().size());
    }
	
	@Test
	void create() {
		//TODO
		Long referencia=1000L;
		Curso curso = new Curso(referencia,"CURSO TEMPORAL");
		List<Materia> materias = new ArrayList<>();
		materias.add( materiaPersistenceJPA.readByReferencia("01") );
		materias.add( materiaPersistenceJPA.readByReferencia("02") );
		materias.add( materiaPersistenceJPA.readByReferencia("05") );
		curso.setMaterias(materias);
		
		cursoPersistanceJPA.create(curso);
		cursoPersistanceJPA.existReferencia(referencia);
		curso = cursoPersistanceJPA.readByReferencia(referencia);
		assertEquals(referencia, curso.getReferencia());
		assertEquals("CURSO TEMPORAL", curso.getNombre());
		
		cursoPersistanceJPA.delete(referencia);
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
		assertThrows(NotFoundException.class, ()->materiaPersistenceJPA.delete("FFF"));
		// PROBAMOS A BORRAR MATERIA Y QUE NO SE BORRE CURSO
		Long referenciaCurso=2L;
		Curso curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		assertEquals(2, curso.getMaterias().size());
		//BORRAMOS UNA MATERIA
		Boolean response=materiaPersistenceJPA.delete("05");
		assertTrue(response);
		
		curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		assertEquals(1, curso.getMaterias().size());
		
		// VOLVEMOS AL ESTADO ANTERIOR
		Materia materia = materiaPersistenceJPA.create(new Materia("05","Fi","Filosofia"));
		List<Materia> materiasSave = curso.getMaterias();
		materiasSave.add(materia);
		cursoPersistanceJPA.update(referenciaCurso,curso);
		curso=cursoPersistanceJPA.readByReferencia(referenciaCurso);
		assertEquals(2, curso.getMaterias().size());
		
		
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
