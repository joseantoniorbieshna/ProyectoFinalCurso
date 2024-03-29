package com.faltasproject.adapters.jpa.clases.daos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;


@SpringBootTest
class GrupoRepositoryJPATest {

	@Autowired
	GrupoRepositoryJPA grupoRepositoryJPA;
	@Autowired
	CursoRepositoryJPA cursoRepositoryJPA;
	
	@Test	
	void findByNombreEquals() {
		String nombre="1A";
		assertTrue(grupoRepositoryJPA.findByNombreEquals(nombre).isPresent());

		nombre="1";
		assertFalse(grupoRepositoryJPA.findByNombreEquals(nombre).isPresent());
	}
	@Test	
	void findByNombreContainingIgnoreCase() {
		int expected = 3;
		String search="1";
		assertEquals(expected, grupoRepositoryJPA.findByNombreContainingIgnoreCase(search).stream().count());

		expected = 2;
		search="a";
		assertEquals(expected, grupoRepositoryJPA.findByNombreContainingIgnoreCase(search).stream().count());
	}
	@Test	
	void deleteByNombreEquals() {
		String nombre="1A";
		Optional<GrupoEntity> grupo = grupoRepositoryJPA.findByNombreEquals(nombre);
		assertTrue(grupo.isPresent());
		Long referenciaCurso = grupo.get().getCurso().getReferencia();
		
		
		grupoRepositoryJPA.deleteByNombreEquals(nombre);
		assertFalse(grupoRepositoryJPA.findByNombreEquals(nombre).isPresent());
		Optional<CursoEntity> curso = cursoRepositoryJPA.findByReferencia(referenciaCurso);
		assertTrue(curso.isPresent());
		
		//VOLVER AL ESTADO DE ANTES
		//No los pone  
		grupoRepositoryJPA.save(new GrupoEntity(nombre,curso.get()));
		assertTrue(grupoRepositoryJPA.findByNombreEquals(nombre).isPresent());
		assertTrue(cursoRepositoryJPA.findByReferencia(referenciaCurso).isPresent());
	
		
	}

}
