package com.faltasproject.domain.services.horario;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.persistance_ports.horario.TramoHorarioPersistance;

@Service
public class TramoHorarioService {
	
	private TramoHorarioPersistance tramoHorarioPersistance;
	
	public TramoHorarioService(TramoHorarioPersistance tramoHorarioPersistance) {
		super();
		this.tramoHorarioPersistance = tramoHorarioPersistance;
	}


	public void Create(TramoHorario tramoHorario) {
		tramoHorarioPersistance.create(tramoHorario);
	}
}
