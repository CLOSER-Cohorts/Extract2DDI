package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.apache.commons.math3.stat.Frequency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

public class DDIInstance extends ElementWithUrn {

	public static final String NODE_NAME_DDI_INSTANCE = "DDIInstance";

	public static final String ATTRIBUTE_NAME_NAMESPACE = "xmlns";
	public static final String ATTRIBUTE_VALUE_NAMESPACE = "ddi:instance:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_R = "xmlns:r";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_R = "ddi:instance:3_2";

	private ResourcePackageElement resourcePackage;
	private Citation citation;

	public DDIInstance(
			String agency,
			String ddiLanguage,
			String title,
			String datasetUri,
			String isPublic,
			String citationTitle,
			String citationAlternateTitle,
			List<VariableScheme> variableSchemes,
			List<CategoryScheme> categorySchemes,
			List<CodeList> codeLists,
			FileFormatInfo.Format dataFormat,
			String productIdentification,
			String statistics,
			Map<String, String> codeSchemeToCategorySchemeMap,
			Map<String, String> excludeVariableToStatMap,
			Map<String, Frequency> variableToFrequencyMap
	) {
		super(agency);

		Citation citation = new Citation(citationTitle + " Instance", citationAlternateTitle, ddiLanguage);
		setCitation(citation);

		// Resource package
		ResourcePackageElement resourcePackage = new ResourcePackageElement(
				getAgency(),
				ddiLanguage,
				title,
				datasetUri,
				isPublic,
				citationTitle,
				citationAlternateTitle,
				variableSchemes,
				categorySchemes,
				codeLists,
				dataFormat,
				productIdentification,
				statistics,
				codeSchemeToCategorySchemeMap,
				excludeVariableToStatMap,
				variableToFrequencyMap
		);

		setResourcePackage(resourcePackage);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		// Resource Package
		super.appendToElement(element, doc);

		// Citation
		if (getCitation() != null) {
			getCitation().appendToElement(element, doc);
		}
		getResourcePackage().appendToElement(element, doc);
	}

	public Citation getCitation() {
		return citation;
	}

	public ResourcePackageElement getResourcePackage() {
		return resourcePackage;
	}

	public void setCitation(Citation citation) {
		this.citation = citation;
	}
	public void setResourcePackage(ResourcePackageElement resourcePackage) {
		this.resourcePackage = resourcePackage;
	}
}
