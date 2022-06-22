package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataRelationshipElement extends ElementWithUrn {

	public static final String NODE_NAME_DATA_RELATIONSHIP = "ddi:DataRelationship";

	private LogicalRecordElement logicalRecord;

	public DataRelationshipElement(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element dataRelationship = doc.createElement(NODE_NAME_DATA_RELATIONSHIP);

		super.appendToElement(dataRelationship, doc);

		getLogicalRecord().appendToElement(dataRelationship, doc);

		element.appendChild(dataRelationship);
	}

	public LogicalRecordElement getLogicalRecord() {
		return logicalRecord;
	}

	public void setLogicalRecord(LogicalRecordElement logicalRecord) {
		this.logicalRecord = logicalRecord;
	}
}
