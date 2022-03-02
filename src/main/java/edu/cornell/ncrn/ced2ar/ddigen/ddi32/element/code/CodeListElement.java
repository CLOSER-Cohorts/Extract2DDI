package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Name;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class CodeListElement extends ElementWithUrn {

	public static final String NODE_NAME_CODE_LIST = "ddi:CodeList";
	public static final String NODE_NAME_CODE_LIST_NAME = "ddi:CodeListName";

	private List<CodeElement> codeElementList = new ArrayList<>();
	private Name name;

	public CodeListElement(String id, String agency) {
		super(id, agency);
	}

	public void addCode(CodeElement codeElement) {
		synchronized (codeElementList) {
			codeElementList.add(codeElement);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element codeList = doc.createElement(NODE_NAME_CODE_LIST);

		super.appendToElement(codeList, doc);

		// Code List Name
		getName().appendToElement(codeList, doc);

		for (CodeElement codeElement : getCodeElementList()) {
			codeElement.appendToElement(codeList, doc);
		}

		element.appendChild(codeList);
	}

	public List<CodeElement> getCodeElementList() {
		return codeElementList;
	}

	public Name getName() {
		return name;
	}

	public void setName(String name) {
		this.name = new Name(NODE_NAME_CODE_LIST_NAME, name);
	}
}
