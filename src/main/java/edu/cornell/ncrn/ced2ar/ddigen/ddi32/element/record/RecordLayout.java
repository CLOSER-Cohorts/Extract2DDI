package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class RecordLayout extends ElementWithUrn {

	public static final String NODE_NAME_RECORD_LAYOUT = "ddi1:RecordLayout";

	private PhysicalStructureLinkReference reference;
	private DefaultVariableSchemeReference defaultVariableSchemeReference;
	private List<DataItem> dataItemList = new ArrayList<>();

	public RecordLayout(String agency, String variableSchemeId) {
		super(agency);
		setDefaultVariableSchemeReference(new DefaultVariableSchemeReference(variableSchemeId, agency));
	}

	public void addDataItem(DataItem dataItem) {
		synchronized (dataItemList) {
			dataItemList.add(dataItem);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element recordLayout = doc.createElement(NODE_NAME_RECORD_LAYOUT);

		super.appendToElement(recordLayout, doc);

		getReference().appendToElement(recordLayout, doc);

		getDefaultVariableSchemeReference().appendToElement(recordLayout, doc);

		for (DataItem dataItem : getDataItemList()) {
			dataItem.appendToElement(recordLayout, doc);
		}

		element.appendChild(recordLayout);
	}

	public List<DataItem> getDataItemList() {
		return dataItemList;
	}

	public Reference getDefaultVariableSchemeReference() {
		return defaultVariableSchemeReference;
	}

	public PhysicalStructureLinkReference getReference() {
		return reference;
	}

	public void setDefaultVariableSchemeReference(DefaultVariableSchemeReference defaultVariableSchemeReference) {
		this.defaultVariableSchemeReference = defaultVariableSchemeReference;
	}

	public void setReference(PhysicalStructureLinkReference reference) {
		this.reference = reference;
	}
}
