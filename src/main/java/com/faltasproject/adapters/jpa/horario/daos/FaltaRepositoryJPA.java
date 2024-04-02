package com.faltasproject.adapters.jpa.horario.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.horario.entities.FaltaEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.FaltaKey;

public interface FaltaRepositoryJPA extends JpaRepository<FaltaEntity, FaltaKey> {

}
