package com.faltasproject.adapters.jpa.horario.persistance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.adapters.jpa.clases.persistence.SesionPersistanceJPA;
import com.faltasproject.adapters.jpa.horario.persistence.HoraHorarioPersistanceJPA;
import com.faltasproject.adapters.jpa.horario.persistence.TramoHorarioPersistanceJPA;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;

import jakarta.transaction.Transactional;

@SpringBootTest
class HoraHorarioPersistanceJPATest {

	@Autowired
	HoraHorarioPersistanceJPA horaHorarioPersistanceJPA;
	@Autowired
	TramoHorarioPersistanceJPA tramoHorarioPersistanceJPA;
	@Autowired
	SesionPersistanceJPA sesionPersistanceJPA;
	
	@Test
	void create() {
		//ERROR YA EXISTENTE
		Sesion sesionError = new Sesion("01");
		TramoHorario tramoHorarioError = new TramoHorario(0,1);
		HoraHorario horaHorarioError = new HoraHorario(sesionError, tramoHorarioError);
		
		assertThrows(ConflictException.class,()-> horaHorarioPersistanceJPA.create(horaHorarioError));
		// CREAR
		
		String referenciaSesion="01";
		Sesion sesion = new Sesion(referenciaSesion);
		int dia=1;
		int indice=4;
		IdTramoHorarioDTO idTramoHorario = new IdTramoHorarioDTO(dia,indice);
		TramoHorario tramoHorario = new TramoHorario(dia,indice);
		HoraHorario horaHorario = new HoraHorario(sesion, tramoHorario);
		
		
		assertTrue( sesionPersistanceJPA.existReferencia(referenciaSesion) );
		assertTrue( tramoHorarioPersistanceJPA.existId(idTramoHorario) );
		
		HoraHorario horaHorarioSave=horaHorarioPersistanceJPA.create(horaHorario);
		assertEquals(referenciaSesion, horaHorarioSave.getReferenciaSesion());
		assertEquals(dia, horaHorarioSave.getDiaTramoHorario());
		assertEquals(indice, horaHorarioSave.getIndiceTramoHorario());
		
		// COMRPROBAMOS QUE NO SE BORRE EN CASCADA
		boolean isDelete=horaHorarioPersistanceJPA.deleteByReferenciaSesionAndTramoHorario(referenciaSesion, idTramoHorario);
		assertTrue(isDelete);
		
		assertFalse(horaHorarioPersistanceJPA.existDiaIndiceTramoHorarioAndReferenciaSesion(referenciaSesion, idTramoHorario));
		assertTrue( sesionPersistanceJPA.existReferencia(referenciaSesion) );
		assertTrue( tramoHorarioPersistanceJPA.existId(new IdTramoHorarioDTO(dia,indice)) );
		
	}
	
	@Test
	void updateError() {
		//ERROR REF NO EXISTENTE
		String referenciaSesionError1 = "QWER";
		int dia=0;
		int indice=1;
		Sesion sesionError = new Sesion(referenciaSesionError1);
		TramoHorario tramoHorarioError = new TramoHorario(dia,indice);
		HoraHorario horaHorarioError1 = new HoraHorario(sesionError, tramoHorarioError);
		assertThrows(NotFoundException.class,()-> horaHorarioPersistanceJPA.update(referenciaSesionError1, dia, indice, horaHorarioError1) );

		//ERROR REF NO EXISTENTE
		String referenciaSesionError2 = "01";
		int diaError = 1; 
		int indiceError = 3; 
		sesionError = new Sesion(referenciaSesionError2);
		tramoHorarioError = new TramoHorario(diaError,indiceError);
		HoraHorario horaHorarioError2 = new HoraHorario(sesionError, tramoHorarioError);
		assertThrows(ConflictException.class,()-> horaHorarioPersistanceJPA.update(referenciaSesionError2, dia, indice, horaHorarioError2) );
		
	}
	
	@Test
	void readAll() {
		long total = horaHorarioPersistanceJPA.readAll().count();
		long expected = 9;
		assertEquals(expected,total);
	}
	
	@Test
	void readAllByReferenciaProfesor() {
		String referenciaProfesor="01";
		long total = horaHorarioPersistanceJPA.readAllByReferenciaProfesor(referenciaProfesor).count();
		long expected = 3;
		assertEquals(expected,total);
		
		
		referenciaProfesor="02";
		total = horaHorarioPersistanceJPA.readAllByReferenciaProfesor(referenciaProfesor).count();
		expected = 6;
		assertEquals(expected,total);
		
		
		referenciaProfesor="05";
		total = horaHorarioPersistanceJPA.readAllByReferenciaProfesor(referenciaProfesor).count();
		expected = 0;
		assertEquals(expected,total);
	}
	
