package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategoryFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategoryReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategorySchemeFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code.CodeFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code.CodeListFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code.CodeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableSchemeFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Category;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Code;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.VariableScheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LogicalProductGenerator {

	private String agency;
	private String ddiLanguage;
	private LogicalProduct logicalProduct;

	public LogicalProductGenerator(LogicalProduct logicalProduct, String agency, String ddiLanguage) {
		setAgency(agency);
		setDdiLanguage(ddiLanguage);
		setLogicalProduct(logicalProduct);
	}

	public String getAgency() {
		return agency;
	}

	public List<Fragment> getCategoryFragmentList(
		String agency,
		int version,
		Map<String, UUID> categoryIdToUuidMap,
		String xmlLang
	) {
		List<Fragment> fragmentList = new ArrayList<>();
		List<Fragment> categoryFragmentList = new ArrayList<>();

		for (CategoryScheme categoryScheme : getLogicalProduct().getCategorySchemeList()) {
			String categorySchemeId = UUID.randomUUID().toString();
			CategorySchemeFragment fragment = new CategorySchemeFragment(categorySchemeId, agency, version);
			fragmentList.add(fragment);
			for (Category category : categoryScheme.getCategoryList()) {
				String id = categoryIdToUuidMap.get(category.getId()).toString();
				CategoryReferenceFragment reference = new CategoryReferenceFragment(id, agency, version);
				fragment.addCategoryReference(reference);

				CategoryFragment categoryFragment = new CategoryFragment(id, agency, version);

				Label label = new Label(category.getLabel(), xmlLang);
				categoryFragment.setLabel(label);

				categoryFragmentList.add(categoryFragment);
			}
		}

		fragmentList.addAll(categoryFragmentList);
		return fragmentList;
	}

	public List<Fragment> getCodeListFragmentList(
		String agency,
		int version,
		Map<String, UUID> categoryIdToUuidMap,
		String xmlLang
	) {
		List<Fragment> fragmentList = new ArrayList<>();
		for (CodeList codeList : getLogicalProduct().getCodeListList()) {
			String categorySchemeId = UUID.randomUUID().toString();
			CodeListFragment codeListFragment = new CodeListFragment(categorySchemeId, agency, version);

			Label label = new Label(codeList.getLabel(), xmlLang);
			codeListFragment.setLabel(label);

			fragmentList.add(codeListFragment);
			for (Code code : codeList.getCodeList()) {
				String id = UUID.randomUUID().toString();

				String categoryId = categoryIdToUuidMap.get(code.getCategoryId()).toString();
				CodeFragment codeFragment = new CodeFragment(id, agency, version, code.getValue());
				CategoryReferenceFragment reference = new CategoryReferenceFragment(categoryId, agency, version);
				codeFragment.setCategoryReference(reference);
				codeListFragment.addCode(codeFragment);
			}
		}

		return fragmentList;
	}

	public String getDdiLanguage() {
		return ddiLanguage;
	}

	public LogicalProduct getLogicalProduct() {
		return logicalProduct;
	}

	public List<Fragment> getVariableFragmentList(String variableSchemeId, String agency, int version, String xmlLang) {
		VariableSchemeFragment variableSchemeFragment =
			new VariableSchemeFragment(variableSchemeId, agency, version);
		List<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(variableSchemeFragment);
		for (VariableScheme variableScheme : getLogicalProduct().getVariableSchemeList()) {

			for (Variable variable : variableScheme.getVariableList()) {
				String id = UUID.randomUUID().toString();
				VariableReferenceFragment variableReferenceFragment = new VariableReferenceFragment(id, agency, version);
				variableSchemeFragment.addVariable(variableReferenceFragment);

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
					DateTimeVariableRepresentation dateTimeVariableRepresentation =new DateTimeVariableRepresentation(
						((DateTimeRepresentation) representation).getType()
					);
					variableFragment.setRepresentation(dateTimeVariableRepresentation);
				} else if (representation instanceof CodeRepresentation) {
					CodeVariableRepresentation codeVariableRepresentation =
						new CodeVariableRepresentation(id, agency, version);
					variableFragment.setRepresentation(codeVariableRepresentation);
				}
				fragmentList.add(variableFragment);
			}
		}

		return fragmentList;
	}

	public List<Fragment> toFragmentList() {
		List<Fragment> fragmentList = new ArrayList<>();

		Map<String, UUID> categoryIdToUuidMap = new HashMap<>();

		for (CategoryScheme categoryScheme : getLogicalProduct().getCategorySchemeList()) {
			for (Category category : categoryScheme.getCategoryList()) {
				if (category.getId() != null) {
					categoryIdToUuidMap.put(category.getId(), UUID.randomUUID());
				}
			}
		}

		fragmentList.addAll(
			getCategoryFragmentList(getAgency(), 1, categoryIdToUuidMap, getDdiLanguage())
		);

		fragmentList.addAll(
			getCodeListFragmentList(getAgency(), 1, categoryIdToUuidMap, getDdiLanguage())
		);

		fragmentList.addAll(
			getVariableFragmentList(UUID.randomUUID().toString(), getAgency(), 1, getDdiLanguage())
		);

		return fragmentList;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	public void setLogicalProduct(LogicalProduct logicalProduct) {
		this.logicalProduct = logicalProduct;
	}
}