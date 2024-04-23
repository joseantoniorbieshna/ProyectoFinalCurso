package com.faltasproject.domain.services.horario;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public void create(TramoHorario tramoHorario) {
		tramoHorarioPersistance.create(tramoHorario);
	}
	
	public List<TramoHorario> findAll() {
		return tramoHorarioPersistance.readAll().collect(Collectors.toList());
	}
}
