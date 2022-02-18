package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariablesInRecordElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogicalRecordElement extends ElementWithUrn {

	public static final String NODE_NAME_LOGICAL_RECORD = "ddi:LogicalRecord";
	public static final String NODE_NAME_LOGICAL_PRODUCT_NAME = "ddi:LogicalProductName";

	public static final String ATTRIBUTE_NAMESPACE_XML_LANG_NAME = "xml:lang";
	public static final String ATTRIBUTE_NAMESPACE_XML_LANG_VALUE = "en-GB";

	public static final String ATTRIBUTE_NAMESPACE_IS_MAINTAINABLE_NAME = "isMaintainable";
	public static final String ATTRIBUTE_NAMESPACE_IS_MAINTAINABLE_VALUE = "true";

	public static final String ATTRIBUTE_NAMESPACE_SCOPE_OF_UNIQUENESS_NAME = "scopeOfUniqueness";
	public static final String ATTRIBUTE_NAMESPACE_SCOPE_OF_UNIQUENESS_VALUE = "Agency";

	private VariablesInRecordElement variablesInRecord;
	private Name logicalProductName;

	public LogicalRecordElement(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element logicalRecord = doc.createElement(NODE_NAME_LOGICAL_RECORD);

		super.appendToElement(element, doc);

		// Logical Product Name
		getLogicalProductName().appendToElement(element, doc);

		// Variables In Record
		getVariablesInRecord().appendToElement(logicalRecord, doc);

		element.appendChild(logicalRecord);
	}

	public Name getLogicalProductName() {
		return logicalProductName;
	}

	public VariablesInRecordElement getVariablesInRecord() {
		return variablesInRecord;
	}

	public void setLogicalProductName(Name logicalProductName) {
		this.logicalProductName = logicalProductName;
	}

	public void setVariablesInRecord(VariablesInRecordElement variablesInRecord) {
		this.variablesInRecord = variablesInRecord;
	}
}
