package com.faltasproject.adapters.jpa.horario.daos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.horario.entities.FaltaEntity;
import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.FaltaKey;

public interface FaltaRepositoryJPA extends JpaRepository<FaltaEntity, FaltaKey> {
	
	List<FaltaEntity> findByKeyFecha(LocalDate fecha);
	
	List<FaltaEntity> findByKeyFechaBetween(LocalDate fechaInicio,LocalDate fechaFin);
	
	Optional<FaltaEntity> findByKeyHoraHorarioAndKeyFecha(HoraHorarioEntity horaHorarioEntity, LocalDate fehca);

}
