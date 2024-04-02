package com.faltasproject.adapters.jpa.horario.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;

public interface HoraHorarioRepositoryJPA extends JpaRepository<HoraHorarioEntity, Long> {

}
