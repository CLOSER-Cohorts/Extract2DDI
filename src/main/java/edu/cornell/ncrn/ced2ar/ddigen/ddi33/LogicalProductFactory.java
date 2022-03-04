package edu.cornell.ncrn.ced2ar.ddigen.ddi33;

import java.util.ArrayList;
import java.util.List;

import edu.cornell.ncrn.ced2ar.ddigen.category.Category;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.Code;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.representation.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LogicalProductFactory {

	public static List<CategoryScheme> createCategorySchemeList(Document document) {
		List<CategoryScheme> categorySchemeList = new ArrayList<>();

		if (document != null) {
			NodeList categorySchemeNodeList = document.getElementsByTagName("CategoryScheme");

			for (int i = 0; i < categorySchemeNodeList.getLength(); i++) {
				CategoryScheme categoryScheme = new CategoryScheme();
				List<Category> categoryList = new ArrayList<>();

				Node categorySchemeNode = categorySchemeNodeList.item(i);
				if (nodeNameEquals(categorySchemeNode, "CategoryScheme")) {
					Node categorySchemeIdNode = categorySchemeNode.getAttributes().getNamedItem("id");
					categoryScheme.setId(categorySchemeIdNode.getTextContent());

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
		}

		return categorySchemeList;
	}

	public static List<CodeList> createCodeListList(Document document) {
		List<CodeList> codeListList = new ArrayList<>();

		if (document != null) {
			NodeList codeSchemeList = document.getElementsByTagName("CodeScheme");

			for (int i = 0; i < codeSchemeList.getLength(); i++) {
				CodeList codeList = new CodeList();

				List<Code> codes = new ArrayList<>();

				Node categorySchemeNode = codeSchemeList.item(i);
				if (nodeNameEquals(categorySchemeNode, "CodeScheme")) {
					String codeSchemeId = categorySchemeNode.getAttributes().getNamedItem("id").getTextContent();
					codeList.setId(codeSchemeId);

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
		}


		return codeListList;
	}

	public static List<VariableScheme> createVariableSchemeList(Document document) {
		List<VariableScheme> variableSchemeList = new ArrayList<>();

		if (document != null) {
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

							Node variableId = variableNode.getAttributes().getNamedItem("id");
							variable.setId(variableId.getTextContent());

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
										String codeSchemeId = representationNode.getFirstChild().getTextContent();
										representation = new CodeRepresentation(codeSchemeId);
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
		}

		return variableSchemeList;
	}

	private static boolean nodeNameEquals(Node node, String string) {
		return node.getNodeName() != null && node.getNodeName().equalsIgnoreCase(string);
	}
}