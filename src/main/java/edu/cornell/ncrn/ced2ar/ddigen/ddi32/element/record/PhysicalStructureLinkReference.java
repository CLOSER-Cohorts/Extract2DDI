package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhysicalStructureLinkReference extends Reference {

	public static final String NODE_NAME_PHYSICAL_STRUCTURE_LINK_REFERENCE = "p:PhysicalStructureLinkReference";
	public static final String NODE_NAME_PHYSICAL_RECORD_SEGMENT_USED = "p:PhysicalRecordSegmentUsed";

	public PhysicalStructureLinkReference(String id, String agency, int version) {
		super(id, agency, version, NODE_NAME_PHYSICAL_STRUCTURE_LINK_REFERENCE, ReferenceObjectType.PhysicalRecordSegment);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		super.appendToElement(element, doc);

		Element physicalStructureLinkReference = doc.createElement(NODE_NAME_PHYSICAL_RECORD_SEGMENT_USED);
		element.appendChild(physicalStructureLinkReference);
	}

}
