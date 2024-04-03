package com.faltasproject.domain.persistance_ports.horario;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;

@Repository
public interface SesionPersistance {
	
	Sesion create(Sesion tramoHorario);
	
	Sesion update(String referencia,Sesion tramoHorario);
	
	Stream<Sesion> readAll();
	
	Sesion readByReferencia(String referencia);
	
	boolean delete(String referencia);
	
	boolean existReferencia(String referencia);
	
	boolean removeTramoHorarioToSesion(String referenciaSesion,IdTramoHorarioDTO idTramoHorarioDTO);
	
	boolean addTramoHorarioToSesion(String referencia,IdTramoHorarioDTO idTramoHorarioDTO);
	
	boolean existTramoHorarioInSesion(String referenciaSesion,IdTramoHorarioDTO idTramoHorarioDTO);
}
