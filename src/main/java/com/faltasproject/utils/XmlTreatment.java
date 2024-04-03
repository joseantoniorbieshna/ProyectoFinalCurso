package com.faltasproject.utils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.faltasproject.domain.exceptions.BadRequestException;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.profesorado.Profesor;


public class XmlTreatment {
	private static final String CLAVE_EXPORTACION_NAME_ELEMENT="claveDeExportacion";
	private static final String NOMBRE_COMPLETO_NAME_ELEMENT="nombreCompleto";
	private static final String NOMBRE_NAME_ELEMENT="nombre";
	Document doc;

	public XmlTreatment(MultipartFile xml) {
		// Configurar el analizador DOM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xml.getInputStream());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new BadRequestException("No se ha podido parsear bien el XML.");
		}
	}
	
	public Set<Materia> getAllMaterias() {
		// OBTENEMOS LA LISTA DE MATERIAS
		String expr = "//materias/materia";
		XpathUtile<Materia> treatmentMateria = new XpathUtile<Materia>(doc,expr) {

			@Override
			public Materia treatment(Element element) {
				String idNombre = element.getElementsByTagName(CLAVE_EXPORTACION_NAME_ELEMENT).item(0).getTextContent();
				String abreviadoMateria = element.getElementsByTagName("abreviatura").item(0).getTextContent();
				String nombreCompleto = element.getElementsByTagName(NOMBRE_COMPLETO_NAME_ELEMENT).item(0).getTextContent();
				return new Materia(idNombre, abreviadoMateria,nombreCompleto);
			}
		};
		return treatmentMateria.getSet();

	}
	
	public Set<Curso> getAllCursos() {
		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//cursos/curso";
		XpathUtile<Curso> treatmentCurso = new XpathUtile<Curso>(doc,expr) {

			@Override
			public Curso treatment(Element element) {
				String id = element.getElementsByTagName(CLAVE_EXPORTACION_NAME_ELEMENT).item(0).getTextContent();
				String nombreCompleto = element.getElementsByTagName(NOMBRE_COMPLETO_NAME_ELEMENT).item(0).getTextContent();
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
				return new Curso(id,nombreCompleto,materias);
			}
			
		};

		return treatmentCurso.getSet();
	}
	
	public Set<TramoHorario> getAllTramosHorarios() {
		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//marcosDeHorario/marcoHorario/tramo";
		XpathUtile<TramoHorario> treatmentTramoHorario = new XpathUtile<TramoHorario>(doc,expr) {

			@Override
			public TramoHorario treatment(Element element) {
				Integer dia = Integer.valueOf( element.getElementsByTagName("dia").item(0).getTextContent() );
				Integer indice = Integer.valueOf( element.getElementsByTagName("indice").item(0).getTextContent() );
				
				String[] horaEntradaTexto = element.getElementsByTagName("horaEntrada").item(0).getTextContent().split(":");
				String[] horaSalidaTexto = element.getElementsByTagName("horaSalida").item(0).getTextContent().split(":");
				
				LocalTime horaEntrada = LocalTime.of(Integer.valueOf(horaEntradaTexto[0]), Integer.valueOf(horaEntradaTexto[1]), Integer.valueOf(horaEntradaTexto[2]));
				LocalTime horaSalida=LocalTime.of(Integer.valueOf(horaSalidaTexto[0]), Integer.valueOf(horaSalidaTexto[1]), Integer.valueOf(horaSalidaTexto[2]));
				
				return new TramoHorario(dia,indice,horaEntrada,horaSalida);
			}
			
		};

		return treatmentTramoHorario.getSet();
	}
	
	public Set<Aula> getAllAulas() {
		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//aulas/aula";
		
		XpathUtile<Aula> treatmentAula = new XpathUtile<Aula>(doc,expr) {

			@Override
			public Aula treatment(Element element) {
				String id = element.getElementsByTagName(CLAVE_EXPORTACION_NAME_ELEMENT).item(0).getTextContent();
				String nombre = element.getElementsByTagName("descripcion").item(0).getTextContent();
				
				return new Aula(id,nombre);
			}
			
		};
		return treatmentAula.getSet();
	}
	
	
	public Set<Grupo> getAllGrupos() {

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//grupos/grupo";
		XpathUtile<Grupo> treatmentGrupo = new XpathUtile<Grupo>(doc,expr) {

			@Override
			public Grupo treatment(Element element) {
				String nombre = element.getElementsByTagName(NOMBRE_NAME_ELEMENT).item(0).getTextContent();
				String claveCurso = element.getElementsByTagName(CLAVE_EXPORTACION_NAME_ELEMENT).item(0).getTextContent().split("-")[0];
				
				return new Grupo(nombre,new Curso(claveCurso));
			}
			
		};
		return treatmentGrupo.getSet();
	}
	
	
	public Set<Profesor> getAllProfesores() {

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//profesores/profesor";
		
		XpathUtile<Profesor> treatmentProfesores = new XpathUtile<Profesor>(doc,expr) {

			@Override
			public Profesor treatment(Element element) {
				Node claveExportacionNode = element.getElementsByTagName(CLAVE_EXPORTACION_NAME_ELEMENT).item(0);
				String claveExportacion= null;
				// A VECES NO FUNCIONA BIEN POR TANTO HAY QUE HACER ESTO
				if(claveExportacionNode==null) {
					claveExportacion=element.getElementsByTagName(NOMBRE_NAME_ELEMENT).item(0).getTextContent();
				}else {
					claveExportacion=claveExportacionNode.getTextContent();
				}
				String nombreCompleto = element.getElementsByTagName(NOMBRE_COMPLETO_NAME_ELEMENT).item(0).getTextContent();
				
				return new Profesor(claveExportacion,nombreCompleto);
			}
			
		};
		return treatmentProfesores.getSet();
	}

}
