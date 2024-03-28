package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.persistance_ports.clases.MateriaPersistance;


@Repository("materiaPersistance")
public class MateriaPersistenceJPA implements MateriaPersistance {
	
	private static final String FIRST_PART_MESSAGE_EXIST_OR_NOT_REFERENCIA="La materia con la referencia '";
	private static final String SECOND_PART_MESSAGE_EXIST_REFERENCIA="' ya existe";
	private static final String SECOND_PART_MESSAGE_NOT_EXIST_REFERENCIA="' no existe";
	
	private final MateriaRepositoryJPA materiaRepository;
	
	public MateriaPersistenceJPA(MateriaRepositoryJPA materiaRepository) {
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Materia create(Materia materia) {
		if(this.existReferencia(materia.getReferencia())) {
			throw new ConflictExceptions(FIRST_PART_MESSAGE_EXIST_OR_NOT_REFERENCIA+materia.getReferencia()+SECOND_PART_MESSAGE_EXIST_REFERENCIA); 
		}
		MateriasEntity materiaEntity = new MateriasEntity(materia);
		return materiaRepository.save(materiaEntity)
				.toMateria();
	}


	@Override
	public Materia update(String referencia, Materia materia) {
		MateriasEntity materiaEntity = materiaRepository.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException(FIRST_PART_MESSAGE_EXIST_OR_NOT_REFERENCIA+ referencia + SECOND_PART_MESSAGE_NOT_EXIST_REFERENCIA));

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
			throw new NotFoundException(FIRST_PART_MESSAGE_EXIST_OR_NOT_REFERENCIA + referencia + SECOND_PART_MESSAGE_NOT_EXIST_REFERENCIA);
		}
		return materia.get().toMateria();
	}

	@Override
	public boolean delete(String referencia) {
		if (!existReferencia(referencia)) {
			throw new NotFoundException(FIRST_PART_MESSAGE_EXIST_OR_NOT_REFERENCIA + referencia + SECOND_PART_MESSAGE_NOT_EXIST_REFERENCIA);
		}
		
		materiaRepository.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Stream<Materia> readAll() {
		return materiaRepository.findAll().stream().map(MateriasEntity::toMateria);
	}

	@Override
	public boolean existReferencia(String referencia) {
		return materiaRepository.findByReferencia(referencia).isPresent();
	}
}
