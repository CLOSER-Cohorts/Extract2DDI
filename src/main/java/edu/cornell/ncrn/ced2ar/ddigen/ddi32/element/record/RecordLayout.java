package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.Software;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.SoftwareType;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class RecordLayout extends ElementWithUrn {

	public static final String NODE_NAME_RECORD_LAYOUT = "ddi1:RecordLayout";

	private PhysicalStructureLinkReference reference;
	private Software software;
	private DefaultVariableSchemeReference defaultVariableSchemeReference;
	private List<DataItem> dataItemList = new ArrayList<>();

	public RecordLayout(String id, String agency, String variableSchemeId, String ddiLang, String productIdentification) {
		super(id, agency);
		this.defaultVariableSchemeReference = new DefaultVariableSchemeReference(variableSchemeId, agency);
		this.software = new Software(new StringElement("SPSS", ddiLang), productIdentification, "ddi1", SoftwareType.System);
	}

	public void addDataItem(DataItem dataItem) {
		synchronized (dataItemList) {
			dataItemList.add(dataItem);
		}
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element recordLayout = doc.createElement(NODE_NAME_RECORD_LAYOUT);

		super.appendToElement(recordLayout, doc);

		getReference().appendToElement(recordLayout, doc);

		software.appendToElement(recordLayout, doc);

		getDefaultVariableSchemeReference().appendToElement(recordLayout, doc);

		for (DataItem dataItem : getDataItemList()) {
			dataItem.appendToElement(recordLayout, doc);
		}

		parent.appendChild(recordLayout);
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

	public void setReference(String physicalStructureLinkReferenceId) {
		this.reference = new PhysicalStructureLinkReference(physicalStructureLinkReferenceId, getAgency());
	}
}
