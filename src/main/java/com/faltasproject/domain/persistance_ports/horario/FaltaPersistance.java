package com.faltasproject.domain.persistance_ports.horario;

import java.time.LocalDate;
import java.util.stream.Stream;

import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.dtos.IdFaltaDTO;

public interface FaltaPersistance {

	Falta create(Falta falta);
	
	Falta update(IdFaltaDTO idFaltaDTO,Falta tramoHorario);
	
	Stream<Falta> readAll();

	Stream<Falta> readByFecha(LocalDate fecha);

	Stream<Falta> readFaltasBetweenFechas(LocalDate primeraFecha, LocalDate segundaFecha);
	
	Falta readById(IdFaltaDTO idFaltaDTO);
	
	void delete(IdFaltaDTO idFaltaDTO);
	
	boolean existId(IdFaltaDTO idFaltaDTO);
}
