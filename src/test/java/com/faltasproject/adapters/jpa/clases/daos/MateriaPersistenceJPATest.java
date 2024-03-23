package com.faltasproject.adapters.jpa.clases.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;


@SpringBootTest
public class MateriaPersistenceJPATest {
	@Autowired
	private MateriaRepositoryJPA materiaRepositoryJPA;
	
	@Test
	public void findByNombreCompletoContaining() {
		int expected=1;
		List<MateriasEntity> materias=materiaRepositoryJPA.findByNombreCompletoContainingIgnoreCase("mat");
		assertEquals(expected, materias.size());
		
		
		expected=4;
		materias=materiaRepositoryJPA.findByNombreCompletoContainingIgnoreCase("A");
		assertEquals(expected, materias.size());
	}
	
}
