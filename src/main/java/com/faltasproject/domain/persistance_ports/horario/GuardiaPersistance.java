package com.faltasproject.domain.persistance_ports.horario;

import java.util.stream.Stream;

import com.faltasproject.domain.models.horario.Guardia;
import com.faltasproject.domain.models.horario.dtos.IdGuardiaDTO;

public interface GuardiaPersistance {
	Guardia create(Guardia guardia);

	Guardia update(IdGuardiaDTO idGuardia, Guardia guardia);

	Stream<Guardia> readAll();

	Guardia readById(IdGuardiaDTO idGuardiaDTO);

	void delete(IdGuardiaDTO idGuardiaDTO);

	boolean existId(IdGuardiaDTO idGuardiaDTO);
}
