package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Name;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataRelationshipElement extends ElementWithUrn {

	public static final String NODE_NAME_DATA_RELATIONSHIP = "ddi:DataRelationship";
	public static final String NODE_NAME_DATA_RELATIONSHIP_NAME = "ddi:DataRelationshipName";

	private final Name dataRelationshipName;
	private LogicalRecordElement logicalRecord;

	public DataRelationshipElement(String title, String agency) {
		super(agency);
		dataRelationshipName = new Name(NODE_NAME_DATA_RELATIONSHIP_NAME, title + "  Data Relationship Name");
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element dataRelationship = doc.createElement(NODE_NAME_DATA_RELATIONSHIP);

		super.appendToElement(dataRelationship, doc);

		if (this.dataRelationshipName != null) {
			this.dataRelationshipName.appendToElement(dataRelationship, doc);
		}

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
