package com.faltasproject.adapters.jpa.horario.daos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.horario.entities.FaltaEntity;
import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;

public interface FaltaRepositoryJPA extends JpaRepository<FaltaEntity, Long> {
	Optional<FaltaEntity> findByHoraHorarioAndFecha(HoraHorarioEntity horaHorario, LocalDate fecha);
	
	List<FaltaEntity> findByFecha(LocalDate fecha);
	
	List<FaltaEntity> findByFechaBetween(LocalDate fechaInicio,LocalDate fechaFin);
}
