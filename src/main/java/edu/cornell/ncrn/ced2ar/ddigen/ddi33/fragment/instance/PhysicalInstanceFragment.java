package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.instance;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Citation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.relationship.DataRelationshipReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhysicalInstanceFragment extends FragmentWithUrn {

	public static final String NODE_NAME_PHYSICAL_INSTANCE = "PhysicalInstance";

	private Citation citation;
	private DataFileIdentification dataFileIdentification;
	private DataRelationshipReferenceFragment dataRelationshipReference;
	private GrossFileStructure grossFileStructure;

	public PhysicalInstanceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element fragment = createFragment(doc);

		Element physicalInstance = doc.createElement(NODE_NAME_PHYSICAL_INSTANCE);
		setUniversallyUniqueAttribute(physicalInstance);
		setVersionDateAttribute(physicalInstance);
		setNamespace(physicalInstance, NAMESPACE_PHYSICAL_INSTANCE);

		fragment.appendChild(physicalInstance);
		element.appendChild(fragment);

		super.appendToElement(physicalInstance, doc);

		if (getCitation() != null) {
			getCitation().appendToElement(physicalInstance, doc);
		}

		if (getDataRelationshipReference() != null) {
			getDataRelationshipReference().appendToElement(physicalInstance, doc);
		}

		if (getDataFileIdentification() != null) {
			getDataFileIdentification().appendToElement(physicalInstance, doc);
		}

		if (getGrossFileStructure() != null) {
			getGrossFileStructure().appendToElement(physicalInstance, doc);
		}

		element.appendChild(fragment);

	}

	public Citation getCitation() {
		return citation;
	}

	public DataFileIdentification getDataFileIdentification() {
		return dataFileIdentification;
	}

	public DataRelationshipReferenceFragment getDataRelationshipReference() {
		return dataRelationshipReference;
	}

	public GrossFileStructure getGrossFileStructure() {
		return grossFileStructure;
	}

	public void setCitation(Citation citation) {
		this.citation = citation;
	}

	public void setDataFileIdentification(DataFileIdentification dataFileIdentification) {
		this.dataFileIdentification = dataFileIdentification;
	}

	public void setDataRelationshipReference(DataRelationshipReferenceFragment fragment) {
		this.dataRelationshipReference = fragment;
	}

	public void setGrossFileStructure(
		GrossFileStructure grossFileStructure) {
		this.grossFileStructure = grossFileStructure;
	}
}