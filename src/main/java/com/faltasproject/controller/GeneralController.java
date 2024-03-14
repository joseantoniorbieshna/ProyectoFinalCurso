package com.faltasproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.faltasproject.dto.MateriaRequest;
import com.faltasproject.models.entities.Curso;
import com.faltasproject.models.entities.Materia;
import com.faltasproject.respositories.CursoRepository;
import com.faltasproject.respositories.MateriaRepository;
import com.faltasproject.utils.XmlTreatment;

@RestController
@RequestMapping("general")
public class GeneralController {

	CursoRepository cursoRepository;
	MateriaRepository materiaRepository;

	public GeneralController(CursoRepository cursoRepository, MateriaRepository materiaRepository) {
		this.cursoRepository = cursoRepository;
		this.materiaRepository = materiaRepository;
	}

	@GetMapping("hola")
	public String hola() {
		return "respuesta";
	}

	@PostMapping("curso")
	public String introducirCurso(@RequestParam("xml") MultipartFile xml) {

		int contador = 0;

		XmlTreatment xmlTreatment;
		try {
			xmlTreatment = new XmlTreatment(xml);

			List<Curso> cursos = xmlTreatment.getAllCursos();
			for (Curso curso : cursos) {
				Long id=curso.getId();
				curso=cursoRepository.save(curso);
				contador++;
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return "se han guardado " + contador + " cursos";
	}

	@PostMapping("materia")
	public String introducirMateria(@RequestParam("xml") MultipartFile xml) {

		int contador = 0;

		XmlTreatment xmlTreatment;
		try {
			xmlTreatment = new XmlTreatment(xml);

			List<MateriaRequest> materias = xmlTreatment.getAllClases();
			for (MateriaRequest materiaIterable : materias) {
				Optional<Materia> materiaResult=materiaRepository.findByNombre(materiaIterable.nombre());
				
				Materia materiaPersistence=null;
				if(materiaResult.isPresent()) {
					materiaPersistence = materiaResult.get();
				}else {
					materiaPersistence=new Materia(materiaIterable.nombre());
				}
				
				//GURADAMOS
				materiaPersistence.addCurso(
						cursoRepository.findById(materiaIterable.idCurso()).orElse(null)
				);
				
				materiaRepository.save(materiaPersistence);
				
				contador++;
				
		
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return "se han guardado " + contador + " materias";
	}
}
