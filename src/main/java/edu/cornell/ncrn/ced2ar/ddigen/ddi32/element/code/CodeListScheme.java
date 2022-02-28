package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class CodeListScheme extends ElementWithUrn {

	public static final String NODE_NAME_CODE_LIST_SCHEME = "ddi:CodeListScheme";

	private List<CodeListElement> codeListList = new ArrayList<>();

	public CodeListScheme(String agency) {
		super(agency);
	}

	public void addCodeList(CodeListElement codeList) {
		synchronized (codeListList) {
			codeListList.add(codeList);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element codeListScheme = doc.createElement(NODE_NAME_CODE_LIST_SCHEME);

		super.appendToElement(codeListScheme, doc);

		// Code Lists
		for (CodeListElement codeList : getCodeListList()) {
			codeList.appendToElement(codeListScheme, doc);
		}

		element.appendChild(codeListScheme);
	}

	public List<CodeListElement> getCodeListList() {
		return codeListList;
	}
}
