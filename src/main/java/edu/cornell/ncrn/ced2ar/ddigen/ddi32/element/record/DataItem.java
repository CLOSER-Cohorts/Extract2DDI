package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableReference;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataItem implements Appendable {

	public static final String NODE_NAME_DATA_ITEM = "ddi1:DataItem";
	public static final String ATTRIBUTE_KEY_DECIMALS = "Decimals";
	public static final String ATTRIBUTE_KEY_FORMAT = "WriteFormatType";
	public static final String ATTRIBUTE_KEY_WIDTH = "Width";

	private Reference reference;
	private ProprietaryInfo proprietaryInfo;

	public DataItem(Variable variable, String agency) {
		setReference(variable.getUuid(), agency);

		ProprietaryInfo proprietaryInfo = new ProprietaryInfo();

		proprietaryInfo.addProprietaryProperty(ATTRIBUTE_KEY_WIDTH, Integer.toString(variable.getWidth()));
		proprietaryInfo.addProprietaryProperty(ATTRIBUTE_KEY_FORMAT, variable.getFormat());
		proprietaryInfo.addProprietaryProperty(ATTRIBUTE_KEY_DECIMALS, Integer.toString(variable.getDeciamls()));

		setProprietaryInfo(proprietaryInfo);

	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element dataItem = doc.createElement(NODE_NAME_DATA_ITEM);

		getReference().appendToElement(dataItem, doc);

		if (getProprietaryInfo() != null) {
			getProprietaryInfo().appendToElement(dataItem, doc);
		}

		element.appendChild(dataItem);
	}

	public ProprietaryInfo getProprietaryInfo() {
		return proprietaryInfo;
	}

	public Reference getReference() {
		return reference;
	}

	public void setProprietaryInfo(ProprietaryInfo proprietaryInfo) {
		this.proprietaryInfo = proprietaryInfo;
	}

	public void setReference(String variableId, String agency) {
		this.reference = new VariableReference(variableId, agency);
	}
}
