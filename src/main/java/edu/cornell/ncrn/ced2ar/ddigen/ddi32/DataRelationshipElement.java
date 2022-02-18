package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataRelationshipElement extends ElementWithUrn {

	public static final String NODE_NAME_DATA_RELATIONSHIP = "ddi:DataRelationship";

	private String fileName;

	public DataRelationshipElement(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element logicalProduct = doc.createElement(NODE_NAME_DATA_RELATIONSHIP);

		logicalProduct.appendChild(logicalProduct);

		element.appendChild(logicalProduct);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
