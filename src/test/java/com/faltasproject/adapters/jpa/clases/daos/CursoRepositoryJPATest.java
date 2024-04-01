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
		String referencia="-1";
		Optional<CursoEntity> curso = cursoRepositoryJPA.findByReferencia(referencia);
		assertFalse(curso.isPresent());
		
		referencia="1";
		curso = cursoRepositoryJPA.findByReferencia(referencia);
		assertTrue(curso.isPresent());
		assertEquals("4º E.S.O", curso.get().getNombre());
		assertEquals(3, curso.get().getMaterias().size());
	}
	@Test
	@Transactional
	void deleteByReferencia() {
		String referencia="10000";
		CursoEntity cursoEntity = new CursoEntity(referencia,"4º CARRERA");
		cursoRepositoryJPA.save(cursoEntity);
		
		Optional<CursoEntity> cursoResult=cursoRepositoryJPA.findByReferencia(referencia);
		assertTrue(cursoResult.isPresent());
		
		cursoRepositoryJPA.deleteByReferencia(referencia);
		
		cursoResult=cursoRepositoryJPA.findByReferencia(referencia);
		assertFalse(cursoResult.isPresent());
	}

}
