package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ReferenceObjectType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference.NODE_NAME_TYPE_OF_OBJECT;

public class PhysicalStructureLinkReference extends ElementWithUrn {

	public static final String NODE_NAME_PHYSICAL_STRUCTURE_LINK_REFERENCE = "p:PhysicalStructureLinkReference";
	public static final String NODE_NAME_PHYSICAL_RECORD_SEGMENT_USED = "p:PhysicalRecordSegmentUsed";

	private String physicalRecordSegmentId;

	public PhysicalStructureLinkReference(String physicalRecordSegmentId, String agency) {
		super(agency);
		this.physicalRecordSegmentId = physicalRecordSegmentId;
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element reference = doc.createElement(NODE_NAME_PHYSICAL_STRUCTURE_LINK_REFERENCE);

		super.appendToElement(reference, doc);

		Element objectType = doc.createElement(NODE_NAME_TYPE_OF_OBJECT);
		objectType.setTextContent(ReferenceObjectType.PhysicalRecordSegment.toString());
		reference.appendChild(objectType);

		Element physicalRecordSegmentUsed = doc.createElement(NODE_NAME_PHYSICAL_RECORD_SEGMENT_USED);
		physicalRecordSegmentUsed.setTextContent(physicalRecordSegmentId);
		reference.appendChild(physicalRecordSegmentUsed);

		parent.appendChild(reference);
	}

}
