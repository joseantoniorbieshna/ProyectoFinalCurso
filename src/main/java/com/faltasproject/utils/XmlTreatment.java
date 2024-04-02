package com.faltasproject.utils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.profesorado.Profesor;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class XmlTreatment {
	
	Document doc;

	public XmlTreatment(MultipartFile xml) {
		// Configurar el analizador DOM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xml.getInputStream());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			log.warn(e.getMessage());
		}
	}
	
	public Set<Materia> getAllMaterias() {

		Set<Materia> materias = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE MATERIAS
		String expr = "//materias/materia";

		try {
			
			XPathExpression expression = xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					String idNombre = element.getElementsByTagName("claveDeExportacion").item(0).getTextContent();
					String abreviadoMateria = element.getElementsByTagName("abreviatura").item(0).getTextContent();
					String nombreCompleto = element.getElementsByTagName("nombreCompleto").item(0).getTextContent();
					materias.add(new Materia(idNombre, abreviadoMateria,nombreCompleto));
				}
			}
			
			
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return materias;

	}
	
	public Set<Curso> getAllCursos() {

		Set<Curso> cursos = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//cursos/curso";

		try {
			
			XPathExpression expression = xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					String id = element.getElementsByTagName("claveDeExportacion").item(0).getTextContent();
					String nombreCompleto = element.getElementsByTagName("nombreCompleto").item(0).getTextContent();
					NodeList materiasNode = ((Element)element.getElementsByTagName("materiasDelCurso").item(0)).getElementsByTagName("materia");
					
					Set<Materia> materias = new HashSet<>();
					//MATERIA ID
					for (int j = 0; j < materiasNode.getLength(); j++) {
						Node materiasChild = materiasNode.item(j);
						if (materiasChild.getNodeType() == Node.ELEMENT_NODE) {
							// OBTENGO EL ID 
							String idMateria = materiasChild.getTextContent();
							materias.add(new Materia(idMateria));
						}
					}
					
					cursos.add(new Curso(id,nombreCompleto,materias));
				}
			}
				
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return cursos;
	}
	
	public Set<TramoHorario> getAllTramosHorarios() {

		Set<TramoHorario> tramosHorarios = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//marcosDeHorario/marcoHorario/tramo";

		try {
			
			XPathExpression expression =	 xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					Integer dia = Integer.valueOf( element.getElementsByTagName("dia").item(0).getTextContent() );
					Integer indice = Integer.valueOf( element.getElementsByTagName("indice").item(0).getTextContent() );
					
					String[] horaEntradaTexto = element.getElementsByTagName("horaEntrada").item(0).getTextContent().split(":");
					String[] horaSalidaTexto = element.getElementsByTagName("horaSalida").item(0).getTextContent().split(":");
					
					LocalTime horaEntrada = LocalTime.of(Integer.valueOf(horaEntradaTexto[0]), Integer.valueOf(horaEntradaTexto[1]), Integer.valueOf(horaEntradaTexto[2]));
					LocalTime horaSalida=LocalTime.of(Integer.valueOf(horaSalidaTexto[0]), Integer.valueOf(horaSalidaTexto[1]), Integer.valueOf(horaSalidaTexto[2]));
					
					tramosHorarios.add(new TramoHorario(dia,indice,horaEntrada,horaSalida));
				}
			}
			
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return tramosHorarios;

	}
	
	public Set<Aula> getAllAulas() {

		Set<Aula> aulas = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//aulas/aula";

		try {
			
			XPathExpression expression = xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					String id = element.getElementsByTagName("claveDeExportacion").item(0).getTextContent();
					String nombre = element.getElementsByTagName("descripcion").item(0).getTextContent();
					
					aulas.add(new Aula(id,nombre));
				}
			}
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return aulas;
	}
	
	
	public Set<Grupo> getAllGrupos() {

		Set<Grupo> grupos = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//grupos/grupo";

		try {
			
			XPathExpression expression = xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
					String claveCurso = element.getElementsByTagName("claveDeExportacion").item(0).getTextContent().split("-")[0];
					
					grupos.add(new Grupo(nombre,new Curso(claveCurso)));
				}
			}
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return grupos;
	}
	
	
	public Set<Profesor> getAllProfesores() {

		Set<Profesor> profesores = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//profesores/profesor";

		try {
			
			XPathExpression expression = xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					Node claveExportacionNode = element.getElementsByTagName("claveDeExportacion").item(0);
					String claveExportacion= null;
					// A VECES NO FUNCIONA BIEN POR TANTO HAY QUE HACER ESTO
					if(claveExportacionNode==null) {
						claveExportacion=element.getElementsByTagName("nombre").item(0).getTextContent();
					}else {
						claveExportacion=claveExportacionNode.getTextContent();
					}
					String nombreCompleto = element.getElementsByTagName("nombreCompleto").item(0).getTextContent();
					
					profesores.add(new Profesor(claveExportacion,nombreCompleto));
				}
			}
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return profesores;
	}

}
