package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.CursoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.persistance_ports.clases.GrupoPersistance;

@Repository("grupoPersistance")
public class GrupoPersistanceJPA implements GrupoPersistance {

	private final GrupoRepositoryJPA grupoRepositoryJPA;
	private final CursoRepositoryJPA cursoRepositoryJPA;
	
	
	
	public GrupoPersistanceJPA(GrupoRepositoryJPA grupoRepositoryJPA,CursoRepositoryJPA cursoRepositoryJPA) {
		super();
		this.grupoRepositoryJPA = grupoRepositoryJPA;
		this.cursoRepositoryJPA = cursoRepositoryJPA;
	}

	@Override
	public Grupo create(Grupo grupo) {
		if(existNombre(grupo.getNombre())) {
			throw new ConflictExceptions("El Grupo con el nombre '"+grupo.getNombre()+"' ya existe");
		}
		GrupoEntity grupoEntity = new GrupoEntity(grupo);
		
		Optional<CursoEntity> cursoEntity = cursoRepositoryJPA.findByReferencia(grupo.getReferenciaCurso());
		if(!cursoEntity.isPresent()) {
			throw new ConflictExceptions("Necesitas que haya una referencia exisente de curso en el grupo");
		}
		grupoEntity.setCurso(cursoEntity.get());
		
		return grupoRepositoryJPA.save(grupoEntity).toGrupo();
	}

	@Override
	public Grupo update(String nombre, Grupo grupo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Grupo> readAll() {
		return grupoRepositoryJPA.findAll().stream()
				.map(grupoEntity->grupoEntity.toGrupo());
	}

	@Override
	public Stream<Grupo> readContainInName(String search) {
		return grupoRepositoryJPA.findByNombreContainingIgnoreCase(search).stream()
				.map(grupoEntity->grupoEntity.toGrupo());
	}

	@Override
	public boolean delete(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Grupo readByNombre(String nombre) {
		return grupoRepositoryJPA.findByNombreEquals(nombre)
				.orElseThrow(()-> new NotFoundException("El grupo con el nombre '"+nombre+"' no existe"))
				.toGrupo();
	}

	@Override
	public boolean existNombre(String nombre) {
		return grupoRepositoryJPA.findByNombreEquals(nombre).isPresent();
	}
	
}
