package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;

public class DDI {
	private String xml;
	private VariableCsv variableCsv;

	public DDI(String xml, VariableCsv variableCsv) {
		setVariableCsv(variableCsv);
		setXml(xml);
	}

	public String getXml() {
		return xml;
	}

	public VariableCsv getVariableCsv() {
		return variableCsv;
	}

	public void setVariableCsv(VariableCsv variableCsv) {
		this.variableCsv = variableCsv;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
}
