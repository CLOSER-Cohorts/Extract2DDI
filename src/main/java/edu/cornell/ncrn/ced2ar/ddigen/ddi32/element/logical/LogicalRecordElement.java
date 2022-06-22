package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Name;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariablesInRecordElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogicalRecordElement extends ElementWithUrn {

	public static final String NODE_NAME_LOGICAL_RECORD = "ddi:LogicalRecord";
	public static final String NODE_NAME_LOGICAL_RECORD_NAME = "ddi:LogicalRecordName";

	private VariablesInRecordElement variablesInRecord;
	private Name logicalRecordName;

	public LogicalRecordElement(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element logicalRecord = doc.createElement(NODE_NAME_LOGICAL_RECORD);

		super.appendToElement(logicalRecord, doc);

		// Logical Product Name
		getLogicalRecordName().appendToElement(logicalRecord, doc);

		// Variables In Record
		getVariablesInRecord().appendToElement(logicalRecord, doc);

		element.appendChild(logicalRecord);
	}

	public Name getLogicalRecordName() {
		return logicalRecordName;
	}

	public VariablesInRecordElement getVariablesInRecord() {
		return variablesInRecord;
	}

	public void setLogicalProductName(String variableSchemeName) {
		this.logicalRecordName = new Name(NODE_NAME_LOGICAL_RECORD_NAME, variableSchemeName);
	}

	public void setVariablesInRecord(VariablesInRecordElement variablesInRecord) {
		this.variablesInRecord = variablesInRecord;
	}
}
