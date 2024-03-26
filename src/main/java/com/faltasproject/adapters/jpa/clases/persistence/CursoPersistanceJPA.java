package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.CursoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.persistance_ports.clases.CursoPersistance;

@Repository("cursoPersistance")
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
			throw new ConflictExceptions("El Curso con la referencia '"+curso.getReferencia()+"' ya existe");
		}
		
		CursoEntity cursoEntity = new CursoEntity(curso);
		List<MateriasEntity> materias = cursoEntity.getMaterias().stream()
				.map(materia->materiaRepositoryJPA.findByReferencia(materia.getReferencia()).get())
				.collect(Collectors.toList());
		cursoEntity.setMaterias(materias);
		return cursoRepositoryJPA.save(cursoEntity).toCurso();
	}

	@Override
	public Curso update(Long referencia, Curso curso) {
		CursoEntity cursoEntity = cursoRepositoryJPA.findByReferencia(referencia)
		.orElseThrow(() -> new NotFoundException("El curso con la referencia '"+referencia+"' no existe"));
		
		List<MateriasEntity> materias = cursoEntity.getMaterias();
		
		cursoEntity.fromCurso(curso);
		//REFERENCIA Y MATERIAS, NO PUECEN SER CAMBIADAS
		cursoEntity.setReferencia(referencia);
		cursoEntity.setMaterias(materias);
		
		return cursoRepositoryJPA.save(cursoEntity).toCurso();
	}

	@Override
	public Stream<Curso> readAll() {
		return cursoRepositoryJPA.findAll().stream()
				.map(cursoEntity->cursoEntity.toCurso());
	}

	@Override
	public Stream<Curso> readContainInCompleteName(String name) {
		return cursoRepositoryJPA.findByNombreContainingIgnoreCase(name).stream()
				.map(cursoEntity -> cursoEntity.toCurso());
	}

	@Override
	public Boolean delete(Long referencia) {
		if(!existReferencia(referencia)) {
			throw new NotFoundException("El Curso con la referencia '"+referencia+"' no existe");
		}
		
		cursoRepositoryJPA.deleteById(referencia);
		return !existReferencia(referencia);
	}

	@Override
	public Curso readByReferencia(Long referencia) {
		return cursoRepositoryJPA.findByReferencia(referencia)
				.orElseThrow(()-> new NotFoundException("El Curso con la referencia '"+referencia+"' no existe"))
				.toCurso();
	}

	@Override
	public Boolean existReferencia(Long referencia) {
		Optional<CursoEntity> cursoEntity = cursoRepositoryJPA.findByReferencia(referencia);
		return cursoEntity.isPresent();
	}
	

	
}
