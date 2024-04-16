package com.faltasproject.domain.services.horario;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.dto.InputHoraHorarioDTO;
import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.persistance_ports.horario.HoraHorarioPersistance;

@Service
public class HoraHorarioService {
	
	private HoraHorarioPersistance horaHorarioPersistance;
	
	public HoraHorarioService(HoraHorarioPersistance horaHorarioPersistance) {
		super();
		this.horaHorarioPersistance = horaHorarioPersistance;
	}

	public void create(InputHoraHorarioDTO inputHoraHorarioDTO) {
		Sesion sesion=new Sesion(inputHoraHorarioDTO.getReferenciaSesion());
		TramoHorario tamoHorario = new TramoHorario(inputHoraHorarioDTO.getDia(), inputHoraHorarioDTO.getIndice());
		horaHorarioPersistance.create(new HoraHorario(sesion, tamoHorario));
	}
}
