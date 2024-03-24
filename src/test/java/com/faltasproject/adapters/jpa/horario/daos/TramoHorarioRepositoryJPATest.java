package com.faltasproject.adapters.jpa.horario.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.KeyTramoHorario;

@SpringBootTest
public class TramoHorarioRepositoryJPATest {
	
	@Autowired
	TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	
	@Test
	void test() {
		Optional<TramoHorarioEntity> tramoHorarioEntity=tramoHorarioRepositoryJPA.findById(new KeyTramoHorario(0, 1));
		assertTrue(tramoHorarioEntity.isPresent());
	}

}
