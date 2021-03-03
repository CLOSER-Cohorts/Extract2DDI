package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataRelationshipFragment extends FragmentWithUrn {

	public static final String NODE_NAME_DATA_RELATIONSHIP = "r:DataRelationship";
	public static final String NODE_NAME_DATA_RELATIONSHIP_NAME = "r:DataRelationshipName";

	private StringElement name;
	private LogicalRecordFragment logicalRecord;

	public DataRelationshipFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_DATA_RELATIONSHIP);
		element.appendChild(fragment);

		super.appendToElement(fragment, doc, namespace);

		if (getName() != null) {
			Element dataRelationshipName = doc.createElementNS(namespace, NODE_NAME_DATA_RELATIONSHIP_NAME);
			getName().appendToElement(dataRelationshipName, doc, namespace);
			fragment.appendChild(dataRelationshipName);
		}

		if (getLogicalRecord() != null) {
			getLogicalRecord().appendToElement(fragment, doc, namespace);
		}
	}

	public LogicalRecordFragment getLogicalRecord() {
		return logicalRecord;
	}

	public StringElement getName() {
		return name;
	}

	public void setLogicalRecord(LogicalRecordFragment logicalRecord) {
		this.logicalRecord = logicalRecord;
	}

	public void setName(StringElement name) {
		this.name = name;
	}
}