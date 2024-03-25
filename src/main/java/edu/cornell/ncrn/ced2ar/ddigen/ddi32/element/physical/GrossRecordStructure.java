package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.UUID;

public class GrossRecordStructure extends ElementWithUrn {

	public static final String NODE_NAME_GROSS_RECORD_STRUCTURE = "p:GrossRecordStructure";

	private LogicalRecordReference logicalRecordReference;
	private PhysicalRecordSegment physicalRecordSegment;

	public GrossRecordStructure(String agency) {
		super(agency);
		setLogicalRecordReference(UUID.randomUUID().toString());
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element grossRecordStructure = doc.createElement(NODE_NAME_GROSS_RECORD_STRUCTURE);

		super.appendToElement(grossRecordStructure, doc);

		getLogicalRecordReference().appendToElement(grossRecordStructure, doc);
		getPhysicalRecordSegment().appendToElement(grossRecordStructure, doc);

		element.appendChild(grossRecordStructure);
	}

	public LogicalRecordReference getLogicalRecordReference() {
		return logicalRecordReference;
	}

	public PhysicalRecordSegment getPhysicalRecordSegment() {
		return physicalRecordSegment;
	}

	public void setLogicalRecordReference(String logicalRecordId) {
		this.logicalRecordReference = new LogicalRecordReference(logicalRecordId, getAgency());
	}

	public void setPhysicalRecordSegment(String physicalRecordSegmentId) {
		PhysicalRecordSegment physicalRecordSegment = new PhysicalRecordSegment(physicalRecordSegmentId, getAgency());
		this.physicalRecordSegment = physicalRecordSegment;
	}
}
