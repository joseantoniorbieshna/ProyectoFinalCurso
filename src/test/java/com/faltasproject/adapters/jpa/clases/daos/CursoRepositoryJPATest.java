package com.faltasproject.adapters.jpa.clases.daos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;

import jakarta.transaction.Transactional;

@SpringBootTest
class CursoRepositoryJPATest {

	@Autowired
	private CursoRepositoryJPA cursoRepositoryJPA;
		
	@Test
	void findByNombreContainingIgnoreCase() {
		int expected=3;
		List<CursoEntity> cursos=cursoRepositoryJPA.findByNombreContainingIgnoreCase("E.S.O");
		assertEquals(expected, cursos.size());
		
		expected=2;
		cursos=cursoRepositoryJPA.findByNombreContainingIgnoreCase("1");
		assertEquals(expected, cursos.size());
	}
	@Test
	void findByReferencia(){
		Optional<CursoEntity> curso = cursoRepositoryJPA.findByReferencia(-1L);
		assertFalse(curso.isPresent());
		
		curso = cursoRepositoryJPA.findByReferencia(1L);
		assertTrue(curso.isPresent());
		assertEquals("4º E.S.O", curso.get().getNombre());
		assertEquals(3, curso.get().getMaterias().size());
	}
	@Test
	@Transactional
	void deleteByReferencia() {
		CursoEntity cursoEntity = new CursoEntity(10000L,"4º CARRERA");
		cursoRepositoryJPA.save(cursoEntity);
		
		Optional<CursoEntity> cursoResult=cursoRepositoryJPA.findByReferencia(10000L);
		assertTrue(cursoResult.isPresent());
		
		cursoRepositoryJPA.deleteByReferencia(10000L);
		
		cursoResult=cursoRepositoryJPA.findByReferencia(10000L);
		assertFalse(cursoResult.isPresent());
	}

}
