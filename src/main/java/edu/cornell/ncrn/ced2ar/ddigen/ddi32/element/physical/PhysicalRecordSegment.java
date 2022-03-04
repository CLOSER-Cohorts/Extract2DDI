package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhysicalRecordSegment extends ElementWithUrn {

	public static final String NODE_NAME_PHYSICAL_RECORD_SEGMENT = "p:PhysicalRecordSegment";
	public static final String NODE_NAME_AGENCY = "r:Agency";
	public static final String NODE_NAME_ID = "r:ID";
	public static final String NODE_NAME_VERSION = "r:Version";

	public PhysicalRecordSegment(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element physicalRecordSegment = doc.createElement(NODE_NAME_PHYSICAL_RECORD_SEGMENT);

		// Agency
		Element agency = doc.createElement(NODE_NAME_AGENCY);
		agency.setTextContent(getAgency());
		physicalRecordSegment.appendChild(agency);

		// ID
		Element id = doc.createElement(NODE_NAME_ID);
		id.setTextContent(getId());
		physicalRecordSegment.appendChild(id);

		// Version
		Element version = doc.createElement(NODE_NAME_VERSION);
		version.setTextContent(Integer.toString(getVersion()));
		physicalRecordSegment.appendChild(version);

		super.appendToElement(physicalRecordSegment, doc);

		element.appendChild(physicalRecordSegment);
	}
}
