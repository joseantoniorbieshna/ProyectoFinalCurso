package com.faltasproject.adapters.jpa.horario.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.TramoHorarioKey;


public interface TramoHorarioRepositoryJPA extends JpaRepository<TramoHorarioEntity, TramoHorarioKey> {
}
