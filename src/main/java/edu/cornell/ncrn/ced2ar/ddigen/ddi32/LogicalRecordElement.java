package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariablesInRecordElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogicalRecordElement extends ElementWithUrn {

	public static final String NODE_NAME_LOGICAL_RECORD = "ddi:LogicalRecord";
	public static final String NODE_NAME_LOGICAL_PRODUCT_NAME = "ddi:LogicalProductName";

	private VariablesInRecordElement variablesInRecord;
	private Name logicalProductName;

	public LogicalRecordElement(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element logicalRecord = doc.createElement(NODE_NAME_LOGICAL_RECORD);

		super.appendToElement(element, doc);

		// Logical Product Name
		getLogicalProductName().appendToElement(element, doc);

		// Variables In Record
		getVariablesInRecord().appendToElement(logicalRecord, doc);

		element.appendChild(logicalRecord);
	}

	public Name getLogicalProductName() {
		return logicalProductName;
	}

	public VariablesInRecordElement getVariablesInRecord() {
		return variablesInRecord;
	}

	public void setLogicalProductName(String variableSchemeName) {
		this.logicalProductName = new Name(NODE_NAME_LOGICAL_PRODUCT_NAME, variableSchemeName);
	}

	public void setVariablesInRecord(VariablesInRecordElement variablesInRecord) {
		this.variablesInRecord = variablesInRecord;
	}
}
