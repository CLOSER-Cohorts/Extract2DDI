package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.FileUtil;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.VariableRepresentation;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Properties;

public class FragmentInstanceTransformer {

	public static final String KEY_AGENCY = "edu.cornell.ncrn.ced2ar.ddigen.fragment.agency";
	public static final String KEY_XML_LANG = "edu.cornell.ncrn.ced2ar.ddigen.fragment.xml.lang";

	public static final String ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE = "blankIsMissingValue";
	public static final String ATTRIBUTE_NAME_CLASSIFICATION_LEVEL = "classificationLevel";
	public static final String ATTRIBUTE_NAME_DECIMAL_POSITIONS = "decimalPositions";
	public static final String ATTRIBUTE_NAME_XML_LANG = "xml:lang";
	public static final String ATTRIBUTE_VALUE_FALSE = "false";
	public static final String ATTRIBUTE_VALUE_NOMINAL = "Nominal";

	public static final String NODE_NAME_FRAGMENT_INSTANCE = "ddi:FragmentInstance";
	public static final String NODE_NAME_FRAGMENT = "ddi:Fragment";
	public static final String NODE_NAME_VARIABLE = "ddi:Variable";
	public static final String NODE_NAME_URN = "r:URN";
	public static final String NODE_NAME_AGENCY = "r:Agency";
	public static final String NODE_NAME_ID = "r:ID";
	public static final String NODE_NAME_VERSION = "r:Version";
	public static final String NODE_NAME_VARIABLE_NAME = "VariableName";
	public static final String NODE_NAME_STRING = "r:String";
	public static final String NODE_NAME_LABEL = "r:Label";
	public static final String NODE_NAME_CONTENT = "r:Content";
	public static final String NODE_NAME_VARIABLE_REPRESENTATION = "VariableRepresentation";
	public static final String NODE_NAME_VARIABLE_ROLE = "VariableRole";
	public static final String NODE_NAME_NUMERIC_TYPE_CODE = "r:NumericTypeCode";
	public static final String NODE_NAME_NUMERIC_REPRESENTATION = "r:NumericRepresentation";
	public static final String NODE_NAME_TEXT_REPRESENTATION = "r:TextRepresentation";
	public static final String NODE_NAME_DATE_TYPE_CODE = "r:DateTypeCode";
	public static final String NODE_NAME_DATE_TIME_REPRESENTATION = "r:DateTimeRepresentation";

	public String namespace = "";
	private LogicalProduct logicalProduct;

	public FragmentInstanceTransformer(LogicalProduct logicalProduct) {
		setLogicalProduct(logicalProduct);
	}

	public void setLogicalProduct(LogicalProduct logicalProduct) {
		this.logicalProduct = logicalProduct;
	}

	public Document toDocument() throws ParserConfigurationException {
		Properties properties = FileUtil.getPropertiesFromResource(getClass());
		String agency = properties.getProperty(KEY_AGENCY);
		String xmlLang = properties.getProperty(KEY_XML_LANG);

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder;
		Document doc;

		domFactory.setNamespaceAware(true);
		domBuilder = domFactory.newDocumentBuilder();
		doc = domBuilder.newDocument();

		Element fragmentInstance = doc.createElementNS(namespace, NODE_NAME_FRAGMENT_INSTANCE);
		fragmentInstance.setAttribute("version", "1.0");

		doc.appendChild(fragmentInstance);
		doc.getDocumentElement().normalize();

		for (Variable variable : logicalProduct.getVariableScheme().getVariableList()) {
			Element fragmentElement = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
			Element variableElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE);
			fragmentElement.appendChild(variableElement);
			fragmentInstance.appendChild(fragmentElement);

			// URN
			Element urnElement = doc.createElementNS(namespace, NODE_NAME_URN);
			variableElement.appendChild(urnElement);

			// Agency
			Element agencyElement = doc.createElementNS(namespace, NODE_NAME_AGENCY);
			agencyElement.setTextContent(agency);
			variableElement.appendChild(agencyElement);

			// ID
			Element idElement = doc.createElementNS(namespace, NODE_NAME_ID);
			variableElement.appendChild(idElement);

			// Version
			Element versionElement = doc.createElementNS(namespace, NODE_NAME_VERSION);
			variableElement.appendChild(versionElement);

			// VariableName
			Element variableNameElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_NAME);
			Element stringElement = doc.createElementNS(namespace, NODE_NAME_STRING);
			stringElement.setAttribute(ATTRIBUTE_NAME_XML_LANG, xmlLang);
			stringElement.setTextContent(variable.getName());
			variableNameElement.appendChild(stringElement);
			variableElement.appendChild(variableNameElement);

			// Label
			Element labelElement = doc.createElementNS(namespace, NODE_NAME_LABEL);
			labelElement.setAttribute(ATTRIBUTE_NAME_XML_LANG, xmlLang);
			Element contentElement = doc.createElementNS(namespace, NODE_NAME_CONTENT);
			contentElement.setTextContent(variable.getLabel());
			labelElement.appendChild(contentElement);
			variableElement.appendChild(labelElement);

			// VariableRepresentation
			VariableRepresentation variableRepresentation = variable.getRepresentation();

			Element representationElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REPRESENTATION);
			Element variableRoleElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_ROLE);
			variableRoleElement.setTextContent(variableRepresentation.getRole());
			variableRoleElement.setTextContent("input");
			representationElement.appendChild(variableRoleElement);

			Representation representation = variableRepresentation.getRepresentation();
			if (representation instanceof NumericRepresentation) {
				NumericRepresentation numericRepresentation = (NumericRepresentation) representation;

				Element numericTypeCodeElement = doc.createElementNS(namespace, NODE_NAME_NUMERIC_TYPE_CODE);
				numericTypeCodeElement.setTextContent(numericRepresentation.getType());

				Element numericRepresentationElement = doc.createElementNS(namespace, NODE_NAME_NUMERIC_REPRESENTATION);
				numericRepresentationElement.appendChild(numericTypeCodeElement);
				numericRepresentationElement.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
				numericRepresentationElement.setAttribute(ATTRIBUTE_NAME_DECIMAL_POSITIONS, "4");

				representationElement.appendChild(numericRepresentationElement);
			} else if (representation instanceof TextRepresentation) {
				Element textRepresentationElement = doc.createElementNS(namespace, NODE_NAME_TEXT_REPRESENTATION);
				textRepresentationElement.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
				textRepresentationElement.setAttribute(ATTRIBUTE_NAME_CLASSIFICATION_LEVEL, ATTRIBUTE_VALUE_NOMINAL);
				representationElement.appendChild(textRepresentationElement);
			} else if (representation instanceof DateTimeRepresentation) {
				DateTimeRepresentation dateTimeRepresentation = (DateTimeRepresentation) representation;
				Element dateTypeCodeElement = doc.createElementNS(namespace, NODE_NAME_DATE_TYPE_CODE);
				dateTypeCodeElement.setTextContent(dateTimeRepresentation.getType());

				Element dateTimeRepresentationElement = doc.createElementNS(namespace, NODE_NAME_DATE_TIME_REPRESENTATION);
				dateTimeRepresentationElement.appendChild(dateTypeCodeElement);
				dateTimeRepresentationElement.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
				representationElement.appendChild(dateTimeRepresentationElement);
			}
			variableElement.appendChild(representationElement);
		}
		return doc;
	}
}