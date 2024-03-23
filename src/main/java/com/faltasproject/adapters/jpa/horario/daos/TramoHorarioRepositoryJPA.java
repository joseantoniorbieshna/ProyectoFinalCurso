package com.faltasproject.adapters.jpa.horario.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.KeyTramoHorario;
import java.util.Optional;


public interface TramoHorarioRepositoryJPA extends JpaRepository<TramoHorarioEntity, KeyTramoHorario> {
	Optional<TramoHorarioEntity> findByKeyDiaAndKeyIndice(Integer dia, Integer indice);
	void deleteByKeyDiaAndKeyIndice(Integer dia, Integer indice);
}
