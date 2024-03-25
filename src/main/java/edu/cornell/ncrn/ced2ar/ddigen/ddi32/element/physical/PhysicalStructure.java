package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.UUID;

public class PhysicalStructure extends ElementWithUrn {

	public static final String NODE_NAME_PHYSICAL_STRUCTURE = "p:PhysicalStructure";
	public static final String NODE_NAME_FILE_FORMAT = "p:FileFormat";

	private BasedOnObject basedOnObject;
	private String fileFormat;
	private GrossRecordStructure grossRecordStructure;

	public PhysicalStructure(String agency) {
		super(agency);

		BasedOnObject basedOnObject = new BasedOnObject(agency);
		basedOnObject.setBasedOnReference(UUID.randomUUID().toString());
		setBasedOnObject(basedOnObject);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element physicalStructure = doc.createElement(NODE_NAME_PHYSICAL_STRUCTURE);
		super.appendToElement(physicalStructure, doc);

		getBasedOnObject().appendToElement(physicalStructure, doc);

		Element fileFormat = doc.createElement(NODE_NAME_FILE_FORMAT);
		fileFormat.setTextContent(getFileFormat());
		physicalStructure.appendChild(fileFormat);

		getGrossRecordStructure().appendToElement(physicalStructure, doc);

		element.appendChild(physicalStructure);
	}

	public BasedOnObject getBasedOnObject() {
		return basedOnObject;
	}

	public GrossRecordStructure getGrossRecordStructure() {
		return grossRecordStructure;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setBasedOnObject(BasedOnObject basedOnObject) {
		this.basedOnObject = basedOnObject;
	}

	public void setGrossRecordStructure(GrossRecordStructure grossRecordStructure) {
		this.grossRecordStructure = grossRecordStructure;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
}
