package com.faltasproject.adapters.rest.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.persistance_ports.clases.MateriaPersistance;
import com.faltasproject.domain.services.clases.MateriaService;
import com.faltasproject.utils.XmlTreatment;

@RestController
@RequestMapping("general")
public class GeneralController {
	
	private final MateriaService materiaService;
	
	public GeneralController(MateriaService materiaService) {
		this.materiaService=materiaService;
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
	
	

}
