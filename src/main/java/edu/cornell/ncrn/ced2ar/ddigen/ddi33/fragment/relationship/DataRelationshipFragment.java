package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.relationship;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataRelationshipFragment extends FragmentWithUrn {

	public static final String NODE_NAME_DATA_RELATIONSHIP = "DataRelationship";
	public static final String NODE_NAME_DATA_RELATIONSHIP_NAME = "DataRelationshipName";

	private StringElement name;
	private LogicalRecordFragment logicalRecord;

	public DataRelationshipFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element fragment = createFragment(doc);

		Element dataRelationship = doc.createElement(NODE_NAME_DATA_RELATIONSHIP);
		setVersionDateAttribute(dataRelationship);
		setUniversallyUniqueAttribute(dataRelationship);
		setNamespace(dataRelationship, NAMESPACE_LOGICAL_PRODUCT);

		super.appendToElement(dataRelationship, doc);

		if (getName() != null) {
			Element dataRelationshipName = doc.createElement(NODE_NAME_DATA_RELATIONSHIP_NAME);
			getName().appendToElement(dataRelationshipName, doc);
			dataRelationship.appendChild(dataRelationshipName);
		}

		if (getLogicalRecord() != null) {
			getLogicalRecord().appendToElement(dataRelationship, doc);
		}

		fragment.appendChild(dataRelationship);
		element.appendChild(fragment);
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