package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.ArrayList;
import java.util.List;

public class CodeList {

	private List<Code> codeList = new ArrayList<>();
	private String label;

	public List<Code> getCodeList() {
		return codeList;
	}

	public String getLabel() {
		return label;
	}

	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
