package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.FileUtil;
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

public class LogicalProductTransformer {

	public static final String KEY_AGENCY = "edu.cornell.ncrn.ced2ar.ddigen.fragment.agency";
	public static final String KEY_XML_LANG = "edu.cornell.ncrn.ced2ar.ddigen.fragment.xml.lang";

	private LogicalProduct logicalProduct;

	public LogicalProductTransformer(LogicalProduct logicalProduct) {
		setLogicalProduct(logicalProduct);
	}

	public LogicalProduct getLogicalProduct() {
		return logicalProduct;
	}

	public List<Fragment> toFragmentList() {
		Properties properties = FileUtil.getPropertiesFromResource(getClass());
		String agency = properties.getProperty(KEY_AGENCY);
		String xmlLang = properties.getProperty(KEY_XML_LANG);

		List<Fragment> fragmentList = new ArrayList<>();

		VariableScheme variableScheme = new VariableScheme();

		List<Fragment> variableFragmentList = new ArrayList<>();
		for (Variable variable : logicalProduct.getVariableList()) {
			String id = UUID.randomUUID().toString();

			VariableReferenceFragment variableReferenceFragment = new VariableReferenceFragment();
			variableScheme.addVariable(variableReferenceFragment);
			variableReferenceFragment.setAgency(agency);
			variableReferenceFragment.setId(id);
			variableScheme.addVariable(variableReferenceFragment);

			VariableFragment variableFragment = new VariableFragment();
			variableFragment.setAgency(agency);
			variableFragment.setId(id);
			variableFragment.setXmlLang(xmlLang);
			variableFragment.setLabel(variable.getLabel());
			variableFragment.setName(variable.getName());
			variableFragment.setName(variable.getName());

			Representation representation = variable.getRepresentation();
			if (representation instanceof TextRepresentation) {
				TextVariableRepresentation textVariableRepresentation = new TextVariableRepresentation();
				variableFragment.setRepresentation(textVariableRepresentation);
			} else if (representation instanceof NumericRepresentation) {
				NumericRepresentation numericRepresentation = (NumericRepresentation) representation;
				NumericVariableRepresentation numericVariableRepresentation = new NumericVariableRepresentation();
				numericVariableRepresentation.setType(numericRepresentation.getType());
				variableFragment.setRepresentation(numericVariableRepresentation);
			} else if (representation instanceof DateTimeRepresentation) {
				DateTimeRepresentation dateTimeRepresentation = (DateTimeRepresentation) representation;
				DateTimeVariableRepresentation dateTimeVariableRepresentation = new DateTimeVariableRepresentation();
				dateTimeVariableRepresentation.setType(dateTimeRepresentation.getType());
				variableFragment.setRepresentation(dateTimeVariableRepresentation);
			}
			variableFragmentList.add(variableFragment);
		}

		fragmentList.add(variableScheme);
		fragmentList.addAll(variableFragmentList);
		return fragmentList;
	}

	public void setLogicalProduct(LogicalProduct logicalProduct) {
		this.logicalProduct = logicalProduct;
	}
}