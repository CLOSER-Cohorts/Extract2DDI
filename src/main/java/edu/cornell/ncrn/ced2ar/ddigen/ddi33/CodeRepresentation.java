package edu.cornell.ncrn.ced2ar.ddigen.ddi33;

public class CodeRepresentation extends Representation {
	private String codeSchemeId;

	public CodeRepresentation(String codeSchemeId) {
		setCodeSchemeId(codeSchemeId);
	}

	public String getCodeSchemeId() {
		return codeSchemeId;
	}

	public void setCodeSchemeId(String codeSchemeId) {
		this.codeSchemeId = codeSchemeId;
	}
}