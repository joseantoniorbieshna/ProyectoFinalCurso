package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;

@SpringBootTest
public class CursoPersistanceJpaTest {
	
	@Autowired
	CursoPersistanceJPA cursoPersistanceJPA;

	@Test
	void testReadNotFound() {
		 assertThrows(NotFoundException.class, () -> this.cursoPersistanceJPA.readByReferencia(0L));
	}
	
	@Test
    void update() {
    	assertThrows( NotFoundException.class, () -> cursoPersistanceJPA.update(500L, new Curso("Curso 1")) );

    	Curso curso = cursoPersistanceJPA.readByReferencia(1L);
    	assertNotEquals(0, curso.getMaterias().size());
    	
    	curso = cursoPersistanceJPA.update(1L, new Curso("44º E.S.O"));
    	assertEquals("44º E.S.O", curso.getNombre());
    	assertNotEquals(0, curso.getMaterias().size());
    	
    	
    	curso = cursoPersistanceJPA.update(2L, new Curso("4º E.S.O"));
    	assertEquals("4º E.S.O", curso.getNombre());
    	assertNotEquals(0, curso.getMaterias().size());
    }
	
}
