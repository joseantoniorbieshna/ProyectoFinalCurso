package com.faltasproject.adapters.jpa.horario.daos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.horario.entities.FaltaEntity;

@SpringBootTest
class FaltaHoraHorarioRepositoryJPATest {

	@Autowired
	private FaltaRepositoryJPA faltaRepositoryJPA;
	
	@Test
	void findByFecha() {
		
		LocalDate fecha = LocalDate.of(2024,04,5);
		List<FaltaEntity> faltas = faltaRepositoryJPA.findByKeyFecha(fecha);
		
		long expected=1;
		assertEquals(expected, faltas.stream().count());
	}
	
	@Test
	void findBetweenFechas() {
		
		LocalDate fecha = LocalDate.of(2024,04,1);
		LocalDate fecha2 = LocalDate.of(2024,04,8);
		List<FaltaEntity> faltas = faltaRepositoryJPA.findByKeyFechaBetween(fecha,fecha2);
		
		long expected=3;
		assertEquals(expected, faltas.stream().count());
	}

}
