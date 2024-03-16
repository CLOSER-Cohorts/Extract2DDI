package edu.cornell.ncrn.ced2ar.ddigen.code;

import java.util.ArrayList;
import java.util.List;

public class CodeList {

	private List<Code> codeList = new ArrayList<>();
	private String id;
	private String label;

	public CodeList() {
	}

	public CodeList(String id) {
		this.id = id;
	}

	public List<Code> getCodeList() {
		return codeList;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
