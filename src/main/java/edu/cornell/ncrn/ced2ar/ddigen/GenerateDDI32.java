package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.DDIInstance;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.util.Map;

public class GenerateDDI32 extends DdiLifecycleGenerator {

	private static final Logger logger = Logger.getLogger(GenerateDDI32.class);

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private String statistics;

	public GenerateDDI32(
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

		DDIInstance ddiInstance = new DDIInstance(
				agency,
				ddiLanguage,
				dataFile,
				"",
				"",
				variableSchemeList,
				categorySchemeList,
				codeListList,
				dataFormat,
				productIdentification,
				statistics,
				codeSchemeToCategorySchemeMap,
				excludeVariableToStatMap,
				variableCsv.getVariableToFrequencyMap()
		);

		DDI32DocumentGenerator generator = new DDI32DocumentGenerator(ddiInstance);
		Document fragmentInstanceDocument = generator.toDocument();

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();

		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
		return new DDI(xml, variableCsv);
	}
}