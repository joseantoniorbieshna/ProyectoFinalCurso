package com.faltasproject.adapters.rest.common;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.services.clases.AulaService;
import com.faltasproject.domain.services.clases.CursoService;
import com.faltasproject.domain.services.clases.GrupoService;
import com.faltasproject.domain.services.clases.MateriaService;
import com.faltasproject.domain.services.horario.TramoHorarioService;
import com.faltasproject.domain.services.profesorado.ProfesorService;
import com.faltasproject.utils.XmlTreatment;


@RestController
@RequestMapping("general")
public class GeneralController {
	
	private final MateriaService materiaService;
	private final CursoService cursoService;
	private final TramoHorarioService tramoHorarioService;
	private final AulaService aulaService;
	private final GrupoService grupoService;
	private final ProfesorService profesorService;
	
	public GeneralController(MateriaService materiaService,
			CursoService cursoService,
			TramoHorarioService tramoHorarioService,
			AulaService aulaService,
			GrupoService grupoService,
			ProfesorService profesorService) {
		this.materiaService=materiaService;
		this.cursoService=cursoService;
		this.tramoHorarioService=tramoHorarioService;
		this.aulaService=aulaService;
		this.grupoService=grupoService;
		this.profesorService=profesorService;
	}


	@GetMapping("hola")
	public String hola() {
		return "respuesta";
	}

	@PostMapping("materias")
	public String introducirMaterias(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Materia> materias = xmlTreatment.getAllMaterias();
		
		for(Materia materia:materias) {
			materiaService.create(materia);
		}

		return "se han guardado " + materias.size() + " materias";
	}
	
	

	@PostMapping("cursos")
	public String introducirCursos(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Curso> cursos = xmlTreatment.getAllCursos();
		
		for(Curso curso:cursos) {
			cursoService.create(curso);
		}

		return "se han guardado " + cursos.size() + " Cursos y en total tienen "+cursos.stream().flatMap(curso -> curso.getMaterias().stream()).count()+" materias relacionadas";
	}
	
	@PostMapping("tramoshorarios")
	public String introducirTramosHorarios(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<TramoHorario> tramosHorarios = xmlTreatment.getAllTramosHorarios();
		
		for(TramoHorario tramoHorario:tramosHorarios) {
			tramoHorarioService.create(tramoHorario);
		}

		return "se han guardado " + tramosHorarios.size() + " TramosHorario";
	}
	
	@PostMapping("aulas")
	public String introducirAulas(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Aula> aulas = xmlTreatment.getAllAulas();
		
		for(Aula aula:aulas) {
			aulaService.create(aula);
		}

		return "se han guardado " + aulas.size() + " Aulas";
	}
	
	@PostMapping("grupos")
	public String introducirGrupos(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Grupo> grupos = xmlTreatment.getAllGrupos();
		
		for(Grupo grupo:grupos) {
			grupoService.create(grupo);
		}

		return "se han guardado " + grupos.size() + " grupos";
	}
	
	@PostMapping("profesores")
	public String introducirProfesores(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Profesor> profesores = xmlTreatment.getAllProfesores();
		
		for(Profesor profesor:profesores) {
			profesorService.create(profesor);
		}

		return "se han guardado " + profesores.size() + " profesores";
	}
	
	

}
