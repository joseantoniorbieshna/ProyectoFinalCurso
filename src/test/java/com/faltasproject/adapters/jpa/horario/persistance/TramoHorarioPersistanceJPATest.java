package com.faltasproject.adapters.jpa.horario.persistance;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Time;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.persistence.TramoHorarioPersistanceJPA;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;

@SpringBootTest
public class TramoHorarioPersistanceJPATest {
	
	@Autowired
	TramoHorarioPersistanceJPA tramoHorarioPersistanceJPA;
	
	@Test
	void testReadNotFound() {
		 assertThrows(NotFoundException.class, () -> this.tramoHorarioPersistanceJPA.readById( new IdTramoHorarioDTO(0, 100) ));
	}
	
    void update() {
    	TramoHorarioEntity tramoHorarioEntity=new TramoHorarioEntity(0, 1, Time.valueOf( LocalTime.of(8, 0) ),Time.valueOf( LocalTime.of(9, 0)));
    	assertThrows( NotFoundException.class, () -> tramoHorarioPersistanceJPA.update(new IdTramoHorarioDTO(0, 10), tramoHorarioEntity.toTramoHorario()) );

    	
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
	
}
