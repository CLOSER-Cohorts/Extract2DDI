package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Label;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeListFragment extends FragmentWithUrn {
	public static final String NODE_NAME_CODE_LIST = "CodeList";

	private List<CodeFragment> codeFragmentList = new ArrayList<>();
	private Label label;

	public CodeListFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element codeList = doc.createElementNS(namespace, NODE_NAME_CODE_LIST);

		super.appendToElement(codeList, doc, namespace);

		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		fragment.appendChild(codeList);
		element.appendChild(fragment);

		if (getLabel() != null) {
			getLabel().appendToElement(codeList, doc, namespace);
		}

		for (CodeFragment variable : getCodeFragmentList()) {
			variable.appendToElement(codeList, doc, namespace);
		}
	}

	public void addCode(CodeFragment code) {
		this.codeFragmentList.add(code);
	}

	public List<CodeFragment> getCodeFragmentList() {
		return codeFragmentList;
	}

	public Label getLabel() {
		return label;
	}

	public void setCodeFragmentList(List<CodeFragment> codeFragmentList) {
		this.codeFragmentList = codeFragmentList;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
}