	@Test
	void readAllByReferenciaSesion() {
		String referenciaSesion="01";
		long expected=3;
		long total=horaHorarioPersistanceJPA.readAllByReferenciaSesion(referenciaSesion).count();
		assertEquals(expected, total);
		
		
		referenciaSesion="00F";
		expected=0;
		total=horaHorarioPersistanceJPA.readAllByReferenciaSesion(referenciaSesion).count();
		assertEquals(expected, total);
	}
	
	@Test
	void readAllByIdTramo() {
		int dia=1;
		int indice=2;
		IdTramoHorarioDTO idTramoHorarioDTO=new IdTramoHorarioDTO(dia, indice);
		long expected=3;
		long total=horaHorarioPersistanceJPA.readAllByIdTramoHorario(idTramoHorarioDTO).count();
		assertEquals(expected, total);
		
	
		dia=0;
		indice=3;
		idTramoHorarioDTO=new IdTramoHorarioDTO(dia, indice);
		expected=2;
		total=horaHorarioPersistanceJPA.readAllByIdTramoHorario(idTramoHorarioDTO).count();
		assertEquals(expected, total);
		
		
		dia=100;
		indice=0;
		idTramoHorarioDTO=new IdTramoHorarioDTO(dia, indice);
		expected=0;
		total=horaHorarioPersistanceJPA.readAllByIdTramoHorario(idTramoHorarioDTO).count();
		assertEquals(expected, total);
	}
	
	@Test
	void readByReferenciaSesionAndTramoHorario() {
		String referenciaSesion="01";
		int dia=0;
		int indice=1;
		IdTramoHorarioDTO idTramoHorarioDTO=new IdTramoHorarioDTO(dia, indice);
		HoraHorario horaHorario = horaHorarioPersistanceJPA.readByReferenciaSesionAndTramoHorario(referenciaSesion, idTramoHorarioDTO);
		assertEquals(referenciaSesion, horaHorario.getReferenciaSesion());
		assertEquals(dia, horaHorario.getDiaTramoHorario());
		assertEquals(indice, horaHorario.getIndiceTramoHorario());
		
		// ERROR
		String referenciaSesionNotExist = "100FF";
		assertThrows(NotFoundException.class, ()-> horaHorarioPersistanceJPA.readByReferenciaSesionAndTramoHorario(referenciaSesionNotExist, idTramoHorarioDTO) ); 
		// ERROR 2
		int diaError=100;
		IdTramoHorarioDTO idTramoHorarioDTOError=new IdTramoHorarioDTO(diaError, indice);
		assertThrows(NotFoundException.class, ()-> horaHorarioPersistanceJPA.readByReferenciaSesionAndTramoHorario(referenciaSesion, idTramoHorarioDTOError) ); 
		
	}
	
	@Test
	void readByReferenciaProfesorAndTramoHorario() {
		String profesorReferencia = "01";
		int dia=0;
		int indice=1;
		IdTramoHorarioDTO idTramoHorarioDTO=new IdTramoHorarioDTO(dia, indice);
		HoraHorario horaHorario = horaHorarioPersistanceJPA.readByReferenciaProfesorAndTramoHorario(profesorReferencia, idTramoHorarioDTO);
		assertEquals(dia, horaHorario.getDiaTramoHorario());
		assertEquals(indice, horaHorario.getIndiceTramoHorario());
		assertEquals(profesorReferencia, horaHorario.getSesion().getProfesor().getReferencia());
		
		dia=1;
		indice=2;
		idTramoHorarioDTO=new IdTramoHorarioDTO(dia, indice);
		horaHorario = horaHorarioPersistanceJPA.readByReferenciaProfesorAndTramoHorario(profesorReferencia, idTramoHorarioDTO);
		assertEquals(dia, horaHorario.getDiaTramoHorario());
		assertEquals(indice, horaHorario.getIndiceTramoHorario());
		assertEquals(profesorReferencia, horaHorario.getSesion().getProfesor().getReferencia());
		
		// ERROR
		final String profesorReferenciaError = "100";
		final IdTramoHorarioDTO idTramoHorarioDTONoError=new IdTramoHorarioDTO(dia, indice);
		assertThrows( NotFoundException.class,()-> horaHorarioPersistanceJPA.readByReferenciaProfesorAndTramoHorario(profesorReferenciaError, idTramoHorarioDTONoError) );
		
		//ERROR2
		final String profesorReferenciaNoError = "01";
		final IdTramoHorarioDTO idTramoHorarioDToError=new IdTramoHorarioDTO(100, 0);
		assertThrows( NotFoundException.class,()-> horaHorarioPersistanceJPA.readByReferenciaProfesorAndTramoHorario(profesorReferenciaNoError, idTramoHorarioDToError) );
		
	
	}

}
