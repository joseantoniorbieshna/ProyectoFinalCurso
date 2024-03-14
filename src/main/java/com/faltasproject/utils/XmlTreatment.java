package com.faltasproject.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.faltasproject.dto.MateriaRequest;
import com.faltasproject.models.entities.Curso;
import com.faltasproject.models.entities.Materia;

public class XmlTreatment {
	Document doc;

	public XmlTreatment(MultipartFile xml) throws ParserConfigurationException, SAXException, IOException {
		// Configurar el analizador DOM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(xml.getInputStream());
	}

	public List<Curso> getAllCursos() {

		List<Curso> cursos = new ArrayList<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE LOS CURSOS
		String expr = "//grupo_datos[@seq='CURSOS_DEL_CENTRO']";

		try {
			
			XPathExpression expression = (XPathExpression) xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			List<String> result = new ArrayList<>();
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList nodelist = node.getChildNodes();

				for (int j = 0; j < nodelist.getLength(); j++) {
					Node child = nodelist.item(j);
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						// OBTENGO EL ID DE CURSO
						Long id = Long.valueOf(child.getChildNodes().item(1).getTextContent());
						// OBTENGO EL NOMBRE DE CURSO
						String nombre = child.getChildNodes().item(3).getTextContent();
						cursos.add(new Curso(id, nombre));
					}
				}
			}
			
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cursos;

	}
	
	
	public List<MateriaRequest> getAllClases() {

		List<MateriaRequest> materias = new ArrayList<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE LOS CURSOS
		String expr = "//grupo_datos[@seq='MATERIAS']";

		try {
			
			XPathExpression expression = (XPathExpression) xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList nodelist = node.getChildNodes();

				for (int j = 0; j < nodelist.getLength(); j++) {
					Node child = nodelist.item(j);
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						// OBTENGO EL ID DE CURSO
						Long idCurso = Long.valueOf(child.getChildNodes().item(1).getTextContent());
						// OBTENGO EL ID MATERIA
						Long idMateria = Long.valueOf(child.getChildNodes().item(3).getTextContent());
						// OBTENGO EL NOMBRE DE MATERIA
						String nombre = child.getChildNodes().item(5).getTextContent();
						//TODO hacer dto y tal
						
						;
						materias.add(new MateriaRequest(idMateria, nombre, idCurso));
					}
				}
			}
			
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return materias;

	}

}
