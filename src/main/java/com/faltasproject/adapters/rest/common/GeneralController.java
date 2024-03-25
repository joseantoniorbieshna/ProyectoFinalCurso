package com.faltasproject.adapters.rest.common;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.persistance_ports.horario.TramoHorarioPersistance;
import com.faltasproject.domain.services.clases.AulaService;
import com.faltasproject.domain.services.clases.CursoService;
import com.faltasproject.domain.services.clases.MateriaService;
import com.faltasproject.domain.services.horario.TramoHorarioService;
import com.faltasproject.utils.XmlTreatment;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("general")
public class GeneralController {
	
	private final MateriaService materiaService;
	private final CursoService cursoService;
	private final TramoHorarioService tramoHorarioService;
	private final AulaService aulaService;
	
	public GeneralController(MateriaService materiaService,
			CursoService cursoService,
			TramoHorarioService tramoHorarioService,
			AulaService aulaService) {
		this.materiaService=materiaService;
		this.cursoService=cursoService;
		this.tramoHorarioService=tramoHorarioService;
		this.aulaService=aulaService;
	}


	@GetMapping("hola")
	public String hola() {
		return "respuesta";
	}

	@PostMapping("materias")
	public String introducirMaterias(@RequestParam("xml") MultipartFile xml) {

		List<Materia> materias=new ArrayList<>();
		
		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		materias = xmlTreatment.getAllMaterias();
		
		for(Materia materia:materias) {
			materiaService.create(materia);
		}

		return "se han guardado " + materias.size() + " materias";
	}
	
	

	@PostMapping("cursos")
	public String introducirCursos(@RequestParam("xml") MultipartFile xml) {

		List<Curso> cursos=new ArrayList<>();
		
		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		cursos = xmlTreatment.getAllCursos();
		
		for(Curso curso:cursos) {
			cursoService.create(curso);
		}

		return "se han guardado " + cursos.size() + " Cursos y en total tienen "+cursos.stream().flatMap(curso -> curso.getMaterias().stream()).count()+" materias relacionadas";
	}
	
	@PostMapping("tramoshorarios")
	public String introducirTramosHorarios(@RequestParam("xml") MultipartFile xml) {

		List<TramoHorario> tramosHorarios=new ArrayList<>();
		
		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		tramosHorarios = xmlTreatment.getAllTramosHorarios();
		
		for(TramoHorario tramoHorario:tramosHorarios) {
			tramoHorarioService.create(tramoHorario);
		}

		return "se han guardado " + tramosHorarios.size() + " TramosHorario";
	}
	
	@PostMapping("aulas")
	public String introducirAulas(@RequestParam("xml") MultipartFile xml) {

		List<Aula> aulas = new ArrayList<>();
		
		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		aulas = xmlTreatment.getAllAulas();
		
		for(Aula aula:aulas) {
			aulaService.create(aula);
		}

		return "se han guardado " + aulas.size() + " Aulas";
	}
	
	

}
