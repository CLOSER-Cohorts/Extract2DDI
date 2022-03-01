package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RecordLayoutScheme extends ElementWithUrn {

	public static final String NODE_NAME_RECORD_LAYOUT_SCHEME = "p:RecordLayoutScheme";

	private RecordLayout recordLayout;

	public RecordLayoutScheme(String id, String agency, int version) {
		super(id, agency, version);
	}

	public RecordLayout getRecordLayout() {
		return recordLayout;
	}

	public void setRecordLayout(RecordLayout recordLayout) {
		this.recordLayout = recordLayout;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element recordLayoutScheme = doc.createElement(NODE_NAME_RECORD_LAYOUT_SCHEME);

		super.appendToElement(recordLayoutScheme, doc);

		getRecordLayout().appendToElement(recordLayoutScheme, doc);

		element.appendChild(recordLayoutScheme);
	}
}
