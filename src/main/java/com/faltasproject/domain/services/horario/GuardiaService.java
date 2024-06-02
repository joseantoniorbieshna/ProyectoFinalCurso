package com.faltasproject.domain.services.horario;

import java.util.List;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.Guardia;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.GuardiaCountedFaltaProfesorDTO;
import com.faltasproject.domain.models.horario.dtos.IdGuardiaDTO;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.models.horario.mappers.ProfesorWithCountMapper;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.persistance_ports.horario.GuardiaPersistance;
import com.faltasproject.domain.persistance_ports.horario.TramoHorarioPersistance;
import com.faltasproject.domain.persistance_ports.profesorado.ProfesorPersistance;

@Service
public class GuardiaService {
	private final GuardiaPersistance guardiaPersistance;
	private final ProfesorPersistance profesorPersistance;
	private final TramoHorarioPersistance tramoHorarioPersistance;
	private final FaltaService faltaService;
	private final ProfesorWithCountMapper profesorWithCountMapper;

	public GuardiaService(GuardiaPersistance guardiaPersistance, ProfesorPersistance profesorPersistance,
			TramoHorarioPersistance tramoHorarioPersistance,FaltaService faltaService, ProfesorWithCountMapper profesorWithCountMapper) {
		super();
		this.guardiaPersistance = guardiaPersistance;
		this.profesorPersistance = profesorPersistance;
		this.tramoHorarioPersistance = tramoHorarioPersistance;
		this.faltaService=faltaService;
		this.profesorWithCountMapper=profesorWithCountMapper;
	}



	public Guardia create(IdGuardiaDTO idGuardiaDTO) {
		IdTramoHorarioDTO idTramoHorarioDTO = new IdTramoHorarioDTO(idGuardiaDTO.getDia(), idGuardiaDTO.getIndice());
		TramoHorario tramoHorario = tramoHorarioPersistance.readById(idTramoHorarioDTO);
		Profesor profesor = profesorPersistance.readByReferencia(idGuardiaDTO.getReferenciaProfesor());
		return this.guardiaPersistance.create(new Guardia(tramoHorario, profesor));
	}



	public List<Guardia> findAll() {
		return guardiaPersistance.readAll().toList();
	}
	
	public List<Guardia> findAllByReferenciaProfesor(String referenciaProfesor) {
		return guardiaPersistance.readAll()
				.filter(p->p.getReferenciaProfesor().equals(referenciaProfesor))
				.toList();
	}
	
	public List<GuardiaCountedFaltaProfesorDTO> findAllByDiaAndIndice(IdTramoHorarioDTO idTramoHorarioDTO) {
		return guardiaPersistance.readAll()
				.filter(p->p.getDia()==idTramoHorarioDTO.getDia() && p.getIndice()==idTramoHorarioDTO.getIndice())
				.map(t -> {
					int totalGuardiaSustituidas=faltaService.countTotalFaltasSustituidasByReferenciaProfesorDiaAndIndice(t.getReferenciaProfesor(), t.getDia(), t.getIndice());
					return new GuardiaCountedFaltaProfesorDTO(t.getTramoHorario(),profesorWithCountMapper.mapToDTO(t.getProfesor(), totalGuardiaSustituidas));
				})
				.toList();
	}

}
