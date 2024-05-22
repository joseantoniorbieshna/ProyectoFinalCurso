package com.faltasproject.adapters.jpa.horario.persistence;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.horario.daos.GuardiaRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.GuardiaEntity;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.TramoHorarioKey;
import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.Guardia;
import com.faltasproject.domain.models.horario.dtos.IdGuardiaDTO;
import com.faltasproject.domain.models.horario.mappers.GuardiaIdMapper;
import com.faltasproject.domain.persistance_ports.horario.GuardiaPersistance;

import jakarta.transaction.Transactional;

@Repository("guardiaPersistance")
@Transactional
public class GuardiaPersistanceJPA implements GuardiaPersistance {

	private final GuardiaRepositoryJPA guardiaRepositoryJPA;
	private final ProfesorRepositoryJPA profesorRepositoryJPA;
	private final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	private final GuardiaIdMapper guardiaIdMapper;

	public GuardiaPersistanceJPA(ProfesorRepositoryJPA profesorRepositoryJPA,
			TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA, GuardiaRepositoryJPA guardiaRepositoryJPA,
			GuardiaIdMapper guardiaIdMapper) {
		super();
		this.profesorRepositoryJPA = profesorRepositoryJPA;
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
		this.guardiaRepositoryJPA = guardiaRepositoryJPA;
		this.guardiaIdMapper = guardiaIdMapper;
	}

	@Override
	public Guardia create(Guardia guardia) {
		GuardiaEntity guardiaPersistance = new GuardiaEntity(guardia);
		changeToPersistData(guardiaPersistance);
		return guardiaRepositoryJPA.save(guardiaPersistance).toGuardia();
	}

	private void changeToPersistData(GuardiaEntity guardiaPersistance) {
		TramoHorarioKey idTramoHorario = new TramoHorarioKey(guardiaPersistance.getDia(),guardiaPersistance.getIndice());
		ProfesorEntity profesor = this.profesorRepositoryJPA.findByReferencia(guardiaPersistance.getReferenciaProfesor())
				.orElseThrow( () -> new NotFoundException("No se ha encontrado el profesor con la referencia "+ guardiaPersistance.getReferenciaProfesor()) );
		TramoHorarioEntity tramoHorario  = this.tramoHorarioRepositoryJPA.findById(idTramoHorario).
				orElseThrow(()-> new NotFoundException("No se ha encontrado el tramo con el id"+idTramoHorario.getDia()+" e indice "+ idTramoHorario.getIndice()));
		
		guardiaPersistance.setTramoHorario(tramoHorario);
		guardiaPersistance.setProfesor(profesor);
	}

	@Override
	public Guardia update(IdGuardiaDTO idGuardia, Guardia guardiaUpdate) {
		IdGuardiaDTO idGuardiaUpdate = guardiaIdMapper.toDto(guardiaUpdate);
		
		if(!idGuardia.equals(idGuardiaUpdate) && existId(idGuardiaUpdate)) {
			throw new ConflictException("La guardia para este profesor ya existe");
		}
		
		GuardiaEntity guardiaPersistance = this.guardiaRepositoryJPA.findByReferencia(idGuardia.getReferenciaProfesor(), idGuardia.getDia(), idGuardia.getIndice())
				.orElseThrow(()-> new NotFoundException("No se ha encontrado la guardia"));
		
		guardiaPersistance.fromGuardia(guardiaUpdate);
		changeToPersistData(guardiaPersistance);
		
		return this.guardiaRepositoryJPA.save(guardiaPersistance).toGuardia();
	}

	@Override
	public Stream<Guardia> readAll() {
		return this.guardiaRepositoryJPA.findAll().stream()
				.map(GuardiaEntity::toGuardia);
	}

	@Override
	public Guardia readById(IdGuardiaDTO idGuardiaDTO) {
		return this.guardiaRepositoryJPA.findByReferencia(idGuardiaDTO.getReferenciaProfesor(),idGuardiaDTO.getDia(),idGuardiaDTO.getIndice())
				.orElseThrow(()-> new NotFoundException("No se ha encontrado la guardia"))
				.toGuardia();
	}

	@Override
	public void delete(IdGuardiaDTO idGuardiaDTO) {
		if(!existId(idGuardiaDTO)) {
			throw new NotFoundException("No se ha encontrado la guardia a eliminar");
		}
		this.guardiaRepositoryJPA.deleteByReferencia(idGuardiaDTO.getReferenciaProfesor(), idGuardiaDTO.getDia(), idGuardiaDTO.getIndice());
	}

	@Override
	public boolean existId(IdGuardiaDTO idGuardiaDTO) {
		return this.guardiaRepositoryJPA.findByReferencia(idGuardiaDTO.getReferenciaProfesor(), idGuardiaDTO.getDia(), idGuardiaDTO.getIndice())
				.isPresent();
	}

}
