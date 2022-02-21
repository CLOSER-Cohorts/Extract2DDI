package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogicalProductElement extends ElementWithUrn {

	public static final String NODE_NAME_LOGICAL_PRODUCT = "ddi:LogicalProduct";
	public static final String NODE_NAME_LOGICAL_PRODUCT_NAME = "ddi:LogicalProductName";

	private LogicalRecordElement logicalRecord;

	public LogicalProductElement(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element logicalProduct = doc.createElement(NODE_NAME_LOGICAL_PRODUCT);

		super.appendToElement(element, doc);

		// Logical Record
		getLogicalRecord().appendToElement(logicalProduct, doc);

		element.appendChild(logicalProduct);
	}

	public LogicalRecordElement getLogicalRecord() {
		return logicalRecord;
	}

	public void setLogicalRecord(LogicalRecordElement logicalRecord) {
		this.logicalRecord = logicalRecord;
	}
}
