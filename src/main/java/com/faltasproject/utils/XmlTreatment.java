package com.faltasproject.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.clases.Materia;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlTreatment {
	
	Document doc;

	public XmlTreatment(MultipartFile xml) {
		// Configurar el analizador DOM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xml.getInputStream());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<Materia> getAllMaterias() {

		List<Materia> materias = new ArrayList<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE MATERIAS
		String expr = "//materias/materia";

		try {
			
			XPathExpression expression = (XPathExpression) xpath.compile(expr);

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return materias;

	}
	
	public List<Curso> getAllCursos() {

		List<Curso> cursos = new ArrayList<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = "//cursos/curso";

		try {
			
			XPathExpression expression = (XPathExpression) xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					Long id = Long.valueOf( element.getElementsByTagName("claveDeExportacion").item(0).getTextContent() );
					String nombreCompleto = element.getElementsByTagName("nombreCompleto").item(0).getTextContent();
					NodeList materiasNode = ((Element)element.getElementsByTagName("materiasDelCurso").item(0)).getElementsByTagName("materia");
					
					List<Materia> materias = new ArrayList<>();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cursos;

	}

}
