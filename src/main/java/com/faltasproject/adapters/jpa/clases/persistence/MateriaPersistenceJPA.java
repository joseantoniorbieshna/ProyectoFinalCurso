package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.persistance_ports.clases.MateriaPersistance;

import jakarta.transaction.Transactional;


@Repository("materiaPersistance")
@Transactional
public class MateriaPersistenceJPA implements MateriaPersistance {
	
	private final MateriaRepositoryJPA materiaRepository;
	
	public MateriaPersistenceJPA(MateriaRepositoryJPA materiaRepository) {
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Materia create(Materia materia) {
		if(this.existReferencia(materia.getReferencia())) {
			throw new ConflictException(getMessageErrorExist(materia.getReferencia())); 
		}
		MateriasEntity materiaEntity = new MateriasEntity(materia);
		return materiaRepository.save(materiaEntity)
				.toMateria();
	}


	@Override
	public Materia update(String referencia, Materia materia) {
		MateriasEntity materiaEntity = materiaRepository.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException(getMessageErrorNotExist(referencia)));

		if(!referencia.equals(materia.getReferencia()) && existReferencia(materia.getReferencia())) {
			throw new ConflictException(String.format("La materia con la referencia '%s' a la que quieres actualizar ya existe.", materia.getReferencia()));
		}
		
		// CAMBIAMOS LOS DATOS
		materiaEntity.fromMateria(materia);
		
		return materiaRepository.save(materiaEntity).toMateria();
	}

	@Override
	public Stream<Materia> readContainInCompleteName(String name) {
		return materiaRepository.findByNombreCompletoContainingIgnoreCase(name).stream()
				.map(MateriasEntity::toMateria);
	}

	@Override
	public Materia readByReferencia(String referencia) {
		Optional<MateriasEntity> materia = materiaRepository.findByReferencia(referencia);
		if (!materia.isPresent()) {
			throw new NotFoundException(getMessageErrorNotExist(referencia));
		}
		return materia.get().toMateria();
	}

	@Override
	public void delete(String referencia) {
		if (!existReferencia(referencia)) {
			throw new NotFoundException(getMessageErrorNotExist(referencia));
		}
		
		materiaRepository.deleteByReferencia(referencia);
	}

	@Override
	public Stream<Materia> readAll() {
		return materiaRepository.findAll().stream().map(MateriasEntity::toMateria);
	}

	@Override
	public boolean existReferencia(String referencia) {
		return materiaRepository.findByReferencia(referencia).isPresent();
	}
	
	private String getMessageErrorExist(String referencia) {
		return "La materia con la referencia '"+referencia+"' ya existe";
	}
	private String getMessageErrorNotExist(String referencia) {
		return "La materia con la referencia '"+referencia+"' no existe";
	}
}
