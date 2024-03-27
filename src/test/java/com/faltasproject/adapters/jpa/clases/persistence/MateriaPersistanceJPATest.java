package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Materia;

@SpringBootTest
public class MateriaPersistanceJPATest { 
	
	@Autowired
	MateriaPersistenceJPA materiaPersistenceJPA;
	
    @Test
    void testReadNotFound() {
        assertThrows(NotFoundException.class, () -> this.materiaPersistenceJPA.readByReferencia("0"));
    }
    
    @Test
    void update() {
    	assertThrows( NotFoundException.class, () -> materiaPersistenceJPA.update("-01", new Materia("Bd", "Base de datos")) );

    	Materia materia = materiaPersistenceJPA.update("01", new Materia("10000","PM", "Programacion"));
    	assertEquals("10000", materia.getReferencia());
    	assertEquals("PM", materia.getNombreAbreviado());
    	assertEquals("Programacion", materia.getNombreCompleto());
    	
    	
    	materia = materiaPersistenceJPA.update("10000", new Materia("01","FyQ", "Fisica y quimica"));
    	assertEquals("01", materia.getReferencia());
    	assertEquals("FyQ", materia.getNombreAbreviado());
    	assertEquals("Fisica y quimica", materia.getNombreCompleto());
    }
    
    @Test
    void create() {
    	assertThrows(ConflictExceptions.class, () -> materiaPersistenceJPA.create(new Materia("01", "PM","Programación")));
    	
    	Materia materia = materiaPersistenceJPA.create(new Materia("FF", "PM2","Programación 2"));
    	assertEquals("FF", materia.getReferencia());
    	assertEquals("PM2", materia.getNombreAbreviado());
    	assertEquals("Programación 2", materia.getNombreCompleto());
    	
    	materiaPersistenceJPA.delete("FF");
    	assertThrows(NotFoundException.class, () -> materiaPersistenceJPA.readByReferencia("FF"));
    }
    
    @Test
	void readAll() {
    	assertEquals(5, materiaPersistenceJPA.readAll().count());
	}
	
    @Test
	void readContainInCompleteName() {
    	// 1º
    	assertEquals(3, materiaPersistenceJPA.readContainInCompleteName("e").count());
    	
    	// 2º
    	assertEquals(0, materiaPersistenceJPA.readContainInCompleteName("hola").count());
    	
    	// 3º
    	assertEquals(1, materiaPersistenceJPA.readContainInCompleteName("Filo").count());
    	Materia materia = materiaPersistenceJPA.readContainInCompleteName("Filo")
    			.map(t -> t).collect(Collectors.toList()).get(0);
    	assertEquals("Filosofia", materia.getNombreCompleto());
    	assertEquals("05", materia.getReferencia());
    }
	
    @Test
	void delete() {
    	//TODO
	}
    
    @Test
	void readByReferencia() {
		// TODO
	}
    
    @Test
	void existReferencia() {
    	assertTrue(materiaPersistenceJPA.existReferencia("01"));
    	assertTrue(materiaPersistenceJPA.existReferencia("05"));

    	assertFalse(materiaPersistenceJPA.existReferencia("00"));
    	assertFalse(materiaPersistenceJPA.existReferencia("06"));
	}
    
    

}
