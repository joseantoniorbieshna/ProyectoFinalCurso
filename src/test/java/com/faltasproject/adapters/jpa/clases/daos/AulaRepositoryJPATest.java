package com.faltasproject.adapters.jpa.clases.daos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.AulaEntity;

import jakarta.transaction.Transactional;

@SpringBootTest
class AulaRepositoryJPATest {

	@Autowired
	AulaRepositoryJPA aulaRepositoryJPA;
	
	@Test
	void findByNombreContainingIgnoreCase() {
		List<AulaEntity> aulas = aulaRepositoryJPA.findByNombreContainingIgnoreCase("aUlA");
		Integer expected=5;
		assertNotNull(aulas);
		assertEquals(expected, aulas.size());
		
		aulas = aulaRepositoryJPA.findByNombreContainingIgnoreCase("A b");
		expected=1;
		assertNotNull(aulas);
		assertEquals(expected, aulas.size());
	}
	
	@Test
	void findByReferencia() {
		Optional<AulaEntity> aulaEntity = aulaRepositoryJPA.findByReferencia("2000");
		assertFalse(aulaEntity.isPresent());
		String referencia = "2";
		aulaEntity = aulaRepositoryJPA.findByReferencia(referencia);
		assertTrue(aulaEntity.isPresent());
		assertEquals("AULA B", aulaEntity.get().getNombre());
		
		referencia = "5";
		aulaEntity = aulaRepositoryJPA.findByReferencia(referencia);
		assertTrue(aulaEntity.isPresent());
		assertEquals("AULA E", aulaEntity.get().getNombre());
		
		
	}
	
	@Test
	@Transactional
	void deleteByReferencia() {
		String referencia="100";
		AulaEntity aulaEntity= new AulaEntity(referencia,"AULA MILLA");
		aulaRepositoryJPA.save(aulaEntity);
		
		Optional<AulaEntity> aulaResult = aulaRepositoryJPA.findByReferencia("5");
		assertTrue(aulaResult.isPresent());
		
		aulaRepositoryJPA.deleteByReferencia(referencia);
		
		aulaResult = aulaRepositoryJPA.findByReferencia(referencia);
		assertFalse(aulaResult.isPresent());
	}

}
