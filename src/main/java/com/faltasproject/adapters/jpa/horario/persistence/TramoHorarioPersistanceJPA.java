package com.faltasproject.adapters.jpa.horario.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.KeyTramoHorario;
import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.persistance_ports.horario.TramoHorarioPersistance;

@Repository("tramoHorarioPersistance")
public class TramoHorarioPersistanceJPA implements TramoHorarioPersistance {
	
	public final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	
	public TramoHorarioPersistanceJPA(TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA) {
		super();
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
	}

	@Override
	public TramoHorario create(TramoHorario tramoHorario) {
		if(existId(tramoHorario)) {
			throw new ConflictExceptions(getMessageErrorExist(tramoHorario.getDia(),tramoHorario.getIndice()));
		}
		return tramoHorarioRepositoryJPA.save(new TramoHorarioEntity(tramoHorario)).toTramoHorario();
	}

	@Override
	public TramoHorario update(IdTramoHorarioDTO idTramoHorarioDTO, TramoHorario tramoHorario) {
		Optional<TramoHorarioEntity> tramoHorarioEntity=tramoHorarioRepositoryJPA.findById(new KeyTramoHorario(idTramoHorarioDTO.getDia(), idTramoHorarioDTO.getIndice()));
		if(!tramoHorarioEntity.isPresent()) {
			throw new NotFoundException(getMessageErrorNotFound(idTramoHorarioDTO.getDia(),idTramoHorarioDTO.getIndice()));
		}
		TramoHorarioEntity tramo = tramoHorarioEntity.get();
		tramo.fromTramoHorario(tramoHorario);
		return tramoHorarioRepositoryJPA.save(tramo).toTramoHorario();
	}

	@Override
	public Stream<TramoHorario> readAll() {
		return tramoHorarioRepositoryJPA.findAll().stream()
				.map(TramoHorarioEntity::toTramoHorario);
	}

	@Override
	public TramoHorario readById(IdTramoHorarioDTO idTramoHorarioDTO) {
		return tramoHorarioRepositoryJPA.findById(new KeyTramoHorario(idTramoHorarioDTO))
				.orElseThrow(()-> new NotFoundException(getMessageErrorNotFound(idTramoHorarioDTO.getDia(),idTramoHorarioDTO.getIndice())))
				.toTramoHorario();
	}

	@Override
	public boolean delete(IdTramoHorarioDTO idTramoHorarioDTO) {
		if(!existId(idTramoHorarioDTO)) {
			throw new NotFoundException(getMessageErrorNotFound(idTramoHorarioDTO.getDia(),idTramoHorarioDTO.getIndice()));
		}
		tramoHorarioRepositoryJPA.deleteById(new KeyTramoHorario(idTramoHorarioDTO));
		return !existId(idTramoHorarioDTO);
	}

	@Override
	public boolean existId(IdTramoHorarioDTO idTramoHorarioDTO) {
		return tramoHorarioRepositoryJPA.findById(new KeyTramoHorario(idTramoHorarioDTO)).isPresent();
	}
	
	public boolean existId(TramoHorario tramoHorario) {
		return existId(new IdTramoHorarioDTO(tramoHorario.getDia(),tramoHorario.getIndice()));
	}
	
	private String getMessageErrorNotFound(Integer dia,Integer indice) {
		return "No se ha encontrado el Tramo horario con dia "+dia+" e indice "+indice;
	}
	
	private String getMessageErrorExist(Integer dia,Integer indice) {
		return  "ya existe el Tramo horario con dia "+dia+" e indice "+indice;
	}
	

}
