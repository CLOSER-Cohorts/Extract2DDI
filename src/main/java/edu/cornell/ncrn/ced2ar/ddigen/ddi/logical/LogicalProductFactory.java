package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LogicalProductFactory {

	public static LogicalProduct createLogicalProduct(Document document) {
		LogicalProduct logicalProduct = new LogicalProduct();

		VariableScheme variableScheme = new VariableScheme();

		NodeList variableSchemeList = document.getElementsByTagName("VariableScheme");
		for (int i = 0; i < variableSchemeList.getLength(); i++) {
			Node variableSchemeNode = variableSchemeList.item(i);
			if (nodeNameEquals(variableSchemeNode, "VariableScheme")) {
				NodeList variableNodeList = variableSchemeNode.getChildNodes();
				for (int j = 0; j < variableNodeList.getLength(); j++) {
					Node variableNode = variableNodeList.item(j);
					if (nodeNameEquals(variableNode, "Variable")) {
						Variable variable = new Variable();
						NodeList variableChildList = variableNode.getChildNodes();

						for (int k = 0; k < variableChildList.getLength(); k++) {
							Node variableChild = variableChildList.item(k);
							if (nodeNameEquals(variableChild, "Name")) {
								variable.setName(variableChild.getTextContent());
							} else if (nodeNameEquals(variableChild, "Label")) {
								variable.setLabel(variableChild.getTextContent());
							} else if (nodeNameEquals(variableChild, "Representation")) {
								Node representationNode = variableChild.getFirstChild();
								VariableRepresentation representation = new VariableRepresentation();
								if (nodeNameEquals(representationNode, "NumericRepresentation")) {
									NumericRepresentation numericRepresentation = new NumericRepresentation();

									for (int l = 0; l < representationNode.getAttributes().getLength(); l++) {
										Node attribute = representationNode.getAttributes().item(l);
										if (nodeNameEquals(attribute, "type")) {
											numericRepresentation.setType(attribute.getTextContent());
										}
									}
									representation.setRepresentation(numericRepresentation);
								} else if (nodeNameEquals(representationNode, "TextRepresentation")) {
									representation.setRepresentation(new TextRepresentation());
								} else if (nodeNameEquals(representationNode, "DateTimeRepresentation")) {

									DateTimeRepresentation dateTimeRepresentation = new DateTimeRepresentation();

									for (int l = 0; l < representationNode.getAttributes().getLength(); l++) {
										Node attribute = representationNode.getAttributes().item(l);
										if (nodeNameEquals(attribute, "type")) {
											dateTimeRepresentation.setType(attribute.getTextContent());
										}
									}
									representation.setRepresentation(dateTimeRepresentation);
								}
								variable.setRepresentation(representation);
							}
						}
						variableScheme.addVariable(variable);
					}
				}
			}
		}
		logicalProduct.setVariableScheme(variableScheme);
		return logicalProduct;
	}

	private static boolean nodeNameEquals(Node node, String string) {
		return node.getNodeName() != null && node.getNodeName().equalsIgnoreCase(string);
	}
}