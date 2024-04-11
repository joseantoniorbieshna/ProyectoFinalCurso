package com.faltasproject.adapters.jpa.profesorado.persistance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.adapters.jpa.profesorado.persistence.ProfesorPersistanceJPA;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.profesorado.Profesor;

@SpringBootTest
class ProfesorPersistanceJPATest {

	@Autowired
	private ProfesorPersistanceJPA profesoradoPersistance;
	
	@Test
	void create() {
		Profesor profesorError = new Profesor("01","Jose");
		assertThrows( ConflictException.class, ()-> profesoradoPersistance.create(profesorError) );
		
		//CREAMOS
		String referenciaProfesor="10";
		String nombreProfesor="Jaimito";
		Profesor profesor = new Profesor(referenciaProfesor,nombreProfesor);
		profesor = profesoradoPersistance.create(profesor);
		assertEquals(referenciaProfesor, profesor.getReferencia());
		assertEquals(nombreProfesor, profesor.getNombre());
		
		//VOLVEMOS AL ESTADO DE ANTES
		assertTrue( profesoradoPersistance.delete(referenciaProfesor) );
		
		assertFalse( profesoradoPersistance.existReferencia(referenciaProfesor) );
	}
	
	@Test
	void readAll() {
		long total=profesoradoPersistance.readAll().count();
		long expected = 5;
		assertEquals(expected, total);
	}
	
	@Test
	void existReferencia() {
		assertTrue( profesoradoPersistance.existReferencia("01") );
		assertTrue( profesoradoPersistance.existReferencia("05") );

		assertFalse( profesoradoPersistance.existReferencia("00") );
		assertFalse( profesoradoPersistance.existReferencia("06") );
	}
	
	@Test
	void readByReferencia() {
		String referencia = "01";
		String nombreExpected = "Jose";
		Profesor profesor = profesoradoPersistance.readByReferencia(referencia);
		assertEquals(referencia, profesor.getReferencia());
		assertEquals(nombreExpected, profesor.getNombre());
		
		
		referencia = "05";
		nombreExpected = "Fernando";
		profesor = profesoradoPersistance.readByReferencia(referencia);
		assertEquals(referencia, profesor.getReferencia());
		assertEquals(nombreExpected, profesor.getNombre());
		
		
		// ERROR
		String referenciaError="00";
		assertThrows( NotFoundException.class, ()-> profesoradoPersistance.readByReferencia(referenciaError) );
	}
	
	
	@Test
	void update() {
		String referenciaDefault="01";
		String nombreDefault="Jose";
		Profesor profesor = profesoradoPersistance.readByReferencia(referenciaDefault);
		assertEquals(referenciaDefault, profesor.getReferencia());
		assertEquals(nombreDefault, profesor.getNombre());
		
		//UPDATE
		String referenciaNueva="10";
		Profesor profesorUpdate = new Profesor(referenciaNueva);
		profesoradoPersistance.update(referenciaDefault, profesorUpdate);
		
		//COMPROBAMOS
		profesor = profesoradoPersistance.readByReferencia(referenciaNueva);
		assertEquals(referenciaNueva, profesor.getReferencia());
		assertEquals(nombreDefault, profesor.getNombre());
		
		// UPDATE
		String nuevoNombre = "Alejandro";
		profesorUpdate = new Profesor(referenciaNueva,nuevoNombre);
		profesor=profesoradoPersistance.update(referenciaNueva, profesorUpdate);
		assertEquals(referenciaNueva, profesor.getReferencia());
		assertEquals(nuevoNombre, profesor.getNombre());
		
		// VOLVEMOS AL ESTADO ANTERIOR
		profesorUpdate = new Profesor(referenciaDefault,nombreDefault);
		profesor=profesoradoPersistance.update(referenciaNueva, profesorUpdate);
		assertEquals(referenciaDefault, profesor.getReferencia());
		assertEquals(nombreDefault, profesor.getNombre());
		
		// ERROR 
		final String referenciaError="100";
		final Profesor profesorError= new Profesor(referenciaError,"aaa");
		assertThrows(NotFoundException.class, ()-> profesoradoPersistance.update(referenciaError,profesorError) );
		
	}
	
	@Test
	void delete() {
		//ERROR
		assertThrows(NotFoundException.class, ()-> profesoradoPersistance.delete("100") );
		
		// CREAMOS Y BORRAMOS
		String referencia="100";
		Profesor profesor = new Profesor(referencia,"Menganito");
		profesor = profesoradoPersistance.create(profesor);
		
		assertTrue( profesoradoPersistance.existReferencia(referencia) );
		assertTrue( profesoradoPersistance.delete(referencia) );
		assertFalse( profesoradoPersistance.existReferencia(referencia) );
		
	}
	

}
