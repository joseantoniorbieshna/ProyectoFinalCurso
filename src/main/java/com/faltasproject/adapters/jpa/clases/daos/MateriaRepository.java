package com.faltasproject.adapters.jpa.clases.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

public interface MateriaRepository extends JpaRepository<MateriasEntity, String> {

}
