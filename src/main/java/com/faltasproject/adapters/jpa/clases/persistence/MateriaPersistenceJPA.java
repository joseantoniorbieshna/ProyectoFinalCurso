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

import jakarta.transaction.Transactional;

@Repository("materiaPersistance")
public class MateriaPersistenceJPA implements MateriaPersistance {
	
	private final MateriaRepositoryJPA materiaRepository;
	
	public MateriaPersistenceJPA(MateriaRepositoryJPA materiaRepository) {
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Materia create(Materia materia) {
		if(this.existReferencia(materia.getReferencia())) {
			throw new ConflictExceptions("La materia con la referencia '"+materia.getReferencia()+"' ya existe"); 
		}
		MateriasEntity MateriaEntity = new MateriasEntity(materia);
		return materiaRepository.save(MateriaEntity)
				.toMateria();
	}


	@Override
	public Materia update(String referencia, Materia materia) {
		MateriasEntity materiaEntity = materiaRepository.findByReferencia(referencia)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado la Materia con la referencia: " + referencia));

		materiaEntity.fromMateria(materia);
		//NO SE PUEDE CAMBIAR NI LA REFERENCIA
		materiaEntity.setReferencia(referencia);
		
		return materiaEntity.toMateria();
	}

	@Override
	public Stream<Materia> readContainInCompleteName(String name) {
		return materiaRepository.findByNombreCompletoContainingIgnoreCase(name).stream()
				.map(materia -> materia.toMateria());
	}

	@Override
	public Materia readByReferencia(String referencia) {
		Optional<MateriasEntity> materia = materiaRepository.findByReferencia(referencia);
		if (!materia.isPresent()) {
			throw new NotFoundException("No se ha encontra la Materia con la referencia: " + referencia);
		}
		return materia.get().toMateria();
	}

	@Override
	@Transactional
	public Boolean delete(String referencia) {
		if (!existReferencia(referencia)) {
			throw new NotFoundException("No se ha encontrado la Materia con la referencia: " + referencia);
		}
		
		materiaRepository.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Stream<Materia> readAll() {
		return materiaRepository.findAll().stream().map(materia -> materia.toMateria());
	}

	@Override
	public Boolean existReferencia(String referencia) {
		return materiaRepository.findByReferencia(referencia).isPresent();
	}
}
