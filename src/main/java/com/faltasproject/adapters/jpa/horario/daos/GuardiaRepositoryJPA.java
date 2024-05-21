package com.faltasproject.adapters.jpa.horario.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faltasproject.adapters.jpa.horario.entities.GuardiaEntity;


public interface GuardiaRepositoryJPA extends JpaRepository<GuardiaEntity, Long> {
	
	@Query("SELECT g FROM GuardiaEntity g " +
		       "JOIN g.tramoHorario th " +
		       "JOIN g.profesor p " +
		       "WHERE p.referencia = :referenciaProfesor AND th.key.dia = :dia AND th.key.indice = :indice")
	Optional<GuardiaEntity> findByReferencia(String referenciaProfesor,int dia, int indice);
	
	@Query("DELETE FROM GuardiaEntity g " +
		       "WHERE g.profesor.referencia = :referenciaProfesor AND " +
		       "g.tramoHorario.key.dia = :dia AND g.tramoHorario.key.indice = :indice")
	void deleteByReferencia(String referenciaProfesor,int dia, int indice);

}
