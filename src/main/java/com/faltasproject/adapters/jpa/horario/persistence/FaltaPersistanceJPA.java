package com.faltasproject.adapters.jpa.horario.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.horario.daos.FaltaRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.HoraHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.FaltaEntity;
import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;
import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.dtos.IdFaltaDTO;
import com.faltasproject.domain.models.horario.mappers.FaltaIdMapper;
import com.faltasproject.domain.persistance_ports.horario.FaltaPersistance;

import jakarta.transaction.Transactional;
@Repository("faltaPersistance")
public class FaltaPersistanceJPA implements FaltaPersistance{
	private static final String MESSAGE_NOT_EXIST_HORA_HORARIO="La hora horario introducida no existe.";
	private static final String MESSAGE_NOT_EXIST_FALTA="La falta introducida no existe.";
	private static final String MESSAGE_NOT_EXIST_PROFESOR="El profesor introducida no existe.";
	
	private final FaltaRepositoryJPA faltaRepositoryJPA;
	private final HoraHorarioRepositoryJPA horaHorarioRepositoryJPA;
	private final ProfesorRepositoryJPA profesorRepositoryJPA;
	private final FaltaIdMapper faltaIdMapper;
	
	public FaltaPersistanceJPA(FaltaRepositoryJPA faltaRepositoryJPA,
			HoraHorarioRepositoryJPA horaHorarioRepositoryJPA,
			FaltaIdMapper faltaIdMapper,
			ProfesorRepositoryJPA profesorRepositoryJPA) {
		
		super();
		this.faltaRepositoryJPA = faltaRepositoryJPA;
		this.horaHorarioRepositoryJPA=horaHorarioRepositoryJPA;
		this.profesorRepositoryJPA=profesorRepositoryJPA;
		this.faltaIdMapper=faltaIdMapper;
	}
	
	@Override
	
	public Falta create(Falta falta) {
		HoraHorarioEntity horaHorarioPersist=getHoraHorarioPersist(faltaIdMapper.toDto(falta))
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_HORA_HORARIO));
		
		if(existId(faltaIdMapper.toDto(falta))) {
			throw new ConflictException("La Falta ya existe");
		}
		
		FaltaEntity faltaEntity = new FaltaEntity(falta);
		
		faltaEntity.setHoraHorario(horaHorarioPersist);
		faltaEntity.setProfesorSustituto(null);
		// NO ES NECESARIO PERSISTIR PROFESOR YA QUE PROFESOR SUSTITUTO NO VA A ESTAR EN PRIMERA INSTANCIA
		return faltaRepositoryJPA.save(faltaEntity)
				.toFalta();
	}

	@Override
	public Falta update(IdFaltaDTO idFaltaDTO, Falta falta) {
		HoraHorarioEntity horaHorarioPersist=getHoraHorarioPersist(idFaltaDTO)
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_HORA_HORARIO));
		
		FaltaEntity faltaUpdate = faltaRepositoryJPA.findByHoraHorarioAndFecha(horaHorarioPersist, idFaltaDTO.getFecha())
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_HORA_HORARIO));
		
		faltaUpdate.fromFalta(falta);
		changeToPersistData(faltaUpdate,falta);
		
		/*SI HAY CONFLICTO AL CAMBIAR LA FALTA*/
		if(!falta.getFecha().equals(idFaltaDTO.getFecha()) && existId(faltaIdMapper.toDto(falta))) {
			throw new ConflictException("La falta a la que quiere cambiar ya existe");
		}
		
		return faltaRepositoryJPA.save(faltaUpdate).toFalta();
	}

	@Override
	public Stream<Falta> readAll() {
		return faltaRepositoryJPA.findAll().stream().map(FaltaEntity::toFalta);
	}

	@Override
	public Stream<Falta> readByFecha(LocalDate fecha) {
		return faltaRepositoryJPA.findByFecha(fecha).stream().map(FaltaEntity::toFalta);
	}

	@Override
	public Stream<Falta> readFaltasBetweenFechas(LocalDate primeraFecha, LocalDate segundaFecha) {
		return faltaRepositoryJPA.findByFechaBetween(primeraFecha, segundaFecha).stream().map(FaltaEntity::toFalta);
	}

	@Override
	public Falta readById(IdFaltaDTO idFaltaDTO) {
		HoraHorarioEntity horaHorarioPersist=getHoraHorarioPersist(idFaltaDTO)
			.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_HORA_HORARIO));
		
		return faltaRepositoryJPA.findByHoraHorarioAndFecha(horaHorarioPersist, idFaltaDTO.getFecha())
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_FALTA))
				.toFalta();
	}

	@Override
	public void delete(IdFaltaDTO idFaltaDTO) {
		HoraHorarioEntity horaHorarioPersist=getHoraHorarioPersist(idFaltaDTO)
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_HORA_HORARIO));
		
		FaltaEntity faltaEntity= faltaRepositoryJPA.findByHoraHorarioAndFecha(horaHorarioPersist, idFaltaDTO.getFecha())
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_FALTA));
		
		faltaRepositoryJPA.delete(faltaEntity);
	}

	@Override
	public boolean existId(IdFaltaDTO idFaltaDTO) {
		Optional<HoraHorarioEntity> horaHorarioEntity = getHoraHorarioPersist(idFaltaDTO);
		
		if(horaHorarioEntity.isPresent()) {
			return faltaRepositoryJPA.findByHoraHorarioAndFecha(horaHorarioEntity.get(),idFaltaDTO.getFecha())
					.isPresent();
		}
		return false;
	}
	
	private Optional<HoraHorarioEntity> getHoraHorarioPersist(IdFaltaDTO idFaltaDTO){
		return horaHorarioRepositoryJPA.findByReferenciaAndTramoHorario(idFaltaDTO.getReferenciaSesion(), idFaltaDTO.getDia(), idFaltaDTO.getIndice());
	}
	
	private void changeToPersistData(FaltaEntity faltaUpdate,Falta falta) {
		HoraHorarioEntity horaHorarioPersist=getHoraHorarioPersist(faltaIdMapper.toDto(falta))
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_HORA_HORARIO));
		faltaUpdate.setHoraHorario(horaHorarioPersist);
		
		if(falta.getProfesorSustituto().isPresent()) {
			ProfesorEntity profesorPersistance = profesorRepositoryJPA.findByReferencia(falta.getReferenciaProfesorSustituto())
					.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_PROFESOR));
			faltaUpdate.setProfesorSustituto(profesorPersistance);
		}
	}

	@Override
	@Transactional
	public Stream<Falta> createAll(List<Falta> faltas) {
		List<Falta> faltasSaveAndPersistance = new ArrayList<>();
		for(Falta falta: faltas) {
		
		HoraHorarioEntity horaHorarioPersist=getHoraHorarioPersist(faltaIdMapper.toDto(falta))
				.orElseThrow(()-> new NotFoundException(MESSAGE_NOT_EXIST_HORA_HORARIO));
		
		if(existId(faltaIdMapper.toDto(falta))) {
			continue;
		}
		
		FaltaEntity faltaEntity = new FaltaEntity(falta);
		
		faltaEntity.setHoraHorario(horaHorarioPersist);
		faltaEntity.setProfesorSustituto(null);
		// NO ES NECESARIO PERSISTIR PROFESOR YA QUE PROFESOR SUSTITUTO NO VA A ESTAR EN PRIMERA INSTANCIA
		faltaEntity = faltaRepositoryJPA.save(faltaEntity);
		
		faltasSaveAndPersistance.add(faltaEntity.toFalta());
		}
		
		return faltasSaveAndPersistance.stream();
	}

}
