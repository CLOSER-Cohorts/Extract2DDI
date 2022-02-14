package edu.cornell.ncrn.ced2ar.ddigen;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class VariableDDIGenerator {

	public String namespace = "";

	/*
	 * <var ID="V5" name="MSC"> <labl>Metropolitan Status Code (provided by
	 * MSG)</labl> <sumStat type="vald">1000</sumStat> <sumStat
	 * type="invd">0</sumStat> <sumStat type="min"/> <sumStat type="max"/>
	 * <sumStat type="mean"/> <sumStat type="stdev"/> <catgry>
	 * <catValu>3</catValu> <labl>Inside a suburban county of the MSA</labl>
	 * </catgry> <catgry> <catValu>2</catValu> <labl>Outside center city of an
	 * MSA but inside county containing
	 * c</labl></catgry><catgry><catValu>1</catValu><labl>In the center city of
	 * an MSA</labl></catgry><catgry><catValu>5</catValu><labl>Not in an
	 * MSA</labl></catgry><catgry><catValu>4</catValu><labl>In an MSA that has
	 * no center city</labl></catgry></var>
	 */

	public Document getCodebookDocument(List<CodebookVariable> variables, String fileName, boolean runSumStats) {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder;
		Document doc;

		try {
			domFactory.setNamespaceAware(true);
			domBuilder = domFactory.newDocumentBuilder();
			doc = domBuilder.newDocument();

			Element codeBook = doc.createElementNS(namespace, "codeBook");
			codeBook.setAttribute("version", "1.0");

			//We shouldn't give the codebook a random id
			//codeBook.setAttribute("ID", getUniqueID());
			doc.appendChild(codeBook);
			doc.getDocumentElement().normalize();

			// docDscr
			Element docDscr = (Element) codeBook.appendChild(doc
					.createElementNS(namespace, "docDscr"));
			Element stdyDscr = (Element) codeBook.appendChild(doc
					.createElementNS(namespace, "stdyDscr"));
			Element citation = (Element) stdyDscr.appendChild(doc
					.createElementNS(namespace, "citation"));
			Element titlStmt = (Element) citation.appendChild(doc
					.createElementNS(namespace, "titlStmt"));
			Element titl = (Element) titlStmt.appendChild(doc.createElementNS(
					namespace, "titl"));
			titl.setTextContent(fileName);
			Element dataDscr = (Element) codeBook.appendChild(doc
					.createElementNS(namespace, "dataDscr"));

			//TODO: Add more details to docDscr?

			int varNum = 0;
			for (CodebookVariable codebookVariable : variables) {
				//TODO:set variable format
				//<varFormat schema="other" type="numeric"/>

				Element varElement = doc.createElementNS(namespace, "var");
				varElement.setAttribute("ID", "V" + (++varNum));
				varElement.setAttribute("name", codebookVariable.getName());

				Element labelElement = doc.createElementNS(namespace, "labl");
				String label = StringEscapeUtils.escapeXml(codebookVariable.getLabel());
				labelElement.setTextContent(label);
				varElement.appendChild(labelElement);

				//Only add if sumstats are enabled
				if(runSumStats){
					Element sumStatValidElement = doc.createElementNS(namespace,"sumStat");
					sumStatValidElement.setAttribute("type", "vald");
					sumStatValidElement.setTextContent(codebookVariable.getValidCount());
					varElement.appendChild(sumStatValidElement);

					Element sumStatInValidElement = doc.createElementNS(namespace,"sumStat");
					sumStatInValidElement.setAttribute("type", "invd");
					sumStatInValidElement.setTextContent(codebookVariable.getInvalidCount());
					varElement.appendChild(sumStatInValidElement);

					Element sumStatMinElement = doc.createElementNS(namespace,"sumStat");
					sumStatMinElement.setAttribute("type", "min");
					sumStatMinElement.setTextContent(codebookVariable.getMinValue());
					varElement.appendChild(sumStatMinElement);

					Element sumStatMaxElement = doc.createElementNS(namespace,"sumStat");
					sumStatMaxElement.setAttribute("type", "max");
					sumStatMaxElement.setTextContent(codebookVariable.getMaxValue());
					varElement.appendChild(sumStatMaxElement);

					Element sumStatMeanElement = doc.createElementNS(namespace,"sumStat");
					sumStatMeanElement.setAttribute("type", "mean");
					sumStatMeanElement.setTextContent(codebookVariable.getMean());
					varElement.appendChild(sumStatMeanElement);

					Element sumStatStdDevElement = doc.createElementNS(namespace,"sumStat");
					sumStatStdDevElement.setAttribute("type", "stdev");
					sumStatStdDevElement.setTextContent(codebookVariable.getStdDeviation());
					varElement.appendChild(sumStatStdDevElement);
				}

				List<String> variableCodes = codebookVariable
						.getVariableCodes();
				for (String variableCode : variableCodes) {
					if (variableCode.equalsIgnoreCase(codebookVariable.getName()))
						continue;
					Element catgryElement = doc.createElementNS(namespace,"catgry");

					String splits[] = variableCode.split("=");
					if (splits.length < 2)
						continue;
					Element catValuElement = doc.createElementNS(namespace,"catValu");
					catValuElement.setTextContent(splits[0]);
					catgryElement.appendChild(catValuElement);

					Element lablElement = doc.createElementNS(namespace, "labl");
					String valueLabel = StringEscapeUtils.escapeXml(splits[1]);
					lablElement.setTextContent(valueLabel);
					catgryElement.appendChild(lablElement);

					varElement.appendChild(catgryElement);
				}
				dataDscr.appendChild(varElement);
			}
			return doc;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<CodebookVariable> getCodebookVariables(VariableCsv variableCSV)
			throws Exception {
		List<CodebookVariable> codebookVariables = new ArrayList<CodebookVariable>();
		List<String[]> vars = new ArrayList<String[]>();
		List<String[]> varValues = new ArrayList<String[]>();
		// String varString = variablesVSC[0];
		String varString = variableCSV.getVariableStatistics();
		String var1[] = varString.split("\n");
		for (int x = 0; x < var1.length; x++) {
			String[] xs = var1[x].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			vars.add(xs);
		}

		// varString = variablesVSC[1];
		varString = variableCSV.getVariableValueLables();
		String var2[] = varString.split("\n");
		for (int x = 0; x < var2.length; x++) {
			String[] xs = var2[x].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			varValues.add(xs);
		}

		// String varValueString = variablesVSC[1];
		//String varValueString = variableCSV.getVariableValueLables();
		for (int i = 0; i < vars.size(); i++) {
			if (i == 0)
				continue;
			//TODO: Q - what does this do?
			/*
			if (i == 127)
				i = i;
			*/

			String[] var = vars.get(i);
			String[] varValue = varValues.get(i - 1);
			CodebookVariable cv = new CodebookVariable();

			switch (var.length) {
				case 1:
					cv.setName(var[0]);
					break;
				case 2:
					cv.setName(var[0]);
					cv.setLabel(var[1]);
					break;
				case 3:
					cv.setName(var[0]);
					cv.setLabel(var[1]);
					cv.setValidCount(var[2]);
					break;
				case 4:
					cv.setName(var[0]);
					cv.setLabel(var[1]);
					cv.setValidCount(var[2]);
					cv.setInvalidCount(var[3]);
					break;
				case 5:
					cv.setName(var[0]);
					cv.setLabel(var[1]);
					cv.setValidCount(var[2]);
					cv.setInvalidCount(var[3]);
					cv.setMinValue(var[4]);
					break;
				case 6:
					cv.setName(var[0]);
					cv.setLabel(var[1]);
					cv.setValidCount(var[2]);
					cv.setInvalidCount(var[3]);
					cv.setMinValue(var[4]);
					cv.setMaxValue(var[5]);
					break;
				case 7:
					cv.setName(var[0]);
					cv.setLabel(var[1]);
					cv.setValidCount(var[2]);
					cv.setInvalidCount(var[3]);
					cv.setMinValue(var[4]);
					cv.setMaxValue(var[5]);
					cv.setMean(var[6]);
					break;
				case 8:
					cv.setName(var[0]);
					cv.setLabel(var[1]);
					cv.setValidCount(var[2]);
					cv.setInvalidCount(var[3]);
					cv.setMinValue(var[4]);
					cv.setMaxValue(var[5]);
					cv.setMean(var[6]);
					cv.setStdDeviation(var[7]);
					break;
				default:
					break;

			}
			for (int j = 0; j < varValue.length; j++) {
				cv.getVariableCodes().add(varValue[j]);
			}
			codebookVariables.add(cv);
		}
		return codebookVariables;
	}
	
	public String domToString(Document doc){
		DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
	    LSSerializer serializer = domImplementation.createLSSerializer();
	    serializer.getDomConfig().setParameter("format-pretty-print", true);
	    serializer.setNewLine("\r\n");

		LSOutput lsOutput = domImplementation.createLSOutput();
		lsOutput.setEncoding("UTF-8");
		Writer stringWriter = new StringWriter();
		lsOutput.setCharacterStream(stringWriter);
		serializer.write(doc, lsOutput);

		return stringWriter.toString();
	}

	public String domToString(Document doc, String encoding){
		DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplementation.createLSSerializer();
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.setNewLine("\r\n");

		LSOutput lsOutput = domImplementation.createLSOutput();
		lsOutput.setEncoding(encoding);
		Writer stringWriter = new StringWriter();
		lsOutput.setCharacterStream(stringWriter);
		serializer.write(doc, lsOutput);

		return stringWriter.toString();
	}
}