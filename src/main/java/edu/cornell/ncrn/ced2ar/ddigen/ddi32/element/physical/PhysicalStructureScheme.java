package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhysicalStructureScheme extends ElementWithUrn {

	public static final String NODE_NAME_PHYSICAL_STRUCTURE_SCHEME = "p:PhysicalStructureScheme";

	private PhysicalStructure physicalStructure;

	public PhysicalStructureScheme(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element physicalStructureScheme = doc.createElement(NODE_NAME_PHYSICAL_STRUCTURE_SCHEME);

		getPhysicalStructure().appendToElement(physicalStructureScheme, doc);

		element.appendChild(physicalStructureScheme);
	}

	public PhysicalStructure getPhysicalStructure() {
		return physicalStructure;
	}

	public void setPhysicalStructure(PhysicalStructure physicalStructure) {
		this.physicalStructure = physicalStructure;
	}
}
