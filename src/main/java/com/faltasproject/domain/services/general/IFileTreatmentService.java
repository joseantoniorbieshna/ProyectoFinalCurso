package com.faltasproject.domain.services.general;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.dto.InputHoraHorarioDTO;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.clases.dtos.CursoCreateDTO;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdGuardiaDTO;
import com.faltasproject.domain.models.profesorado.Profesor;

@Service
public interface IFileTreatmentService {
	Set<Materia> getAllMaterias();
	Set<CursoCreateDTO> getAllCursos();
	Set<TramoHorario> getAllTramosHorarios();
	Set<Aula> getAllAulas();
	Set<Grupo> getAllGrupos();
	Set<Profesor> getAllProfesores();
	Set<Sesion> getAllSesiones();
	Set<InputHoraHorarioDTO> getAllHoraHorario();
	Set<IdGuardiaDTO> getAllGuardias();
}
