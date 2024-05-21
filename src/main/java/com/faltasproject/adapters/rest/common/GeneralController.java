package com.faltasproject.adapters.rest.common;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.faltasproject.domain.dto.InputHoraHorarioDTO;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdGuardiaDTO;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.services.clases.AulaService;
import com.faltasproject.domain.services.clases.CursoService;
import com.faltasproject.domain.services.clases.GrupoService;
import com.faltasproject.domain.services.clases.MateriaService;
import com.faltasproject.domain.services.clases.SesionService;
import com.faltasproject.domain.services.horario.GuardiaService;
import com.faltasproject.domain.services.horario.HoraHorarioService;
import com.faltasproject.domain.services.horario.TramoHorarioService;
import com.faltasproject.domain.services.profesorado.ProfesorService;
import com.faltasproject.utils.InitialDataBase;
import com.faltasproject.utils.XmlTreatment;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("general")
public class GeneralController {
	private static final String MENSAJE_GURADADO_TOTAL_DE = "Se ha guardado un total de ";
	
	@Autowired
	private MateriaService materiaService;
	@Autowired
	private CursoService cursoService;
	@Autowired
	private TramoHorarioService tramoHorarioService;
	@Autowired
	private AulaService aulaService;
	@Autowired
	private GrupoService grupoService;
	@Autowired
	private ProfesorService profesorService;
	@Autowired
	private SesionService sesionService;
	@Autowired
	private HoraHorarioService horaHorarioService;
	@Autowired
	private GuardiaService guardiaService;
	@Autowired
	private InitialDataBase initialDataBase;

	

	public String introducirMaterias(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Materia> materias = xmlTreatment.getAllMaterias();
		
		for(Materia materia:materias) {
			materiaService.create(materia);
		}

		return MENSAJE_GURADADO_TOTAL_DE + materias.size() + " materias";
	}
	
	


	public String introducirCursos(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Curso> cursos = xmlTreatment.getAllCursos();
		
		for(Curso curso:cursos) {
			cursoService.create(curso);
		}

		return MENSAJE_GURADADO_TOTAL_DE + cursos.size() + " Cursos y en total tienen "+cursos.stream().flatMap(curso -> curso.getMaterias().stream()).count()+" materias relacionadas";
	}
	

	public String introducirTramosHorarios(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<TramoHorario> tramosHorarios = xmlTreatment.getAllTramosHorarios();
		
		for(TramoHorario tramoHorario:tramosHorarios) {
			tramoHorarioService.create(tramoHorario);
		}

		return MENSAJE_GURADADO_TOTAL_DE + tramosHorarios.size() + " TramosHorario";
	}
	

	public String introducirAulas(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Aula> aulas = xmlTreatment.getAllAulas();
		
		for(Aula aula:aulas) {
			aulaService.create(aula);
		}

		return MENSAJE_GURADADO_TOTAL_DE + aulas.size() + " Aulas";
	}
	

	public String introducirGrupos(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Grupo> grupos = xmlTreatment.getAllGrupos();
		
		for(Grupo grupo:grupos) {
			grupoService.create(grupo);
		}

		return MENSAJE_GURADADO_TOTAL_DE + grupos.size() + " grupos";
	}
	

	public String introducirProfesores(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Profesor> profesores = xmlTreatment.getAllProfesores();
		
		for(Profesor profesor:profesores) {
			profesorService.create(profesor);
		}

		return MENSAJE_GURADADO_TOTAL_DE + profesores.size() + " profesores";
	}
	

	public String introducirSesiones(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<Sesion> sesiones = xmlTreatment.getAllSesiones();
		
		for(Sesion sesion:sesiones) {
			sesionService.create(sesion);
		}

		return MENSAJE_GURADADO_TOTAL_DE + sesiones.size() + " sesiones";
	}
	

	public String introducirHorasHorario(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<InputHoraHorarioDTO> horarios = xmlTreatment.getAllHoraHorario();
		
		for(InputHoraHorarioDTO horario:horarios) {
			horaHorarioService.create(horario);
		}

		return MENSAJE_GURADADO_TOTAL_DE + horarios.size() + " hora de horarios";
	}
	

	public String introducirGuardias(@RequestParam("xml") MultipartFile xml) {

		XmlTreatment xmlTreatment = new XmlTreatment(xml);

		Set<IdGuardiaDTO> guardias = xmlTreatment.getAllGuardias();
		
		for(IdGuardiaDTO guardia:guardias) {
			guardiaService.create(guardia);
		}

		return MENSAJE_GURADADO_TOTAL_DE + guardias.size() + " guardias";
	}
	
	@PostMapping("all")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Transactional
	public ResponseEntity<String> all(@RequestParam("xml") MultipartFile xml) {
		initialDataBase.clearToInitialDatabase();
		
		String result = "";
		result = result + introducirMaterias(xml) +"\n";
		result = result + introducirCursos(xml)+"\n";
		result = result + introducirTramosHorarios(xml)+"\n";
		result = result + introducirAulas(xml)+"\n";
		result = result + introducirGrupos(xml)+"\n";
		result = result + introducirProfesores(xml)+"\n";
		result = result + introducirSesiones(xml)+"\n";
		result = result + introducirHorasHorario(xml)+"\n";
		result = result + introducirGuardias(xml)+"\n";
		return new ResponseEntity<>(result,HttpStatus.OK);
	}

}
