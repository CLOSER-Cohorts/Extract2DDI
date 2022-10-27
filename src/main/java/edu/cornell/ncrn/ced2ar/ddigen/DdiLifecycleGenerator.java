package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.category.Category;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.Code;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.StataCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DdiLifecycleGenerator {
	protected final List<CategoryScheme> categorySchemeList = new ArrayList<>();
	protected final List<CodeList> codeListList = new ArrayList<>();
	protected final List<VariableScheme> variableSchemeList = new ArrayList<>();
	protected String productIdentification;

	protected void populateSpss(SpssCsvGenerator spssGen, SPSSFile spssFile) throws Exception {
		Document logicalProductDocument = spssGen.getDDI3LogicalProduct(spssFile);

		categorySchemeList.addAll(LogicalProductFactory.createCategorySchemeList(logicalProductDocument));
		codeListList.addAll(LogicalProductFactory.createCodeListList(logicalProductDocument));
		variableSchemeList.addAll(LogicalProductFactory.createVariableSchemeList(logicalProductDocument));
	}

	protected VariableCsv generateVariablesCsv(String dataFile, boolean runSumStats, long observationLimit) throws Exception {
		VariableCsv variableCsv;
		if (dataFile.toLowerCase().endsWith(".dta")) {
			// STATA
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);
			populateStata(variableCsv);

		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			// SPSS
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);

			File serverFile = new File(dataFile);
			SPSSFile spssFile = new SPSSFile(serverFile);
			populateSpss(spssGen, spssFile);
			productIdentification = spssFile.getDDI2().getElementsByTagName("software").item(1).getTextContent();
		} else {
			throw new IllegalArgumentException("Unknown data file extension");
		}

		return variableCsv;
	}

	protected void populateStata(VariableCsv variableCsv) throws Exception {
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		List<CodebookVariable> codebookVariables = variableDDIGenerator.getCodebookVariables(variableCsv);

		for (CodebookVariable codebookVariable : codebookVariables) {
			List<Category> categoryList = new ArrayList<>();
			List<Code> codeList = new ArrayList<>();
			for (String variableCode : codebookVariable.getVariableCodes()) {
				if (variableCode.equalsIgnoreCase(codebookVariable.getName()))
					continue;

				String splits[] = variableCode.split("=");
				if (splits.length < 2)
					continue;

				Category category = new Category(splits[0]);
				category.setLabel(splits[1]);
				categoryList.add(category);

				Code code = new Code(splits[0]);
				code.setValue(splits[1]);
				codeList.add(code);
			}
			if (!categoryList.isEmpty()) {
				CategoryScheme categoryScheme = new CategoryScheme();
				categoryScheme.setCategoryList(categoryList);
				categorySchemeList.add(categoryScheme);
			}

			if (!codeList.isEmpty()) {
				CodeList localCodeList = new CodeList();
				localCodeList.setCodeList(codeList);
				codeListList.add(localCodeList);
			}
		}

		List<Variable> variableList = new ArrayList<>();
		for (Ced2arVariableStat stat : variableCsv.getVariableStatList()) {
			Variable variable = new Variable(UUID.randomUUID().toString());
			variable.setName(stat.getName());
			variable.setLabel(stat.getLabel());
			variableList.add(variable);
		}

		VariableScheme defaultVariableScheme = new VariableScheme(UUID.randomUUID().toString());
		defaultVariableScheme.setVariableList(variableList);
		variableSchemeList.add(defaultVariableScheme);
	}
}
