package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategorySchemeReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableSchemeReferenceFragment;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ResourcePackageFragment extends Fragment {

	public static final String NODE_NAME_CODE = "Code";
	public static final String NODE_NAME_VALUE = "r:Value";

	private String value;
	private List<PhysicalInstanceReferenceFragment> physicalInstanceReferenceFragmentList;
	private List<CategorySchemeReferenceFragment> categorySchemeReferenceFragmentList;
	private List<VariableSchemeReferenceFragment> variableSchemeReferenceFragmentList;

	public ResourcePackageFragment(String id, String agency, int version, String value) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element code = doc.createElementNS(namespace, NODE_NAME_CODE);
		super.appendToElement(code, doc, namespace);

		element.appendChild(code);
	}
}