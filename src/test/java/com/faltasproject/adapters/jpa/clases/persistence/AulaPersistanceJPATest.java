package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Aula;

@SpringBootTest
class AulaPersistanceJPATest {
	
	@Autowired
	AulaPersistanceJPA aulaPersistanceJPA;

	@Test
	void update() {
		Long referencia=3L;
		Long referenciaExpected=100L;
		String nombreExpected="AULA TRASERA";
		Aula aula = aulaPersistanceJPA.update(referencia, new Aula(referenciaExpected,"AULA TRASERA"));
		assertEquals(referenciaExpected, aula.getReferencia());
		assertEquals(nombreExpected, aula.getNombre());
		
		//VOLVER AL VALOR ANTERIOR
		referencia=100L;
		referenciaExpected=3L;
		nombreExpected="AULA C";
		aula = aulaPersistanceJPA.update(referencia, new Aula(referenciaExpected,"AULA C"));
		assertEquals(referenciaExpected, aula.getReferencia());
		assertEquals(nombreExpected, aula.getNombre());
	}

	@Test
	void create() {
		assertThrows(ConflictExceptions.class, ()->aulaPersistanceJPA.create(new Aula(1L,"AULA A")));
		
		Long referencia=10L;
		String nombreAula="AULA TEMPORAL";
		aulaPersistanceJPA.create(new Aula(referencia,nombreAula));
		
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		
		Aula aula = aulaPersistanceJPA.readByReferencia(referencia);
		assertEquals(referencia, aula.getReferencia());
		assertEquals(nombreAula, aula.getNombre());
		
		// REVERTIR CAMBIOS
		aulaPersistanceJPA.delete(referencia);
		assertFalse(aulaPersistanceJPA.existReferencia(referencia));
	}	
	
	@Test
	void readAll() {
		Stream<Aula> aulas = aulaPersistanceJPA.readAll();
		Long expected=5L;
		assertEquals(expected, aulas.count());
	}
	
	@Test
	void readContainInName() {
		String search="AuLa";
		Long sizeExpected=5L;
		Stream<Aula> aulas = aulaPersistanceJPA.readContainInName(search);
		assertEquals(sizeExpected, aulas.count());

		search="a a";
		sizeExpected=1L;
		aulas = aulaPersistanceJPA.readContainInName(search);
		assertEquals(sizeExpected, aulas.count());
	}
	
	@Test
	void delete() {

		// 1º
		Long referencia=10L;
		String nombreAula="AULA TEMPORAL";
		aulaPersistanceJPA.create(new Aula(referencia,nombreAula));
		
		
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		aulaPersistanceJPA.delete(referencia);
		assertFalse(aulaPersistanceJPA.existReferencia(referencia));
		
		// 2º
		referencia=11L;
		nombreAula="AULA TEMPORAL2";
		aulaPersistanceJPA.create(new Aula(referencia,nombreAula));
		
		
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		aulaPersistanceJPA.delete(referencia);
		assertFalse(aulaPersistanceJPA.existReferencia(referencia));
		
		assertThrows(NotFoundException.class, ()->aulaPersistanceJPA.delete(11L));
		
	}
	
	@Test
	void readByReferencia() {
		Long referencia=1L;
		Aula aula = aulaPersistanceJPA.readByReferencia(referencia);
		String expectedName="AULA A";
		assertEquals(expectedName, aula.getNombre());
		assertEquals(referencia, aula.getReferencia());

		referencia=5L;
		aula = aulaPersistanceJPA.readByReferencia(referencia);
		expectedName="AULA E";
		assertEquals(expectedName, aula.getNombre());
		assertEquals(referencia, aula.getReferencia());
	}
	
	@Test
	void existReferencia() {
		assertTrue(aulaPersistanceJPA.existReferencia(1L));
		assertTrue(aulaPersistanceJPA.existReferencia(5L));
		
		assertFalse(aulaPersistanceJPA.existReferencia(0L));
		assertFalse(aulaPersistanceJPA.existReferencia(6L));
	}

}
