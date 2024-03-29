package com.faltasproject.adapters.jpa.horario.persistance;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.persistence.TramoHorarioPersistanceJPA;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;

@SpringBootTest
class TramoHorarioPersistanceJPATest {
	
	@Autowired
	TramoHorarioPersistanceJPA tramoHorarioPersistanceJPA;
	
	@Test
	void testReadNotFound() {
		final IdTramoHorarioDTO idTramoHorarioSearch = new IdTramoHorarioDTO(0, 100);
		assertThrows(NotFoundException.class, () -> this.tramoHorarioPersistanceJPA.readById( idTramoHorarioSearch ));
	}
	
	@Test
    void update() {
    	TramoHorarioEntity tramoHorarioEntity=new TramoHorarioEntity(0, 1, Time.valueOf( LocalTime.of(8, 0) ),Time.valueOf( LocalTime.of(9, 0)));
    	final TramoHorario tramoMateriaUpdateError = tramoHorarioEntity.toTramoHorario();
    	final IdTramoHorarioDTO idTramoHorarioUpdateError = new IdTramoHorarioDTO(0, 10);
    	assertThrows( NotFoundException.class, () -> tramoHorarioPersistanceJPA.update(idTramoHorarioUpdateError, tramoMateriaUpdateError ));

    	
    	IdTramoHorarioDTO idTramoHorarioDTO = new IdTramoHorarioDTO(0, 2);
    	TramoHorario tramoHorarioSave = tramoHorarioPersistanceJPA.readById(idTramoHorarioDTO);
    	
    	//COMPROBAR QUE EL INDICIE Y EL DIA(KEY) NO CMABIA, PERO LO OTRO SI
    	TramoHorario tramoHorarioResult = tramoHorarioPersistanceJPA.update(idTramoHorarioDTO, tramoHorarioEntity.toTramoHorario());
    	assertEquals(tramoHorarioSave.getDia(),tramoHorarioResult.getDia());
    	assertEquals(tramoHorarioSave.getIndice(),tramoHorarioResult.getIndice());
    	assertEquals(tramoHorarioEntity.toTramoHorario().getHoraEntrada(), tramoHorarioResult.getHoraEntrada());
    	assertEquals(tramoHorarioEntity.toTramoHorario().getHoraSalida(), tramoHorarioResult.getHoraSalida());
    	
    	//VOLVER A LO DE ANTES
    	tramoHorarioResult = tramoHorarioPersistanceJPA.update(idTramoHorarioDTO, tramoHorarioSave);
    	assertEquals(tramoHorarioSave.getDia(),tramoHorarioResult.getDia());
    	assertEquals(tramoHorarioSave.getIndice(),tramoHorarioResult.getIndice());
    	assertEquals(tramoHorarioSave.getHoraEntrada(), tramoHorarioResult.getHoraEntrada());
    	assertEquals(tramoHorarioSave.getHoraSalida(), tramoHorarioResult.getHoraSalida());
    	
    }
	
	@Test
	void existId() {
		//Exist
		IdTramoHorarioDTO idTramoHorarioDTO = new IdTramoHorarioDTO(0, 1);
		assertTrue(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));

		idTramoHorarioDTO = new IdTramoHorarioDTO(0, 4);
		assertTrue(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));

		idTramoHorarioDTO = new IdTramoHorarioDTO(1, 1);
		assertTrue(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));
	
		idTramoHorarioDTO = new IdTramoHorarioDTO(1, 4);
		assertTrue(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));
		
		//Not exist
		idTramoHorarioDTO = new IdTramoHorarioDTO(0, 0);
		assertFalse(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));

		idTramoHorarioDTO = new IdTramoHorarioDTO(0, 5);
		assertFalse(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));

	}
	
	@Test
	void create() {
		LocalTime horaEntrada = LocalTime.of(8, 0);
		LocalTime horasSalida = LocalTime.of(11, 0);
		IdTramoHorarioDTO idTramoHorarioDTO = new IdTramoHorarioDTO(4,0);
		TramoHorario tramoHorario=new TramoHorario(idTramoHorarioDTO.getDia(),idTramoHorarioDTO.getIndice(),horaEntrada,horasSalida);
		tramoHorarioPersistanceJPA.create(tramoHorario);
		assertTrue(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));
		
		TramoHorario tramoHorarioResult = tramoHorarioPersistanceJPA.readById(idTramoHorarioDTO);
		
		assertEquals(idTramoHorarioDTO.getDia(), tramoHorarioResult.getDia());
		assertEquals(idTramoHorarioDTO.getIndice(), tramoHorarioResult.getIndice());
		assertEquals(horaEntrada, tramoHorarioResult.getHoraEntrada());
		assertEquals(horasSalida,tramoHorarioResult.getHoraSalida());
		assertEquals(tramoHorario, tramoHorarioResult);
		
		//VOLVEMOS AL ESTADO DE ANTES
		boolean result = tramoHorarioPersistanceJPA.delete(idTramoHorarioDTO);
		assertTrue(result);
		result = tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO);
		assertFalse(result);
	}
	
	@Test
	void delete() {
		LocalTime horaEntrada = LocalTime.of(8, 0);
		LocalTime horasSalida = LocalTime.of(11, 0);
		IdTramoHorarioDTO idTramoHorarioDTO = new IdTramoHorarioDTO(4,0);
		TramoHorario tramoHorario=new TramoHorario(idTramoHorarioDTO.getDia(),idTramoHorarioDTO.getIndice(),horaEntrada,horasSalida);
		tramoHorarioPersistanceJPA.create(tramoHorario);
		assertTrue(tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO));
		
		boolean result=tramoHorarioPersistanceJPA.delete(idTramoHorarioDTO);
		assertTrue(result);
		result = tramoHorarioPersistanceJPA.existId(idTramoHorarioDTO);
		assertFalse(result);
	}
	
	@Test
	void readAll() {
		int expected=8;
		assertEquals(expected, tramoHorarioPersistanceJPA.readAll().count());
	}
	
	@Test
	void readById() {
		TramoHorario tramoHorario = tramoHorarioPersistanceJPA.readById(new IdTramoHorarioDTO(0,1));
		LocalTime horaEntradaExpected=LocalTime.of(8, 0);
		LocalTime horaSalidaExpected=LocalTime.of(9, 0);
		assertEquals(horaEntradaExpected, tramoHorario.getHoraEntrada());
		assertEquals(horaSalidaExpected, tramoHorario.getHoraSalida());
		
		tramoHorario = tramoHorarioPersistanceJPA.readById(new IdTramoHorarioDTO(1,4));
		horaEntradaExpected=LocalTime.of(11, 30);
		horaSalidaExpected=LocalTime.of(12, 30);
		assertEquals(horaEntradaExpected, tramoHorario.getHoraEntrada());
		assertEquals(horaSalidaExpected, tramoHorario.getHoraSalida());
	}


}
