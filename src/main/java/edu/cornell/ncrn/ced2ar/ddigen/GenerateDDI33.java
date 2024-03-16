package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.FragmentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentInstanceGenerator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.util.List;
import java.util.Map;

public class GenerateDDI33 extends DdiLifecycleGenerator {

	private static final Logger logger = Logger.getLogger(GenerateDDI33.class);

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private String statistics;

	public GenerateDDI33(
		String agency,
		String ddiLanguage,
		Map<String, String> excludeVariableToStatMap,
		String statistics
	) {
		this.agency = agency;
		this.ddiLanguage = ddiLanguage;
		this.excludeVariableToStatMap = excludeVariableToStatMap;
		this.statistics = statistics;
	}

	public DDI generateDDI(String dataFile, boolean runSumStats, long observationLimit) throws Exception {
		VariableCsv variableCsv = generateVariablesCsv(dataFile, runSumStats, observationLimit);

		FragmentGenerator fragmentGenerator = new FragmentGenerator(
			categorySchemeList,
			codeListList,
			variableSchemeList,
			variableCsv.getVariableStatList(),
			getStatistics(),
			getExcludeVariableToStatMap(),
			null,
			getAgency(),
			getDdiLanguage(),
			dataFile,
			dataFormat,
			productIdentification
		);
		fragmentGenerator.setVariableToFrequencyMap(variableCsv.getVariableToFrequencyMap());
		List<Fragment> fragmentList = fragmentGenerator.getFragmentList();

		FragmentInstanceGenerator generator = new FragmentInstanceGenerator(fragmentList);
		Document fragmentInstanceDocument = generator.toDocument();

		// We need VariableDDIGenerator to translate XML document to string
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
		return new DDI(xml, variableCsv);
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

	public String getStatistics() {
		return statistics;
	}
}