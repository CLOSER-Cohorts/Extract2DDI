package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Name;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VariableSchemeElement extends ElementWithUrn {

	public static final String NODE_NAME_VARIABLE_SCHEME = "ddi:VariableScheme";
	public static final String NODE_NAME_VARIABLE_SCHEME_NAME = "ddi:VariableSchemeName";

	private Name variableSchemeName;

	private List<VariableElement> variableElementList = new ArrayList<>();

	public VariableSchemeElement(
			String id,
			String agency,
			String ddilanguage,
			String title,
			List<Variable> variableList,
			Map<String, UUID> codeListIdToUuidMap
	) {
		super(id, agency);
		setVariableSchemeName(title);

		for (Variable variable : variableList) {
			VariableElement variableElement = new VariableElement(variable.getUuid(), getAgency());
			variableElement.setName(variable.getName());

			String labelContent = variable.getLabel();

			variableElement.setLabel(labelContent, ddilanguage);
			variableElement.setVariableRepresentation(variable.getRepresentation(), codeListIdToUuidMap);

			addVariableElement(variableElement);
		}
	}

	public void addVariableElement(VariableElement variableElement) {
		synchronized (variableElementList) {
			variableElementList.add(variableElement);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableScheme = doc.createElement(NODE_NAME_VARIABLE_SCHEME);

		super.appendToElement(variableScheme, doc);

		getVariableSchemeName().appendToElement(variableScheme, doc);

		for (VariableElement variableElement : getVariableElementList()) {
			variableElement.appendToElement(variableScheme, doc);
		}

		element.appendChild(variableScheme);
	}

	public List<VariableElement> getVariableElementList() {
		return variableElementList;
	}

	public Name getVariableSchemeName() {
		return variableSchemeName;
	}

	public void setVariableSchemeName(String variableSchemeName) {
		this.variableSchemeName = new Name(NODE_NAME_VARIABLE_SCHEME_NAME, variableSchemeName);
	}
}
