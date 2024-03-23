package com.faltasproject.adapters.jpa.horario.persistence;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
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
		TramoHorarioEntity tramoHorarioEntity=tramoHorarioRepositoryJPA.findByKeyDiaAndKeyIndice(tramoHorario.getDia(),tramoHorario.getIndice()).
		orElseThrow(()->new ConflictExceptions("No se ha encontrado el Tramo horario con dia "+tramoHorario.getDia()+" y indice "+tramoHorario.getIndice()));
		return tramoHorarioEntity.toTramoHorario();
	}

	@Override
	public TramoHorario update(IdTramoHorarioDTO idTramoHorarioDTO, TramoHorario tramoHorario) {
		if(!existId(idTramoHorarioDTO)) {
			throw new NotFoundException("El tramo horario con el dia "+idTramoHorarioDTO.getDia()+ " y el indice "+idTramoHorarioDTO.getIndice()+" no existe");
		}
		TramoHorarioEntity tramoHorarioEntity=tramoHorarioRepositoryJPA.findByKeyDiaAndKeyIndice(idTramoHorarioDTO.getDia(), idTramoHorarioDTO.getIndice()).get();
		tramoHorarioEntity.fromTramoHorario(tramoHorario);
		return tramoHorarioRepositoryJPA.save(tramoHorarioEntity).toTramoHorario();
	}

	@Override
	public Stream<TramoHorario> readAll() {
		return tramoHorarioRepositoryJPA.findAll().stream()
				.map(tramoHorarioEntity -> tramoHorarioEntity.toTramoHorario());
	}

	@Override
	public TramoHorario readById(IdTramoHorarioDTO idTramoHorarioDTO) {
		return tramoHorarioRepositoryJPA.findByKeyDiaAndKeyIndice(idTramoHorarioDTO.getDia(), idTramoHorarioDTO.getIndice())
				.orElseThrow(()-> new NotFoundException("No se ha encontrado el Tramo horario con dia "+idTramoHorarioDTO.getDia()+" y indice "+idTramoHorarioDTO.getIndice()))
				.toTramoHorario();
	}

	@Override
	public Boolean delete(IdTramoHorarioDTO idTramoHorarioDTO) {
		if(!existId(idTramoHorarioDTO)) {
			throw new NotFoundException("No se ha encontrado el Tramo horario con dia "+idTramoHorarioDTO.getDia()+" y indice "+idTramoHorarioDTO.getIndice());
		}
		tramoHorarioRepositoryJPA.deleteByKeyDiaAndKeyIndice(idTramoHorarioDTO.getDia(), idTramoHorarioDTO.getIndice());;
		return !existId(idTramoHorarioDTO);
	}

	@Override
	public Boolean existId(IdTramoHorarioDTO idTramoHorarioDTO) {
		return tramoHorarioRepositoryJPA.findByKeyDiaAndKeyIndice(idTramoHorarioDTO.getDia(), idTramoHorarioDTO.getIndice()).isPresent();
	}
	

}
