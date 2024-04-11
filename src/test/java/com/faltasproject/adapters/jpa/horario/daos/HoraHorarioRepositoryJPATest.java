package com.faltasproject.adapters.jpa.horario.daos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HoraHorarioRepositoryJPATest {

	@Autowired
	HoraHorarioRepositoryJPA horaHorarioRepositoryJPA;
	
	@Test
	void findAllByProfesorFaltante() {
		long result=horaHorarioRepositoryJPA.findAllByProfesorFaltante("02").stream().count();
		long expected=3;
		assertEquals(expected,result);

		result=horaHorarioRepositoryJPA.findAllByProfesorFaltante("100").stream().count();
		expected=0;
		assertEquals(expected,result);
	}
	
	void findAllByReferenciaSesion() {
		long result=horaHorarioRepositoryJPA.findAllByReferenciaSesion("01").stream().count();
		long expected=3;
		assertEquals(expected,result);
		
		result=horaHorarioRepositoryJPA.findAllByReferenciaSesion("03").stream().count();
		expected=0;
		assertEquals(expected,result);

		result=horaHorarioRepositoryJPA.findAllByReferenciaSesion("100").stream().count();
		expected=0;
		assertEquals(expected,result);
	}

}
