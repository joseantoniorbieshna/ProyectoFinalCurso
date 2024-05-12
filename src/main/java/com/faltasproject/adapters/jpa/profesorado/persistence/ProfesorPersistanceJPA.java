package com.faltasproject.adapters.jpa.profesorado.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.persistance_ports.profesorado.ProfesorPersistance;
import com.faltasproject.security.usuarios.daos.UserRepositoryJPA;
import com.faltasproject.security.usuarios.entity.UsuarioEntity;

@Repository("profesorPersistance")
public class ProfesorPersistanceJPA implements ProfesorPersistance {

	ProfesorRepositoryJPA profesorRepositoryJPA;
	UserRepositoryJPA userRepositoryJPA;

	public ProfesorPersistanceJPA(ProfesorRepositoryJPA profesorRepositoryJPA, UserRepositoryJPA userRepositoryJPA) {
		super();
		this.profesorRepositoryJPA = profesorRepositoryJPA;
		this.userRepositoryJPA = userRepositoryJPA;
	}

	@Override
	public Profesor create(Profesor profesor) {
		if (existReferencia(profesor.getReferencia())) {
			throw new ConflictException(profesor.getReferencia());
		}
		ProfesorEntity profesorCreate = new ProfesorEntity(profesor);
		persistanceUser(profesorCreate);

		return profesorRepositoryJPA.save(profesorCreate).toProfesor();
	}

	@Override
	public Profesor update(String referencia, Profesor profesor) {
		ProfesorEntity profesorEntiy = profesorRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException(getMessageErrorExist(referencia)));

		if (!referencia.equals(profesor.getReferencia()) && existReferencia(profesor.getReferencia())) {
			throw new ConflictException(
					String.format("El profesor con la referencia '%s' a la que intentas actualizar ya existe.",
							profesor.getReferencia()));
		}

		// actualizamos
		profesorEntiy.fromProfesor(profesor);

		persistanceUser(profesorEntiy);

		return profesorRepositoryJPA.save(profesorEntiy).toProfesor();
	}

	@Override
	public Stream<Profesor> readAll() {
		return profesorRepositoryJPA.findAll().stream().map(ProfesorEntity::toProfesor);
	}

	@Override
	public boolean delete(String referencia) {
		if (!existReferencia(referencia)) {
			throw new NotFoundException(getMessageErrorNotExist(referencia));
		}
		profesorRepositoryJPA.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Profesor readByReferencia(String referencia) {
		return profesorRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException(getMessageErrorNotExist(referencia))).toProfesor();
	}

	@Override
	public boolean existReferencia(String referencia) {
		return profesorRepositoryJPA.findByReferencia(referencia).isPresent();
	}

	private String getMessageErrorNotExist(String referencia) {
		return "El profesor con la referencia '" + referencia + "' no existe";
	}

	private String getMessageErrorExist(String referencia) {
		return "El profesor con la referencia '" + referencia + "' ya existe";
	}

	private void persistanceUser(ProfesorEntity profesorEntiy) {
		if (profesorEntiy.getUsuario() != null) {
			String username = profesorEntiy.getUsuario().getUsername();
			Optional<UsuarioEntity> user = userRepositoryJPA.findByUsername(username);
			if (!user.isPresent()) {
				throw new NotFoundException("El usuari con el nombre " + username + " no existe");
			}
			profesorEntiy.setUsuario(user.get());
		}
	}

}
