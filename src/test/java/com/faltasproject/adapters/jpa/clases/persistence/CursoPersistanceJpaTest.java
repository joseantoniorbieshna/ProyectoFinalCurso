package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.DatabaseSeederService;
import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Materia;

@SpringBootTest
public class CursoPersistanceJpaTest {
	
	@Autowired
	CursoPersistanceJPA cursoPersistanceJPA;
	@Autowired
	MateriaPersistenceJPA materiaPersistenceJPA;
	@Autowired
	DatabaseSeederService databaseSeederService;

	@Test
	void testReadNotFound() {
		 assertThrows(NotFoundException.class, () -> this.cursoPersistanceJPA.readByReferencia(0L));
	}
	
	@Test
    void update() {
    	assertThrows( NotFoundException.class, () -> cursoPersistanceJPA.update(500L, new Curso("Curso 1")) );

    	Long referencia=1L;
    	Curso curso = cursoPersistanceJPA.readByReferencia(referencia);
    	assertNotEquals(0, curso.getMaterias().size());
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso("44º E.S.O"));
    	assertEquals("44º E.S.O", curso.getNombre());
    	assertEquals(0, curso.getMaterias().size());
    	
    	
    	curso = cursoPersistanceJPA.update(referencia, new Curso("4º E.S.O", Arrays.asList( new Materia("01"),new Materia("02"),new Materia("03") )));
    	assertEquals("4º E.S.O", curso.getNombre());
    	assertEquals(3, curso.getMaterias().size());
    }
	
	@Test
	void create() {
		//TODO
	}
	
	@Test
	void readAll() {
		//TODO
	}
	
	@Test
	void readContainInCompleteName() {
		//TODO
	}
	
	@Test
	void delete() {
//		new CursoEntity(2L,"1º Bachillerato(Arte)");
//		new MateriasEntity("05","Fi","Filosofia");
		
		Curso curso=cursoPersistanceJPA.readByReferencia(2L);
		assertEquals(2, curso.getMaterias().size());
		//BORRAMOS UNA MATERIA
		Boolean response=materiaPersistenceJPA.delete("05");
		assertTrue(response);
		
		curso=cursoPersistanceJPA.readByReferencia(2L);
		assertEquals(1, curso.getMaterias().size());
		
		//VOLVEMOS AL ESTADO ANTERIOR
		//TODO hay que hacerlo bien en vez de resetear la base de datos
		//Así estaría tardando más
		databaseSeederService.reSeedDatabase();
		
		
	}
	
	@Test
	void readByReferencia() {
		//TODO
	}

	@Test
	void existReferencia() {
		//TODO
	}
	
}
