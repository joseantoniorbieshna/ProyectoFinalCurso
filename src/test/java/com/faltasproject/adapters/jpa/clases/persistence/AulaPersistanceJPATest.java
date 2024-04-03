package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Aula;

@SpringBootTest
class AulaPersistanceJPATest {
	
	@Autowired
	AulaPersistanceJPA aulaPersistanceJPA;

	@Test
	void update() {
		String referencia="3";
		String referenciaExpected="100";
		String nombreExpected="AULA TRASERA";
		Aula aula = aulaPersistanceJPA.update(referencia, new Aula(referenciaExpected,"AULA TRASERA"));
		assertEquals(referenciaExpected, aula.getReferencia());
		assertEquals(nombreExpected, aula.getNombre());
		
		//VOLVER AL VALOR ANTERIOR
		referencia="100";
		referenciaExpected="3";
		nombreExpected="AULA C";
		aula = aulaPersistanceJPA.update(referencia, new Aula(referenciaExpected,"AULA C"));
		assertEquals(referenciaExpected, aula.getReferencia());
		assertEquals(nombreExpected, aula.getNombre());
	}

	@Test
	void create() {
		final Aula aulaUpdate = new Aula("1","AULA A");
		assertThrows(ConflictException.class, ()->aulaPersistanceJPA.create(aulaUpdate));
		
		String referencia="10";
		String nombreAula="AULA TEMPORAL";
		Aula aula = new Aula(referencia,nombreAula);
		aulaPersistanceJPA.create(aula);
		
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		
		Aula aulaResult = aulaPersistanceJPA.readByReferencia(referencia);
		assertEquals(referencia, aulaResult.getReferencia());
		assertEquals(nombreAula, aulaResult.getNombre());
		assertEquals(aula, aulaResult);
		
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
		String referencia="10";
		String nombreAula="AULA TEMPORAL";
		aulaPersistanceJPA.create(new Aula(referencia,nombreAula));
		
		
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		aulaPersistanceJPA.delete(referencia);
		assertFalse(aulaPersistanceJPA.existReferencia(referencia));
		
		// 2º
		referencia="11";
		nombreAula="AULA TEMPORAL2";
		aulaPersistanceJPA.create(new Aula(referencia,nombreAula));
		
		
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		aulaPersistanceJPA.delete(referencia);
		assertFalse(aulaPersistanceJPA.existReferencia(referencia));
		
		assertThrows(NotFoundException.class, ()->aulaPersistanceJPA.delete("11"));
		
	}
	
	@Test
	void readByReferencia() {
		String referencia="1";
		Aula aula = aulaPersistanceJPA.readByReferencia(referencia);
		String expectedName="AULA A";
		assertEquals(expectedName, aula.getNombre());
		assertEquals(referencia, aula.getReferencia());

		referencia="5";
		aula = aulaPersistanceJPA.readByReferencia(referencia);
		expectedName="AULA E";
		assertEquals(expectedName, aula.getNombre());
		assertEquals(referencia, aula.getReferencia());
	}
	
	@Test
	void existReferencia() {
		String referencia="1";
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		referencia="5";
		assertTrue(aulaPersistanceJPA.existReferencia(referencia));
		
		referencia="0";
		assertFalse(aulaPersistanceJPA.existReferencia(referencia));
		referencia="6";
		assertFalse(aulaPersistanceJPA.existReferencia(referencia));
	}

}
