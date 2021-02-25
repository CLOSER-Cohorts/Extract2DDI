package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.FileUtil;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Category;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Variable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class LogicalProductGenerator {

	public static final String KEY_AGENCY = "edu.cornell.ncrn.ced2ar.ddigen.fragment.agency";
	public static final String KEY_XML_LANG = "edu.cornell.ncrn.ced2ar.ddigen.fragment.xml.lang";

	private LogicalProduct logicalProduct;

	public LogicalProductGenerator(LogicalProduct logicalProduct) {
		setLogicalProduct(logicalProduct);
	}

	public List<Fragment> getCategoryFragmentList(String categorySchemeId, String agency, int version, String xmlLang) {

		List<Fragment> fragmentList = new ArrayList<>();
		for (Category category : logicalProduct.getCategoryList()) {
			String id = UUID.randomUUID().toString();
			CategoryFragment categoryFragment = new CategoryFragment(id, agency, version);

			Label label = new Label(category.getLabel(), xmlLang);
			categoryFragment.setLabel(label);

			fragmentList.add(categoryFragment);
		}

		return fragmentList;
	}

	public LogicalProduct getLogicalProduct() {
		return logicalProduct;
	}

	public List<Fragment> getVariableFragmentList(String variableSchemeId, String agency, int version, String xmlLang) {
		VariableScheme variableScheme = new VariableScheme(variableSchemeId, agency, version);
		List<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(variableScheme);
		for (Variable variable : logicalProduct.getVariableList()) {

			String id = UUID.randomUUID().toString();
			VariableReferenceFragment variableReferenceFragment = new VariableReferenceFragment(id, agency, version);
			variableScheme.addVariable(variableReferenceFragment);


			VariableFragment variableFragment = new VariableFragment(id, agency, version);

			Label label = new Label(variable.getLabel(), xmlLang);
			variableFragment.setLabel(label);

			StringElement string = new StringElement(variable.getName(), xmlLang);
			variableFragment.setName(string);

			Representation representation = variable.getRepresentation();
			if (representation instanceof TextRepresentation) {
				TextVariableRepresentation textVariableRepresentation = new TextVariableRepresentation();
				variableFragment.setRepresentation(textVariableRepresentation);
			} else if (representation instanceof NumericRepresentation) {
				NumericVariableRepresentation numericVariableRepresentation = new NumericVariableRepresentation(
					((NumericRepresentation) representation).getType()
				);
				variableFragment.setRepresentation(numericVariableRepresentation);
			} else if (representation instanceof DateTimeRepresentation) {
				DateTimeVariableRepresentation dateTimeVariableRepresentation = new DateTimeVariableRepresentation(
					((DateTimeRepresentation) representation).getType()
				);
				variableFragment.setRepresentation(dateTimeVariableRepresentation);
			} else if (representation instanceof CodeRepresentation) {
				CodeVariableRepresentation codeVariableRepresentation = new CodeVariableRepresentation(id, agency, version);
				variableFragment.setRepresentation(codeVariableRepresentation);
			}
			fragmentList.add(variableFragment);
		}

		return fragmentList;
	}

	public List<Fragment> toFragmentList() {
		Properties properties = FileUtil.getPropertiesFromResource(getClass());
		String agency = properties.getProperty(KEY_AGENCY);
		String xmlLang = properties.getProperty(KEY_XML_LANG);

		List<Fragment> fragmentList = new ArrayList<>();

		fragmentList.addAll(getCategoryFragmentList(
			UUID.randomUUID().toString(),
			agency,
			1,
			xmlLang
		));

		fragmentList.addAll(getVariableFragmentList(
			UUID.randomUUID().toString(),
			agency,
			1,
			xmlLang
		));


		return fragmentList;
	}

	public void setLogicalProduct(LogicalProduct logicalProduct) {
		this.logicalProduct = logicalProduct;
	}
}