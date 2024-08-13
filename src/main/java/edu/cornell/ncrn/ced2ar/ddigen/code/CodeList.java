package edu.cornell.ncrn.ced2ar.ddigen.code;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CodeList {
	private List<Code> codeList = new ArrayList<>();
	private String id;
	private String label;
	private final String uuid;

	public CodeList() {
		this.uuid = UUID.randomUUID().toString();
	}

	public CodeList(String id, List<Code> codeList) {
		this();
		setId(id);
		setCodeList(codeList);
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

	public String getUuid() {
		return uuid;
	}

	public Code findCodeById(String id) {
		for (Code code : codeList) {
			if (code.getCategoryId().equals(id)) {
				return code;
			}
		}
		return null;
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
