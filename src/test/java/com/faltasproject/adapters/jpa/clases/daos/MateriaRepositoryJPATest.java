package com.faltasproject.adapters.jpa.clases.daos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

@SpringBootTest
class MateriaRepositoryJPATest {
	@Autowired
	private MateriaRepositoryJPA materiaRepositoryJPA;
	
	@Test
	void findByNombreCompletoContaining() {
		int expected=1;
		List<MateriasEntity> materias=materiaRepositoryJPA.findByNombreCompletoContainingIgnoreCase("mat");
		assertEquals(expected, materias.size());
		
		expected=5;
		materias=materiaRepositoryJPA.findByNombreCompletoContainingIgnoreCase("A");
		assertEquals(expected, materias.size());
	}
	
	@Test
	void findByReferencia(){
		Optional<MateriasEntity> materia = materiaRepositoryJPA.findByReferencia("5000");
		assertFalse(materia.isPresent());

		
		materia = materiaRepositoryJPA.findByReferencia("01");
		assertTrue(materia.isPresent());
		assertEquals("FyQ", materia.get().getNombreAbreviado());	
		assertEquals("Fisica y quimica", materia.get().getNombreCompleto());
	}
	
	@Test
	void deleteByReferencia() {
		MateriasEntity materia=new MateriasEntity("100","ML","Machine Learning");
		materiaRepositoryJPA.save(materia);
		
		Optional<MateriasEntity> materiaResult = materiaRepositoryJPA.findByReferencia("100");
		assertTrue(materiaResult.isPresent());
		
		materiaRepositoryJPA.deleteByReferencia("100");
		
		materiaResult = materiaRepositoryJPA.findByReferencia("100");
		assertFalse(materiaResult.isPresent());
		
	}
	
}
