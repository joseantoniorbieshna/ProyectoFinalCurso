package com.faltasproject.adapters.jpa.clases.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;

@SpringBootTest
public class CursoRepositoryJPATest {

	@Autowired
	private CursoRepositoryJPA cursoRepositoryJPA;
	
	@Test
	public void findByNombreContaining() {
		int expected=3;
		List<CursoEntity> cursos=cursoRepositoryJPA.findByNombreContainingIgnoreCase("E.S.O");
		assertEquals(expected, cursos.size());
		
		expected=2;
		cursos=cursoRepositoryJPA.findByNombreContainingIgnoreCase("1");
		assertEquals(expected, cursos.size());
	}
	
}
