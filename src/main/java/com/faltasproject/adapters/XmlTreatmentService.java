package com.faltasproject.adapters;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.faltasproject.domain.dto.InputHoraHorarioDTO;
import com.faltasproject.domain.exceptions.BadRequestException;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.clases.dtos.CursoCreateDTO;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.IdGuardiaDTO;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.services.general.IFileTreatmentService;
import com.faltasproject.utils.XpathUtile;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("iFileTreatmentService")
@NoArgsConstructor
public class XmlTreatmentService implements IFileTreatmentService {
	private static final String CLAVE_EXPORTACION_NAME_ELEMENT="claveDeExportacion";
	private static final String NOMBRE_COMPLETO_NAME_ELEMENT="nombreCompleto";
	private static final String NOMBRE_NAME_ELEMENT="nombre";
	private static final String INDICE_NAME="indice";
	private Document doc;

	public XmlTreatmentService(MultipartFile xml) {
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
				String[] partesNombreCopleto = nombreCompleto.split("~");
				String nombreCompletoTratado = partesNombreCopleto.length>1?partesNombreCopleto[1].trim():nombreCompleto.trim();
				return new Materia(idNombre, abreviadoMateria,nombreCompletoTratado);
			}
		};
		return treatmentMateria.getSet();

	}
	
	public Set<CursoCreateDTO> getAllCursos() {
		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//cursos/curso";
		XpathUtile<CursoCreateDTO> treatmentCurso = new XpathUtile<CursoCreateDTO>(doc,expr) {

			@Override
			public CursoCreateDTO treatment(Element element) {
				String id = element.getElementsByTagName(CLAVE_EXPORTACION_NAME_ELEMENT).item(0).getTextContent();
				String nombreCompleto = element.getElementsByTagName(NOMBRE_COMPLETO_NAME_ELEMENT).item(0).getTextContent();
				NodeList materiasNode = ((Element)element.getElementsByTagName("materiasDelCurso").item(0)).getElementsByTagName("materia");
				
				Set<String> materiasReferencias = new HashSet<>();
				//MATERIA ID
				for (int j = 0; j < materiasNode.getLength(); j++) {
					Node materiasChild = materiasNode.item(j);
					if (materiasChild.getNodeType() == Node.ELEMENT_NODE) {
						// OBTENGO EL ID 
						String idMateria = materiasChild.getTextContent();
						materiasReferencias.add(idMateria);
					}
				}
				return new CursoCreateDTO(id,nombreCompleto,materiasReferencias);
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
				Integer dia = Integer.parseInt( element.getElementsByTagName("dia").item(0).getTextContent() );
				Integer indice = Integer.parseInt( element.getElementsByTagName(INDICE_NAME).item(0).getTextContent() );
				
				String[] horaEntradaTexto = element.getElementsByTagName("horaEntrada").item(0).getTextContent().split(":");
				String[] horaSalidaTexto = element.getElementsByTagName("horaSalida").item(0).getTextContent().split(":");
				
				LocalTime horaEntrada = LocalTime.of(Integer.parseInt(horaEntradaTexto[0]), Integer.parseInt(horaEntradaTexto[1]), Integer.parseInt(horaEntradaTexto[2]));
				LocalTime horaSalida=LocalTime.of(Integer.parseInt(horaSalidaTexto[0]), Integer.parseInt(horaSalidaTexto[1]), Integer.parseInt(horaSalidaTexto[2]));
				
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
	
	
	public Set<Sesion> getAllSesiones() {
		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//sesionesLectivas/sesion";
		XpathUtile<Sesion> treatmentCurso = new XpathUtile<Sesion>(doc,expr) {

			@Override
			public Sesion treatment(Element element) {
				Set<Grupo> grupos = new HashSet<>();
				String referencia = element.getAttribute("id");
				
				
				Element aulasElement = ((Element)element.getElementsByTagName("listaDeAulas").item(0));
				Element aulaElement = (Element)aulasElement.getElementsByTagName("aula").item(0);
				String aulaReferencia = aulaElement!=null? aulaElement.getTextContent():null;
				
				String materiaReferencia = element.getElementsByTagName("materia").item(0).getTextContent();
				String grupoReferenciaNombreBase = element.getElementsByTagName("grupo").item(0).getTextContent();
				String profesorReferencia = element.getElementsByTagName("profesor").item(0).getTextContent();
				
				grupos.add(new Grupo(grupoReferenciaNombreBase));
				
				//MIRAR SI HAY OTROS ELEMENTOS
				Element otroGrupoElement = ((Element)element.getElementsByTagName("otrosGrupos").item(0));
				if(otroGrupoElement!=null) {
					NodeList otroGrupoNodeList = otroGrupoElement.getElementsByTagName("grupo");
					//MATERIA ID
					for (int j = 0; j < otroGrupoNodeList.getLength(); j++) {
						Node grupoChild = otroGrupoNodeList.item(j);
						if (grupoChild.getNodeType() == Node.ELEMENT_NODE) {
							// OBTENGO EL ID 
							String otroGrupoNombreReferencia = grupoChild.getTextContent();
							grupos.add(new Grupo(otroGrupoNombreReferencia));
						}
					}
				}
				
				return new Sesion(referencia,new Materia(materiaReferencia),new Profesor(profesorReferencia),grupos, new Aula(aulaReferencia));
			}
			
		};

		return treatmentCurso.getSet();
	}
	
	public Set<InputHoraHorarioDTO> getAllHoraHorario() {
		Set<InputHoraHorarioDTO> result = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOS
		String expr = "//horario/tramo";

		try {
			
			XPathExpression expression = xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					
					Element element = (Element)child;
					int dia = Integer.parseInt(element.getAttribute("dia"));
					int indice = Integer.parseInt(element.getAttribute(INDICE_NAME));
					//Volvemos a recorrer
					   NodeList aulas = element.getElementsByTagName("aula");
					   if (child.getNodeType() == Node.ELEMENT_NODE) {
						   for (int j = 0; j < aulas.getLength(); j++) {
			                    Element aula = (Element) aulas.item(j);
			                    Element sesion = (Element) aula.getElementsByTagName("sesion").item(0);
			                    String sesionReferencia = sesion.getTextContent();
			                    result.add(new InputHoraHorarioDTO(sesionReferencia,dia,indice));
			                }
					   }
		                
				}
			}
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return result;
	}
	
	public Set<IdGuardiaDTO> getAllGuardias() {
	    Set<IdGuardiaDTO> result = new HashSet<>();

	    XPathFactory xPathFactory = XPathFactory.newInstance();
	    XPath xpath = xPathFactory.newXPath();

	    // OBTENEMOS LA LISTA DE GUARDIAS DE TIPO AULA
	    String expr = "//horario/tramo/guardia[nombre='Aula']";

	    try {
	        XPathExpression expression = xpath.compile(expr);

	        NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
	        // ITERO LA LISTA DE GUARDIAS DE TIPO AULA
	        for (int i = 0; i < nodeList.getLength(); i++) {
	            Node guardiaNode = nodeList.item(i);

	            if (guardiaNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element guardiaElement = (Element) guardiaNode;

	                int dia = Integer.parseInt(guardiaElement.getParentNode().getAttributes().getNamedItem("dia").getNodeValue());
	                int indice = Integer.parseInt(guardiaElement.getParentNode().getAttributes().getNamedItem(INDICE_NAME).getNodeValue());
	                String profesorReferencia = guardiaElement.getElementsByTagName("profesor").item(0).getTextContent();
	                
	                result.add(new IdGuardiaDTO(profesorReferencia, dia, indice));
	            }
	        }
	    } catch (XPathExpressionException e) {
	        log.warn(e.getMessage());
	    }
	    return result;
	}
	
	public void changeDocument() {
		
	}

}
