package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.MateriaRepository;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.clases.models.Materia;
import com.faltasproject.domain.clases.persistance_ports.MateriaPersistance;

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
	public Stream<Materia> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Materia readByName() {
		// TODO Auto-generated method stub
		return null;
	}

}
