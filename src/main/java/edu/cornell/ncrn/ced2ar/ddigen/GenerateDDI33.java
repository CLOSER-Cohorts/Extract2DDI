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
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.FragmentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GenerateDDI33 {

	private static final Logger logger = Logger.getLogger(GenerateDDI33.class);

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private String outputFile;
	private String statistics;

	public GenerateDDI33(
		String agency,
		String ddiLanguage,
		Map<String, String> excludeVariableToStatMap,
		String statistics,
		String outputFile
	) {
		setAgency(agency);
		setDdiLanguage(ddiLanguage);
		setExcludeVariableToStatMap(excludeVariableToStatMap);
		setOutputFile(outputFile);
		setStatistics(statistics);
	}

	public void generateDDI(
		String dataFile,
		boolean runSumStats,
		long observationLimit,
		boolean isStatisticFileEnabled,
		boolean isFrequencyFileEnabled
	) throws Exception {
		long s = System.currentTimeMillis();
		VariableCsv variableCsv = null;
		int recordCount = 0;
		List<CategoryScheme> categorySchemeList = new ArrayList<>();
		List<CodeList> codeListList = new ArrayList<>();
		List<VariableScheme> variableSchemeList = new ArrayList<>();

		if (dataFile.toLowerCase().endsWith(".dta")) {
			// STATA
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);

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

		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			// SPSS
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);

			File serverFile = new File(dataFile);
			SPSSFile spssFile = new SPSSFile(serverFile);

			Document logicalProductDocument = spssGen.getDDI3LogicalProduct(spssFile);

			categorySchemeList.addAll(LogicalProductFactory.createCategorySchemeList(logicalProductDocument));
			codeListList.addAll(LogicalProductFactory.createCodeListList(logicalProductDocument));
			variableSchemeList.addAll(LogicalProductFactory.createVariableSchemeList(logicalProductDocument));

			recordCount = spssFile.getRecordCount();
		}

		FragmentGenerator fragmentGenerator = new FragmentGenerator(
			categorySchemeList,
			codeListList,
			variableSchemeList,
			variableCsv.getVariableStatList(),
			getStatistics(),
			getExcludeVariableToStatMap(),
			getAgency(),
			getDdiLanguage(),
			dataFile,
			recordCount
		);
		fragmentGenerator.setVariableToFrequencyMap(variableCsv.getVariableToFrequencyMap());
		List<Fragment> fragmentList = fragmentGenerator.getFragmentList();

		FragmentInstanceGenerator generator = new FragmentInstanceGenerator(fragmentList);
		Document fragmentInstanceDocument = generator.toDocument();

		// We need VariableDDIGenerator to translate XML document to string
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument, "UTF-8");

		String fileName;
		if (getOutputFile() != null && !getOutputFile().trim().isEmpty()) {
			fileName = getOutputFile();
		} else {
			fileName = dataFile;
		}

		FileUtil.createFile(xml, fileName + ".xml");
		logger.info("Successfully created DDI file");

		logger.info("CSV created in: "+ ((System.currentTimeMillis() - s) / 1000.0) + " seconds ");
		FileUtil.createFile(variableCsv.getVariableStatistics(), dataFile+".vars.csv");

		if (isFrequencyFileEnabled) {
			FileUtil.createFile(variableCsv.getFrequencies(), dataFile+".freq.csv");
		}

		if (isStatisticFileEnabled) {
			FileUtil.createFile(variableCsv.getStatistics(), dataFile+".stats.csv");
		}

		FileUtil.createFile(variableCsv.getVariableValueLables(), dataFile+"_var_values.csv");
		logger.info("Successfully created csv files");

		logger.info(observationLimit);
	}

	public String getAgency() {
		return agency;
	}

	public String getDdiLanguage() {
		return ddiLanguage;
	}

	public Map<String, String> getExcludeVariableToStatMap() {
		return excludeVariableToStatMap;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	public void setExcludeVariableToStatMap(Map<String, String> excludeVariableToStatMap) {
		this.excludeVariableToStatMap = excludeVariableToStatMap;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
}