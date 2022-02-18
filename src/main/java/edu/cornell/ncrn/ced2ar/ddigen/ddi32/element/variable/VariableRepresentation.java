package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class VariableRepresentation implements Appendable {

	public static final String NODE_NAME_VARIABLE_REPRESENTATION = "ddi:VariableRepresentation";
	public static final String NODE_NAME_RECOMMENDED_DATA_TYPE = "r:RecommendedDataType";

	private String recommendedDataType;

	public VariableRepresentation(String recommendedDataType) {
		setRecommendedDataType(recommendedDataType);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element recommendedDataType = doc.createElement(NODE_NAME_RECOMMENDED_DATA_TYPE);
		recommendedDataType.setTextContent(getRecommendedDataType());
		element.appendChild(recommendedDataType);
	}

	public String getRecommendedDataType() {
		return recommendedDataType;
	}

	public void setRecommendedDataType(String recommendedDataType) {
		this.recommendedDataType = recommendedDataType;
	}
}
