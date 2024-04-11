package com.faltasproject.domain.persistance_ports.horario;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;

@Repository
public interface HoraHorarioPersistance {

	HoraHorario create(HoraHorario horaHorarioEntity);
	
	HoraHorario update(String referenciaSesion,int dia, int indice,HoraHorario horaHorarioUpdate);
	
	Stream<HoraHorario> readAll();
	
	Stream<HoraHorario> readAllByReferenciaProfesor(String referenciaProfesor);
	
	Stream<HoraHorario> readAllByReferenciaSesion(String referenciaSesion);

	Stream<HoraHorario> readAllByIdTramoHorario(IdTramoHorarioDTO referenciaTramoHorario);
	
	HoraHorario readByReferenciaProfesorAndTramoHorario(String referenciaProfesor,IdTramoHorarioDTO referenciaTramoHorario);
	
	HoraHorario readByReferenciaSesionAndTramoHorario(String referenciaSesion,IdTramoHorarioDTO idTramoHorarioDTO);
	
	boolean deleteByReferenciaSesionAndTramoHorario(String referenciaSesion,IdTramoHorarioDTO idTramoHorarioDTO);
	
	boolean existDiaIndiceTramoHorarioAndReferenciaSesion(String referenciaSesion,IdTramoHorarioDTO idTramoHorarioDTO);
	
}
