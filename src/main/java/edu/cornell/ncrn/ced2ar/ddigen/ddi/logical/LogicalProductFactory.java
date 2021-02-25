package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LogicalProductFactory {

	public static List<Category> createCategoryList(Document document) {
		List<Category> categoryList = new ArrayList<>();

		NodeList variableSchemeList = document.getElementsByTagName("CategoryScheme");
		for (int i = 0; i < variableSchemeList.getLength(); i++) {
			Node variableSchemeNode = variableSchemeList.item(i);
			if (nodeNameEquals(variableSchemeNode, "CategoryScheme")) {
				NodeList variableNodeList = variableSchemeNode.getChildNodes();
				for (int j = 0; j < variableNodeList.getLength(); j++) {
					Node variableNode = variableNodeList.item(j);
					if (nodeNameEquals(variableNode, "Category")) {
						Category variable = new Category();
						NodeList variableChildList = variableNode.getChildNodes();

						for (int k = 0; k < variableChildList.getLength(); k++) {
							Node variableChild = variableChildList.item(k);
							if (nodeNameEquals(variableChild, "Label")) {
								variable.setLabel(variableChild.getTextContent());
							}
						}
						categoryList.add(variable);
					}
				}
			}
		}

		return categoryList;
	}

	public static LogicalProduct createLogicalProduct(Document document) {
		LogicalProduct logicalProduct = new LogicalProduct();
		List<Variable> variableList = createVariableList(document);
		List<Category> categoryList = createCategoryList(document);

		logicalProduct.setCategoryList(categoryList);
		logicalProduct.setVariableList(variableList);

		return logicalProduct;
	}

	private static List<Variable> createVariableList(Document document) {
		List<Variable> variableList = new ArrayList<>();

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
								Representation representation = null;
								if (nodeNameEquals(representationNode, "NumericRepresentation")) {
									NumericRepresentation numericRepresentation = new NumericRepresentation();

									for (int l = 0; l < representationNode.getAttributes().getLength(); l++) {
										Node attribute = representationNode.getAttributes().item(l);
										if (nodeNameEquals(attribute, "type")) {
											numericRepresentation.setType(attribute.getTextContent());
										}
									}
									representation = numericRepresentation;
								} else if (nodeNameEquals(representationNode, "TextRepresentation")) {
									representation = new TextRepresentation();
								} else if (nodeNameEquals(representationNode, "DateTimeRepresentation")) {

									DateTimeRepresentation dateTimeRepresentation = new DateTimeRepresentation();

									for (int l = 0; l < representationNode.getAttributes().getLength(); l++) {
										Node attribute = representationNode.getAttributes().item(l);
										if (nodeNameEquals(attribute, "type")) {
											dateTimeRepresentation.setType(attribute.getTextContent());
										}
									}
									representation = dateTimeRepresentation;
								} else if (nodeNameEquals(representationNode, "CodeRepresentation")) {
									representation = new CodeRepresentation();
								}
								variable.setRepresentation(representation);
							}
						}
						variableList.add(variable);
					}
				}
			}
		}

		return variableList;
	}



	private static boolean nodeNameEquals(Node node, String string) {
		return node.getNodeName() != null && node.getNodeName().equalsIgnoreCase(string);
	}
}