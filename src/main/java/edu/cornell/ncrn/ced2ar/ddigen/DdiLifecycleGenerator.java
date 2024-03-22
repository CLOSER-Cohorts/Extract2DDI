package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.category.Category;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.Code;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.StataCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.representation.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class DdiLifecycleGenerator {
	protected final List<CategoryScheme> categorySchemeList = new ArrayList<>();
	protected final List<CodeList> codeListList = new ArrayList<>();
	protected final List<VariableScheme> variableSchemeList = new ArrayList<>();
	protected Map<String, String> attributeMap = new HashMap<>();
	protected Map<String, String> codeSchemeToCategorySchemeMap = new HashMap<>();
	protected FileFormatInfo.Format dataFormat;
	protected String productIdentification;

	protected VariableCsv generateVariablesCsv(String dataFile, boolean runSumStats, long observationLimit) throws Exception {
		VariableCsv variableCsv;
		if (dataFile.toLowerCase().endsWith(".dta")) {
			// STATA
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);
			populate(variableCsv);
			dataFormat = FileFormatInfo.Format.STATA;

		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			// SPSS
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);

			File serverFile = new File(dataFile);
			SPSSFile spssFile = new SPSSFile(serverFile);

			// the next two lines are to initialise the variableMap inside spssFile
			spssGen.getDDI3LogicalProduct(spssFile);
			spssFile.getVariable(0);

			populate(variableCsv);

			productIdentification = spssFile.getDDI2().getElementsByTagName("software").item(1).getTextContent();
			dataFormat = FileFormatInfo.Format.SPSS;
		} else {
			throw new IllegalArgumentException("Unknown data file extension");
		}

		return variableCsv;
	}

	protected void populate(VariableCsv variableCsv) throws Exception {
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		List<CodebookVariable> codebookVariables = variableDDIGenerator.getCodebookVariables(variableCsv);
		Map<String, String> variableToCodeSchemeMap = new HashMap<>();

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
			if (!categoryList.isEmpty() && !codeList.isEmpty()) {
				String categorySchemeId = UUID.randomUUID().toString();
				CategoryScheme categoryScheme = new CategoryScheme(categorySchemeId, categoryList);
				categorySchemeList.add(categoryScheme);

				String codeSchemeId = UUID.randomUUID().toString();
				CodeList localCodeList = new CodeList(codeSchemeId, codeList);
				codeListList.add(localCodeList);
				variableToCodeSchemeMap.put(codebookVariable.getName(), codeSchemeId);
				codeSchemeToCategorySchemeMap.put(codeSchemeId, categorySchemeId);
			}
		}

		List<Variable> variableList = new ArrayList<>();
		for (Ced2arVariableStat stat : variableCsv.getVariableStatList()) {
			Variable variable = new Variable(UUID.randomUUID().toString());
			variable.setName(stat.getName());
			variable.setLabel(stat.getLabel());

			Representation representation;
			if (stat.isNumeric() && stat.getValidValues().isEmpty()) {
				representation = new NumericRepresentation("Decimal");
			} else if (stat.isNumeric() && !stat.getValidValues().isEmpty()) {
				String codeSchemeId = variableToCodeSchemeMap.get(stat.getName());
				representation = new CodeRepresentation(codeSchemeId);
			} else if (stat.isDate()) {
				representation = new DateTimeRepresentation("DateTime");
			} else {
				representation = new TextRepresentation();
			}
			variable.setRepresentation(representation);
			variableList.add(variable);
		}

		VariableScheme defaultVariableScheme = new VariableScheme(UUID.randomUUID().toString());
		defaultVariableScheme.setVariableList(variableList);
		variableSchemeList.add(defaultVariableScheme);
	}
}
