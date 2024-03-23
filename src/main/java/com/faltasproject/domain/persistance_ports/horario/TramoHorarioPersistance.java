package com.faltasproject.domain.persistance_ports.horario;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;

@Service
public interface TramoHorarioPersistance {

	TramoHorario create(TramoHorario tramoHorario);
	
	TramoHorario update(IdTramoHorarioDTO idTramoHorarioDTO,TramoHorario tramoHorario);
	
	Stream<TramoHorario> readAll();
	
	TramoHorario readById(IdTramoHorarioDTO idTramoHorarioDTO);
	
	Boolean delete(IdTramoHorarioDTO idTramoHorarioDTO);
	
	Boolean existId(IdTramoHorarioDTO idTramoHorarioDTO);
}
