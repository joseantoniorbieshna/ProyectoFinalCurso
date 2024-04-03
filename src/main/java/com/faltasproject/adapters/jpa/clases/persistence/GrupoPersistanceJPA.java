package com.faltasproject.adapters.jpa.clases.persistence;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.CursoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.persistance_ports.clases.GrupoPersistance;

@Repository("grupoPersistance")
public class GrupoPersistanceJPA implements GrupoPersistance {
	
	private static final String ERROR_CURSO_NOT_EXIST="Necesitas que haya una referencia exisente de curso en el grupo";

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
			throw new ConflictException(getMessageErrorExist(grupo.getNombre()));
		}
		GrupoEntity grupoEntity = new GrupoEntity(grupo);
		grupoEntity.setCurso(getCursoPersistByReferenciaCurso(grupo.getReferenciaCurso()));
		
		return grupoRepositoryJPA.save(grupoEntity).toGrupo();
	}

	@Override
	public Grupo update(String nombre, Grupo grupo) {
		GrupoEntity grupoEntity=grupoRepositoryJPA.findByNombreEquals(nombre)
		.orElseThrow(()-> new NotFoundException(getMessageErrorNotExist(nombre)));
		
		//OBLIGAMOS A TENER NOMBRE
		if(grupo.getNombre()==null) {
			grupo.setNombre(nombre);
		}else if( !nombre.equals(grupo.getNombre()) &&
				existNombre(grupo.getNombre())) {
			throw new ConflictException(getMessageErrorExist(grupo.getNombre()));
		}
		//CAMBIAMOS LOS DATOS
		grupoEntity.fromGrupo(grupo);
		grupoEntity.setCurso(getCursoPersistByReferenciaCurso(grupo.getReferenciaCurso()));
		
		return grupoRepositoryJPA.save(grupoEntity)
				.toGrupo();
	}

	@Override
	public Stream<Grupo> readAll() {
		return grupoRepositoryJPA.findAll().stream()
				.map(GrupoEntity::toGrupo);
	}

	@Override
	public Stream<Grupo> readContainInName(String search) {
		return grupoRepositoryJPA.findByNombreContainingIgnoreCase(search).stream()
				.map(GrupoEntity::toGrupo);
	}

	@Override
	public boolean delete(String nombre) {
		if(!existNombre(nombre)) {
			throw new NotFoundException(getMessageErrorNotExist(nombre));
		}
		grupoRepositoryJPA.deleteByNombreEquals(nombre);
		return !existNombre(nombre);
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
	
	private String getMessageErrorExist(String nombre) {
		return "El Grupo con el nombre '"+nombre+"' ya existe";
	}
	private String getMessageErrorNotExist(String nombre) {
		return "El Grupo con el nombre '"+nombre+"' no existe";
	}
	
	private CursoEntity getCursoPersistByReferenciaCurso(String referenciaCurso) {
		return cursoRepositoryJPA.findByReferencia(referenciaCurso)
				.orElseThrow( ()-> new NotFoundException(ERROR_CURSO_NOT_EXIST));
	}
	
}
