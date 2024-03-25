package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.Software;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.SoftwareType;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordLayout extends ElementWithUrn {

	public static final String NODE_NAME_RECORD_LAYOUT = "ddi1:RecordLayout";

	private PhysicalStructureLinkReference reference;
	private Software software;
	private DefaultVariableSchemeReference defaultVariableSchemeReference;
	private List<DataItem> dataItemList = new ArrayList<>();

	public RecordLayout(String id, String agency, String variableSchemeId, String ddiLang, FileFormatInfo.Format dataFormat, String productIdentification) {
		super(id, agency);
		this.defaultVariableSchemeReference = new DefaultVariableSchemeReference(variableSchemeId, agency);
		this.software = new Software(new StringElement(dataFormat.toString(), ddiLang), productIdentification, "ddi1", SoftwareType.System);
	}

	public void addDataItem(String variableId, Map<String, String> attributeMap) {
		synchronized (dataItemList) {
			DataItem dataItem = new DataItem();

				dataItem.setReference(variableId, getAgency());

				if (!attributeMap.isEmpty()) {
					ProprietaryInfo proprietaryInfo = new ProprietaryInfo();

					// Anticipating the following properties: Width, Decimals, WriteFormatType etc
					for (Map.Entry<String, String> attribute : attributeMap.entrySet()) {
						proprietaryInfo.addProprietaryProperty(attribute.getKey(), attribute.getValue());
					}
					dataItem.setProprietaryInfo(proprietaryInfo);
				}
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
