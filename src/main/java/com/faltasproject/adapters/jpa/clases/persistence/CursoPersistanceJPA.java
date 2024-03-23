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
		if(existId(curso.getId())) {
			throw new ConflictExceptions("El Curso con el id '"+curso.getId()+"' ya existe");
		}
		
		CursoEntity cursoEntity = new CursoEntity(curso);
		List<MateriasEntity> materias = cursoEntity.getMaterias().stream()
				.map(materiaEntity->materiaRepositoryJPA.findById(materiaEntity.getId()).get())
				.collect(Collectors.toList());
		cursoEntity.setMaterias(materias);
		return cursoRepositoryJPA.save(cursoEntity).toCurso();
	}

	@Override
	public Curso update(Long id, Curso curso) {
		CursoEntity cursoEntity = cursoRepositoryJPA.findById(id)
		.orElseThrow(() -> new NotFoundException("El curso con el id '"+id+"' no existe"));
		
		cursoEntity.fromCurso(curso);
		
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
	public Boolean delete(Long id) {
		if(!existId(id)) {
			throw new NotFoundException("El Curso con el id '"+id+"' no existe");
		}
		
		cursoRepositoryJPA.deleteById(id);
		return !existId(id);
	}

	@Override
	public Curso readById(Long id) {
		return cursoRepositoryJPA.findById(id)
				.orElseThrow(()-> new NotFoundException("El Curso con el id '"+id+"' no existe"))
				.toCurso();
	}

	@Override
	public Boolean existId(Long id) {
		Optional<CursoEntity> cursoEntity = cursoRepositoryJPA.findById(id);
		return cursoEntity.isPresent();
	}
	

	
}
