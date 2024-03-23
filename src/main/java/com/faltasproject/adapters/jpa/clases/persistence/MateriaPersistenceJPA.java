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

	private final MateriaRepositoryJPA materiaRepository;

	public MateriaPersistenceJPA(MateriaRepositoryJPA materiaRepository) {
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Materia create(Materia materia) {
		this.assertIdMateriaNotExist(materia.getId());
		MateriasEntity MateriaEntity = new MateriasEntity(materia);
		return materiaRepository.save(MateriaEntity)
				.toMateria();
	}


	@Override
	public Materia update(String id, Materia materia) {
		MateriasEntity materiaEntity = materiaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado la Materia con el id: " + id));

		materiaEntity.fromMateria(materia);
		return materiaEntity.toMateria();
	}

	@Override
	public Stream<Materia> readContainInCompleteName(String name) {
		return materiaRepository.findByNombreCompletoContainingIgnoreCase(name).stream()
				.map(materia -> materia.toMateria());
	}

	@Override
	public Materia readById(String id) {
		Optional<MateriasEntity> materia = materiaRepository.findById(id);
		if (!materia.isPresent()) {
			throw new NotFoundException("No se ha encontra la Materia con el id: " + id);
		}
		return materia.get().toMateria();
	}

	@Override
	public Boolean delete(String id) {
		if (!existId(id)) {
			throw new NotFoundException("No se ha encontrado la Materia con el id: " + id);
		}
		
		materiaRepository.deleteById(id);
		return !existId(id);
	}

	@Override
	public Stream<Materia> readAll() {
		return materiaRepository.findAll().stream().map(materia -> materia.toMateria());
	}

	@Override
	public boolean existId(String id) {
		return materiaRepository.findById(id).isPresent();
	}


	public void assertIdMateriaNotExist(String id) {
		if(this.existId(id)) {
			throw new ConflictExceptions("La materia don el id '"+id+"' ya existe"); 
		}
	}
}
