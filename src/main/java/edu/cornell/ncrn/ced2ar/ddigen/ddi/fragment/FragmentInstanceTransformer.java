package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.VariableRepresentation;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FragmentInstanceTransformer {

	public String namespace = "";
	private LogicalProduct logicalProduct;

	public FragmentInstanceTransformer(LogicalProduct logicalProduct) {
		setLogicalProduct(logicalProduct);
	}

	public void setLogicalProduct(LogicalProduct logicalProduct) {
		this.logicalProduct = logicalProduct;
	}

	public Document toDocument() {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder;
		Document doc;

		try {
			domFactory.setNamespaceAware(true);
			domBuilder = domFactory.newDocumentBuilder();
			doc = domBuilder.newDocument();

			Element fragmentInstance = doc.createElementNS(namespace, "ddi:FragmentInstance");
			fragmentInstance.setAttribute("version", "1.0");

			doc.appendChild(fragmentInstance);
			doc.getDocumentElement().normalize();

			for (Variable variable : logicalProduct.getVariableScheme().getVariableList()) {
				Element fragmentElement = doc.createElementNS(namespace, "ddi:Fragment");
				Element variableElement = doc.createElementNS(namespace, "Variable");
				fragmentElement.appendChild(variableElement);
				fragmentInstance.appendChild(fragmentElement);

				// URN
				Element urnElement = doc.createElementNS(namespace, "r:URN");
				variableElement.appendChild(urnElement);

				// Agency
				Element agencyElement = doc.createElementNS(namespace, "r:Agency");
				variableElement.appendChild(agencyElement);

				// ID
				Element idElement = doc.createElementNS(namespace, "r:ID");
				variableElement.appendChild(idElement);

				// Version
				Element versionElement = doc.createElementNS(namespace, "r:Version");
				variableElement.appendChild(versionElement);

				// VariableName
				Element variableNameElement = doc.createElementNS(namespace, "VariableName");
				Element stringElement = doc.createElementNS(namespace, "r:String");
				stringElement.setTextContent(variable.getName());
				variableNameElement.appendChild(stringElement);
				variableElement.appendChild(variableNameElement);

				// Label
				Element labelElement = doc.createElementNS(namespace, "r:Label");
				Element contentElement = doc.createElementNS(namespace, "r:Content");
				contentElement.setTextContent(variable.getLabel());
				labelElement.appendChild(contentElement);
				variableElement.appendChild(labelElement);

				// VariableRepresentation
				VariableRepresentation variableRepresentation = variable.getRepresentation();

				Element representationElement = doc.createElementNS(namespace, "VariableRepresentation");
				Element variableRoleElement = doc.createElementNS(namespace, "VariableRole");
				variableRoleElement.setTextContent(variableRepresentation.getRole());
				variableRoleElement.setTextContent("input");
				representationElement.appendChild(variableRoleElement);

				Representation representation = variableRepresentation.getRepresentation();
				if (representation instanceof NumericRepresentation) {
					NumericRepresentation numericRepresentation = (NumericRepresentation) representation;

					Element numericTypeCodeElement = doc.createElementNS(namespace, "r:NumericTypeCode");
					numericTypeCodeElement.setTextContent(numericRepresentation.getType());

					Element numericRepresentationElement = doc.createElementNS(namespace, "r:NumericRepresentation");
					numericRepresentationElement.appendChild(numericTypeCodeElement);
					numericRepresentationElement.setAttribute("blankIsMissingValue", "false");
					numericRepresentationElement.setAttribute("decimalPositions", "4");

					representationElement.appendChild(numericRepresentationElement);
				} else if (representation instanceof TextRepresentation) {
					Element textRepresentationElement = doc.createElementNS(namespace, "r:TextRepresentation");
					textRepresentationElement.setAttribute("blankIsMissingValue", "false");
					textRepresentationElement.setAttribute("classificationLevel", "Nominal");
					representationElement.appendChild(textRepresentationElement);
				} else if (representation instanceof DateTimeRepresentation) {
					DateTimeRepresentation dateTimeRepresentation = (DateTimeRepresentation) representation;
					Element dateTypeCodeElement = doc.createElementNS(namespace, "r:DateTypeCode");
					dateTypeCodeElement.setTextContent(dateTimeRepresentation.getType());

					Element dateTimeRepresentationElement = doc.createElementNS(namespace, "r:DateTimeRepresentation");
					dateTimeRepresentationElement.appendChild(dateTypeCodeElement);
					dateTimeRepresentationElement.setAttribute("blankIsMissingValue", "false");
					representationElement.appendChild(dateTimeRepresentationElement);
				}

				variableElement.appendChild(representationElement);
			}
			return doc;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}