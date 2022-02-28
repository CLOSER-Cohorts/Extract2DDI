package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.UUID;

public class BasedOnObject extends ElementWithUrn {

	public static final String NODE_NAME_BASED_ON_OBJECT = "r:BasedOnObject";

	private BasedOnReference basedOnReference;

	public BasedOnObject(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element basedOnObject = doc.createElement(NODE_NAME_BASED_ON_OBJECT);

		getBasedOnReference().appendToElement(basedOnObject, doc);

		element.appendChild(basedOnObject);
	}

	public BasedOnReference getBasedOnReference() {
		return basedOnReference;
	}

	public void setBasedOnReference(String id) {
		this.basedOnReference = new BasedOnReference(id, getAgency());
	}
}
