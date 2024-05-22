package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.CursoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.persistance_ports.clases.CursoPersistance;

import jakarta.transaction.Transactional;


@Repository("cursoPersistance")
@Transactional
public class CursoPersistanceJPA implements CursoPersistance {
	
	private final CursoRepositoryJPA cursoRepositoryJPA;
	private final MateriaRepositoryJPA materiaRepositoryJPA;
	
	public CursoPersistanceJPA(CursoRepositoryJPA cursoRepositoryJPA, MateriaRepositoryJPA materiaRepositoryJPA) {
		super();
		this.cursoRepositoryJPA = cursoRepositoryJPA;
		this.materiaRepositoryJPA = materiaRepositoryJPA;
	}

	@Override
	public Curso create(Curso curso) {
		if(existReferencia(curso.getReferencia())) {
			throw new ConflictException(getMessageErrorExist(curso.getReferencia()));
		}
		
		Set<MateriasEntity> materiaPersistData = getMateriaPersistData(curso);
		CursoEntity cursoEntity = new CursoEntity(curso);
		cursoEntity.setMaterias(materiaPersistData);
		
		return cursoRepositoryJPA.save(cursoEntity).toCurso();
	}

	@Override
	public Curso update(String referencia, Curso curso) {
		CursoEntity cursoEntity = cursoRepositoryJPA.findByReferencia(referencia)
		.orElseThrow(() -> new NotFoundException(getMessageErrorNotExist(referencia)));
		
		if(!referencia.equals(curso.getReferencia()) && existReferencia(curso.getReferencia())) {
			throw new ConflictException(String.format("El curso con la referencia %s a la que quieres cambiar ya existe.", curso.getReferencia()));
		}
		

		//CAMBIAMOS LOS DATOS Y PERSISTIMOS MATERIAS
		Set<MateriasEntity> materiaPersistData = getMateriaPersistData(curso);
		cursoEntity.fromCurso(curso);
		cursoEntity.setMaterias(materiaPersistData);
		
		return cursoRepositoryJPA.save(cursoEntity).toCurso();
	}

	private Set<MateriasEntity> getMateriaPersistData(Curso curso) {
		return curso.getMaterias().stream().map(materia -> materiaRepositoryJPA.findByReferencia(materia.getReferencia())
				.orElseThrow(()->new NotFoundException( getMateriaErrorNotFound(materia.getReferencia()) )))
				.collect(Collectors.toSet());
		
	}

	@Override
	public Stream<Curso> readAll() {
		return cursoRepositoryJPA.findAll().stream()
				.map(CursoEntity::toCurso);
	}

	@Override
	public Stream<Curso> readContainInCompleteName(String name) {
		return cursoRepositoryJPA.findByNombreContainingIgnoreCase(name).stream()
				.map(CursoEntity::toCurso);
	}

	@Override
	public boolean delete(String referencia) {
		if(!existReferencia(referencia)) {
			throw new NotFoundException(getMessageErrorNotExist(referencia));
		}
		
		cursoRepositoryJPA.deleteByReferencia(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Curso readByReferencia(String referencia) {
		return cursoRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(()-> new NotFoundException(getMessageErrorNotExist(referencia)))
				.toCurso();
	}

	@Override
	public boolean existReferencia(String referencia) {
		Optional<CursoEntity> cursoEntity = cursoRepositoryJPA.findByReferencia(referencia);
		return cursoEntity.isPresent();
	}
	
	private String getMessageErrorExist(String referencia) {
		return "El curso con la referencia '"+referencia+"' ya existe";
	}
	
	private String getMessageErrorNotExist(String referencia) {
		return "El curso con la referencia '"+referencia+"' no existe";
	}
	
	private String getMateriaErrorNotFound(String referencia) {
		return "La meteria con la referencia '"+referencia+"' no existe";
	}

	
}
