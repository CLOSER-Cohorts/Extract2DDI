package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LogicalProductFactory {

	public static List<CategoryScheme> createCategoryList(Document document) {
		List<CategoryScheme> categorySchemeList = new ArrayList<>();

		NodeList categorySchemeNodeList = document.getElementsByTagName("CategoryScheme");
		CategoryScheme categoryScheme = new CategoryScheme();

		for (int i = 0; i < categorySchemeNodeList.getLength(); i++) {
			List<Category> categoryList = new ArrayList<>();

			Node categorySchemeNode = categorySchemeNodeList.item(i);
			if (nodeNameEquals(categorySchemeNode, "CategoryScheme")) {
				NodeList variableNodeList = categorySchemeNode.getChildNodes();
				for (int j = 0; j < variableNodeList.getLength(); j++) {
					Node categoryNode = variableNodeList.item(j);
					if (nodeNameEquals(categoryNode, "Category")) {
						Category category = new Category();

						Node id = categoryNode.getAttributes().getNamedItem("id");
						if (id != null) {
							category.setId(id.getTextContent());
						}

						NodeList variableChildList = categoryNode.getChildNodes();
						for (int k = 0; k < variableChildList.getLength(); k++) {
							Node categoryChild = variableChildList.item(k);
							if (nodeNameEquals(categoryChild, "Label")) {
								category.setLabel(categoryChild.getTextContent());
							}
						}
						categoryList.add(category);
					}
				}
			}

			categoryScheme.setCategoryList(categoryList);
			categorySchemeList.add(categoryScheme);
		}

		return categorySchemeList;
	}

	public static List<CodeList> createCodeListList(Document document) {
		List<CodeList> codeListList = new ArrayList<>();

		NodeList codeSchemeList = document.getElementsByTagName("CodeScheme");

		for (int i = 0; i < codeSchemeList.getLength(); i++) {
			CodeList codeList = new CodeList();

			List<Code> codes = new ArrayList<>();

			Node categorySchemeNode = codeSchemeList.item(i);
			if (nodeNameEquals(categorySchemeNode, "CodeScheme")) {
				NodeList variableNodeList = categorySchemeNode.getChildNodes();
				for (int j = 0; j < variableNodeList.getLength(); j++) {
					Node codeNode = variableNodeList.item(j);
					if (nodeNameEquals(codeNode, "Code")) {
						Code code = new Code();
						NodeList codeChildList = codeNode.getChildNodes();

						for (int k = 0; k < codeChildList.getLength(); k++) {
							Node categoryChild = codeChildList.item(k);
							if (nodeNameEquals(categoryChild, "Value")) {
								code.setValue(categoryChild.getTextContent());
							} else if (nodeNameEquals(categoryChild, "CategoryReference")) {
								Node id = categoryChild.getFirstChild();
								if (nodeNameEquals(id, "ID")) {
									code.setCategoryId(id.getTextContent());
								}
							}
						}
						codes.add(code);
					}
				}
			}

			codeList.setCodeList(codes);
			codeListList.add(codeList);
		}

		return codeListList;
	}

	public static LogicalProduct createLogicalProduct(Document document) {
		LogicalProduct logicalProduct = new LogicalProduct();
		List<CategoryScheme> categoryList = createCategoryList(document);
		List<CodeList> codeListList = createCodeListList(document);
		List<VariableScheme> variableList = createVariableList(document);

		logicalProduct.setCategorySchemeList(categoryList);
		logicalProduct.setCodeListList(codeListList);
		logicalProduct.setVariableSchemeList(variableList);

		return logicalProduct;
	}

	private static List<VariableScheme> createVariableList(Document document) {
		List<VariableScheme> variableSchemeList = new ArrayList<>();

		NodeList variableSchemeNodeList = document.getElementsByTagName("VariableScheme");
		for (int i = 0; i < variableSchemeNodeList.getLength(); i++) {
			Node variableSchemeNode = variableSchemeNodeList.item(i);
			List<Variable> variableList = new ArrayList<>();
			VariableScheme variableScheme = new VariableScheme();

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

			variableScheme.setVariableList(variableList);
			variableSchemeList.add(variableScheme);
		}

		return variableSchemeList;
	}

	private static boolean nodeNameEquals(Node node, String string) {
		return node.getNodeName() != null && node.getNodeName().equalsIgnoreCase(string);
	}
}