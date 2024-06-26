package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Materia;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class MateriaPersistanceJPATest {

	
	@Autowired
	MateriaPersistenceJPA materiaPersistenceJPA;
	
    @Test
    void testReadNotFound() {
        assertThrows(NotFoundException.class, () -> this.materiaPersistenceJPA.readByReferencia("0"));
    }
    
    @Test
    void update() {
    	final String referenciaUpdate="-01";
    	final Materia materiaUpdate =  new Materia("Bd", "Base de datos");
    	assertThrows( NotFoundException.class, () -> materiaPersistenceJPA.update(referenciaUpdate, materiaUpdate));

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
    	final Materia materiaUpdate = new Materia("01", "PM","Programación");
    	assertThrows(ConflictException.class, () -> materiaPersistenceJPA.create(materiaUpdate));
    	
    	String referencia="FF";
    	Materia materiaCreate = new Materia(referencia, "PM2","Programación 2");
    	materiaPersistenceJPA.create(materiaCreate);
    	
    	
    	Materia materiaResult = materiaPersistenceJPA.readByReferencia(referencia);
    	assertEquals("FF", materiaResult.getReferencia());
    	assertEquals("PM2", materiaResult.getNombreAbreviado());
    	assertEquals("Programación 2", materiaResult.getNombreCompleto());
    	assertEquals(materiaCreate, materiaResult);
    	
    	//VOLVER AL ESTADO DE ANTES
    	materiaPersistenceJPA.delete(referencia);
    	assertFalse(materiaPersistenceJPA.existReferencia(referencia));
    	// ERROR
    	assertThrows(NotFoundException.class, () -> materiaPersistenceJPA.readByReferencia(referencia));
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
    	// 1º
    	String referencia="100F";
    	Materia materia =new Materia(referencia);
    	materiaPersistenceJPA.create(materia);
    	
    	assertTrue(materiaPersistenceJPA.existReferencia(referencia));
    	materiaPersistenceJPA.delete(referencia);
    	assertFalse(materiaPersistenceJPA.existReferencia(referencia));
    	
    	//2º
    	final String referenciaExceptionExist="01";
    	final Materia materiaExceptionExist = new Materia(referenciaExceptionExist);
    	assertThrows(ConflictException.class, ()->materiaPersistenceJPA.create(materiaExceptionExist));
	}
    
    @Test
	void readByReferencia() {
    	Materia materia = materiaPersistenceJPA.readByReferencia("01");
    	assertEquals("Fisica y quimica", materia.getNombreCompleto());
    	assertEquals("FyQ", materia.getNombreAbreviado());
    	assertEquals("01", materia.getReferencia());
    	
    	materia = materiaPersistenceJPA.readByReferencia("05");
    	assertEquals("Filosofia", materia.getNombreCompleto());
    	assertEquals("Fi", materia.getNombreAbreviado());
    	assertEquals("05", materia.getReferencia());
    	
    	assertThrows(NotFoundException.class,()->materiaPersistenceJPA.readByReferencia("JDF"));
	}
    
    @Test
	void existReferencia() {
    	assertTrue(materiaPersistenceJPA.existReferencia("01"));
    	assertTrue(materiaPersistenceJPA.existReferencia("05"));

    	assertFalse(materiaPersistenceJPA.existReferencia("00"));
    	assertFalse(materiaPersistenceJPA.existReferencia("06"));
	}
    
   

}
