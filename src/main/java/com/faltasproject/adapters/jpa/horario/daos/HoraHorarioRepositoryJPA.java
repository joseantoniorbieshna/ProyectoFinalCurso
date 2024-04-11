package com.faltasproject.adapters.jpa.horario.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;

public interface HoraHorarioRepositoryJPA extends JpaRepository<HoraHorarioEntity, Long> {
	@Query("SELECT hh FROM HoraHorarioEntity hh " +
		       "JOIN hh.tramoHorario th " +
		       "JOIN hh.sesion s " +
		       "WHERE s.referencia = :referenciaSesion and th.key.dia = :dia and th.key.indice = :indice")
		Optional<HoraHorarioEntity> findByReferenciaAndTramoHorario(String referenciaSesion, int dia, int indice);
	
	// Importante: para hacer JPQL, tiene que ser el nombre de la clase y el nombre de los atributos de la clase
	@Query("SELECT hh FROM HoraHorarioEntity hh " +
		       "JOIN hh.sesion s " +
		       "JOIN s.profesor p " +
		       "WHERE p.referencia = :referenciaProfesor")
    List<HoraHorarioEntity> findAllByProfesorFaltante(String referenciaProfesor);
	
	@Query("SELECT hh FROM HoraHorarioEntity hh " +
		       "JOIN hh.sesion s "+
		       "WHERE s.referencia = :referenciaSesion")
	List<HoraHorarioEntity> findAllByReferenciaSesion(String referenciaSesion);
	
	@Query("SELECT hh FROM HoraHorarioEntity hh " +
		       "JOIN hh.tramoHorario th "+
		       "WHERE th.key.dia = :dia and th.key.indice = :indice")
	List<HoraHorarioEntity> findAllByTramoHorario(int dia, int indice);

}
