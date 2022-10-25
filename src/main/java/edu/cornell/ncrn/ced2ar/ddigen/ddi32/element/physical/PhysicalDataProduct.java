package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.RecordLayoutScheme;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhysicalDataProduct extends ElementWithUrn {

	public static final String NODE_NAME_PHYSICAL_DATA_PRODUCT = "p:PhysicalDataProduct";

	private PhysicalStructureScheme physicalStructureScheme;
	private RecordLayoutScheme recordLayoutScheme;

	public PhysicalDataProduct(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element physicalDataProduct = doc.createElement(NODE_NAME_PHYSICAL_DATA_PRODUCT);
		super.appendToElement(physicalDataProduct, doc);

		getPhysicalStructureScheme().appendToElement(physicalDataProduct, doc);

		getRecordLayoutScheme().appendToElement(physicalDataProduct, doc);

		element.appendChild(physicalDataProduct);
	}

	public PhysicalStructureScheme getPhysicalStructureScheme() {
		return physicalStructureScheme;
	}

	public RecordLayoutScheme getRecordLayoutScheme() {
		return recordLayoutScheme;
	}

	public void setPhysicalStructureScheme(PhysicalStructureScheme physicalStructureScheme) {
		this.physicalStructureScheme = physicalStructureScheme;
	}

	public void setRecordLayoutScheme(RecordLayoutScheme recordLayoutScheme) {
		this.recordLayoutScheme = recordLayoutScheme;
	}
}
