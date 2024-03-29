package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Label;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Name;
import edu.cornell.ncrn.ced2ar.ddigen.representation.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.TextRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.UUID;

public class VariableElement extends ElementWithUrn {

	public static final String NODE_NAME_VARIABLE = "ddi:Variable";
	public static final String NODE_NAME_VARIABLE_NAME = "ddi:VariableName";

	private Name name;
	private Label label;
	private VariableRepresentation variableRepresentation;

	public VariableElement(String id, String agency) {
		super(id, agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableElement = doc.createElement(NODE_NAME_VARIABLE);

		super.appendToElement(variableElement, doc);

		getName().appendToElement(variableElement, doc);

		getLabel().appendToElement(variableElement, doc);

		if (getVariableRepresentation() != null) {
			getVariableRepresentation().appendToElement(variableElement, doc);
		}

		element.appendChild(variableElement);
	}

	public Label getLabel() {
		return label;
	}

	public Name getName() {
		return name;
	}

	public VariableRepresentation getVariableRepresentation() {
		return variableRepresentation;
	}

	public void setLabel(String label, String ddiLanguage) {
		this.label = new Label(label, ddiLanguage);
	}

	public void setName(String name) {
		this.name = new Name(NODE_NAME_VARIABLE_NAME, name);
	}

//	public void setVariableRepresentation(VariableRepresentation variableRepresentation) {
//		this.variableRepresentation = variableRepresentation;
//	}

	public void setVariableRepresentation(Representation variableRepresentation, Map<String, UUID> codeListIdToUuidMap) {
		if (variableRepresentation instanceof NumericRepresentation) {
			NumericRepresentation representation = (NumericRepresentation) variableRepresentation;

			this.variableRepresentation = new NumericVariableRepresentation(representation.getType());
		} else if (variableRepresentation instanceof TextRepresentation) {
			this.variableRepresentation = new TextVariableRepresentation("text");
		} else if (variableRepresentation instanceof DateTimeRepresentation) {
			DateTimeRepresentation representation = (DateTimeRepresentation) variableRepresentation;

			this.variableRepresentation = new DateTimeVariableRepresentation(representation.getType());
		} else if (variableRepresentation instanceof CodeRepresentation) {
			CodeRepresentation representation = (CodeRepresentation) variableRepresentation;

			UUID codeSchemeId = codeListIdToUuidMap.get(representation.getCodeSchemeId());

			CodeVariableRepresentation codeVariableRepresentation = new CodeVariableRepresentation("Numeric");
			codeVariableRepresentation.setReferenceElement(codeSchemeId.toString(), getAgency());
			this.variableRepresentation = codeVariableRepresentation;
		}
	}
}
