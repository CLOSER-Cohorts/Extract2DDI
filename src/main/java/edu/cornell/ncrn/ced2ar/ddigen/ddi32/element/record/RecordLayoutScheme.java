package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

public class RecordLayoutScheme extends ElementWithUrn {

	public static final String NODE_NAME_RECORD_LAYOUT_SCHEME = "p:RecordLayoutScheme";

	private RecordLayout recordLayout;

	public RecordLayoutScheme(
			String agency,
			String recordLayoutId,
			String variableSchemeId,
			String ddiLanguage,
			FileFormatInfo.Format dataFormat,
			String productIdentification,
			String physicalRecordSegmentId,
			List<VariableScheme> variableSchemeList,
			Map<String, String> attributeMap
	) {
		super(agency);

		//FileFormatInfo.Format dataType = getTitle().toLowerCase().endsWith(".dta") ? "STATA" : "SPSS";
		RecordLayout recordLayout = new RecordLayout(recordLayoutId, agency, variableSchemeId, ddiLanguage, dataFormat, productIdentification);

		// Physical Structure Link Reference
		recordLayout.setReference(physicalRecordSegmentId);

		// Data List Item
		for (VariableScheme variableScheme : variableSchemeList) {
			for (Variable variable : variableScheme.getVariableList()) {
				recordLayout.addDataItem(variable.getUuid(), attributeMap);
			}
		}

		setRecordLayout(recordLayout);
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
