package com.faltasproject.adapters.jpa.profesorado.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.models.usuario.RoleEnum;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


public interface ProfesorRepositoryJPA extends JpaRepository<ProfesorEntity, Long> {
	Optional<ProfesorEntity> findByReferencia(String referencia);
	
	@Query("SELECT p FROM ProfesorEntity p " +
		       "JOIN p.usuario u " +
		       "JOIN u.role r " +
		       "WHERE r.roleEnum = :roleEnum")
	List<ProfesorEntity> findAllByRole(RoleEnum roleEnum);
	@Transactional
	void deleteByReferencia(String referencia);
}
