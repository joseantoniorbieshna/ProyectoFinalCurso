package com.faltasproject.adapters.jpa.clases.daos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.adapters.jpa.clases.entities.SesionEntity;

import jakarta.transaction.Transactional;


@SpringBootTest
@Transactional
class GrupoRepositoryJPATest {

	@Autowired
	GrupoRepositoryJPA grupoRepositoryJPA;
	@Autowired
	CursoRepositoryJPA cursoRepositoryJPA;
	@Autowired
	SesionRepositoryJPA sesionRepositoryJPA;
	
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
		//CREACION
		String nombreGrupoNuevo = "5B";
		String referenciaCurso = "5";
		String sesionReferencia = "01";
		//CREAMOS CURSO
		CursoEntity curso = cursoRepositoryJPA.findByReferencia(referenciaCurso).get();
		GrupoEntity grupoEntity = new GrupoEntity(nombreGrupoNuevo,curso);
		grupoEntity = grupoRepositoryJPA.save(grupoEntity);
		
		// AÑADIMOS EL GRUPO A LA SESION
		SesionEntity sesionPersistance = sesionRepositoryJPA.findByReferencia(sesionReferencia).get();
		sesionPersistance.addGrupoEntity(grupoEntity);
		sesionPersistance = sesionRepositoryJPA.save(sesionPersistance);
		
		assertTrue(grupoRepositoryJPA.findByNombreEquals(nombreGrupoNuevo).isPresent());
		long expected=4L;
		assertEquals(expected, sesionRepositoryJPA.findByReferencia(sesionReferencia).get()
				.getGrupos().stream().count());
		assertEquals(referenciaCurso, grupoRepositoryJPA.findByNombreEquals(nombreGrupoNuevo).get()
				.getCurso()
				.getReferencia());
		// aquí sesiones si tiene el grupo pero el grupo tiene sesiones null
		assertTrue(grupoRepositoryJPA.findByNombreEquals(nombreGrupoNuevo).get().getSesiones().size()>0);
		
		//BORRAMOS Y COMPROBAMOS QUE LOS OTROS DATOS EXISTAN
		grupoRepositoryJPA.deleteByNombre(nombreGrupoNuevo);
		assertFalse(grupoRepositoryJPA.findByNombreEquals(nombreGrupoNuevo).isPresent());
		
		assertTrue(sesionRepositoryJPA.findByReferencia(sesionReferencia).isPresent());
		expected=3L;
		assertEquals(expected, sesionRepositoryJPA.findByReferencia(sesionReferencia).get()
				.getGrupos().stream().count());
		assertTrue(cursoRepositoryJPA.findByReferencia(referenciaCurso).isPresent());
		
	}

}
