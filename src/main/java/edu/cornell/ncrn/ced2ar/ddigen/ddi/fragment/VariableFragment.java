package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableFragment extends Fragment {

	public static final String ATTRIBUTE_NAME_XML_LANG = "xml:lang";

	public static final String NODE_NAME_VARIABLE = "ddi:Variable";

	public static final String NODE_NAME_VARIABLE_NAME = "VariableName";
	public static final String NODE_NAME_STRING = "r:String";
	public static final String NODE_NAME_LABEL = "r:Label";
	public static final String NODE_NAME_CONTENT = "r:Content";

	private String label;
	private String name;
	private AbstractVariableRepresentation variableRepresentation;
	private String xmlLang;

	public VariableFragment(String agency, String id, int version, String label, String name, String xmlLang) {
		super(agency, id, version);
		setLabel(label);
		setName(name);
		setXmlLang(xmlLang);
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public AbstractVariableRepresentation getVariableRepresentation() {
		return variableRepresentation;
	}

	public String getXmlLang() {
		return xmlLang;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setRepresentation(AbstractVariableRepresentation variableRepresentation) {
		this.variableRepresentation = variableRepresentation;
	}

	public void setXmlLang(String xmlLang) {
		this.xmlLang = xmlLang;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragmentElement = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		Element variableElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE);
		fragmentElement.appendChild(variableElement);
		element.appendChild(fragmentElement);

		super.appendToElement(variableElement, doc, namespace);

		// VariableName
		Element variableNameElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_NAME);
		Element stringElement = doc.createElementNS(namespace, NODE_NAME_STRING);
		stringElement.setAttribute(ATTRIBUTE_NAME_XML_LANG, xmlLang);
		stringElement.setTextContent(getName());
		variableNameElement.appendChild(stringElement);
		variableElement.appendChild(variableNameElement);

		// Label
		Element labelElement = doc.createElementNS(namespace, NODE_NAME_LABEL);
		labelElement.setAttribute(ATTRIBUTE_NAME_XML_LANG, getXmlLang());
		Element contentElement = doc.createElementNS(namespace, NODE_NAME_CONTENT);
		contentElement.setTextContent(getLabel());
		labelElement.appendChild(contentElement);
		variableElement.appendChild(labelElement);

		// VariableRepresentation

		AbstractVariableRepresentation variableRepresentation = getVariableRepresentation();
		if (variableRepresentation != null) {
			variableRepresentation.appendToElement(variableElement, doc, namespace);
		}
	}
}