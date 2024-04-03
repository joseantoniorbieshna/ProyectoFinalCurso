package com.faltasproject.utils;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public abstract class XpathUtile<R> {
	private Document doc;
	private String xPathExpresion;
	
	protected XpathUtile(Document doc, String xPathExpresion) {
		this.doc=doc;
		this.xPathExpresion=xPathExpresion;
	}
	
	public Set<R> getSet(){
		Set<R> result = new HashSet<>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// OBTENEMOS LA LISTA DE CURSOSS
		String expr = xPathExpresion;

		try {
			
			XPathExpression expression = xpath.compile(expr);

			NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
			// ITERO LA LISTA DEL NODO CURSO
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node child = nodeList.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;
					result.add(this.treatment(element));
				}
			}
		} catch (XPathExpressionException e) {
			log.warn(e.getMessage());
		}
		return result;
	}

	public abstract R treatment(Element element);

}
