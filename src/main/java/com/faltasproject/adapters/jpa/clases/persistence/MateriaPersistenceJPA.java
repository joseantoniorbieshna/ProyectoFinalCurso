package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.MateriaRepository;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.persistance_ports.clases.MateriaPersistance;

@Repository("materiaPersistance")
public class MateriaPersistenceJPA implements MateriaPersistance {
	
	private final MateriaRepository materiaRepository;
	
	public MateriaPersistenceJPA(MateriaRepository materiaRepository) {
		this.materiaRepository=materiaRepository;
	}

	@Override
	public Materia create(Materia materia) {
		MateriasEntity entity = new MateriasEntity(materia);
		return materiaRepository.save(entity).toMateria();
	}

	@Override
	public Materia update(Materia materia) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Materia readByCompleteName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Materia readByDni(String id) {
		Optional<MateriasEntity> materia=materiaRepository.findById(id);
		if(!materia.isPresent()) {
			throw new NotFoundException("error");
		}
		return materia.get().toMateria();
	}

	@Override
	public Stream<Materia> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existId(String id) {
		return materiaRepository.findById(id).isPresent();
	}

}
