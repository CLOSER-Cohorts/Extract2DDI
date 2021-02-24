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

	public VariableFragment(String id, String agency, int version, String label, String name, String xmlLang) {
		super(id, agency, version);
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
		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		Element variable = doc.createElementNS(namespace, NODE_NAME_VARIABLE);
		fragment.appendChild(variable);
		element.appendChild(fragment);

		super.appendToElement(variable, doc, namespace);

		// VariableName
		Element variableName = doc.createElementNS(namespace, NODE_NAME_VARIABLE_NAME);
		Element string = doc.createElementNS(namespace, NODE_NAME_STRING);
		string.setAttribute(ATTRIBUTE_NAME_XML_LANG, xmlLang);
		string.setTextContent(getName());
		variableName.appendChild(string);
		variable.appendChild(variableName);

		// Label
		Element label = doc.createElementNS(namespace, NODE_NAME_LABEL);
		label.setAttribute(ATTRIBUTE_NAME_XML_LANG, getXmlLang());
		Element content = doc.createElementNS(namespace, NODE_NAME_CONTENT);
		content.setTextContent(getLabel());
		label.appendChild(content);
		variable.appendChild(label);

		// VariableRepresentation

		AbstractVariableRepresentation variableRepresentation = getVariableRepresentation();
		if (variableRepresentation != null) {
			variableRepresentation.appendToElement(variable, doc, namespace);
		}
	}
}