package com.faltasproject.domain.persistance_ports.horario;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.models.horario.dtos.ReferenciaHoraHorarioDTO;

@Repository
public interface HoraHorarioPersistance {

	HoraHorario create(HoraHorario horaHorarioEntity);
	
	HoraHorario update(String referencia,HoraHorario horaHorarioEntity);
	
	Stream<HoraHorario> readAll();
	
	Stream<HoraHorario> readAllByReferenciaSesion(String referenciaSesion);
	
	Stream<HoraHorario> readAllByReferenciaTramoHorario(IdTramoHorarioDTO referenciaTramoHorario);
	
	HoraHorario readByReferencia(ReferenciaHoraHorarioDTO referencia);
	
	boolean deleteByReferenciaSesion(String referenciaSesion);
	
	boolean existReferencia(String referencia);
	
}
