package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FragmentInstanceGenerator {

	public static final String ATTRIBUTE_NAME_NAMESPACE_DDI = "xmlns:ddi";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_DDI = "ddi:instance:3_3";
	public static final String NODE_NAME_FRAGMENT_INSTANCE = "ddi:FragmentInstance";

	private List<Fragment> fragmentList;

	public FragmentInstanceGenerator(List<Fragment> fragmentList) {
		setFragmentList(fragmentList);
	}

	public void setFragmentList(List<Fragment> fragmentList) {
		this.fragmentList = fragmentList;
	}

	public Document toDocument() throws ParserConfigurationException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		Document doc = domBuilder.newDocument();

		Element fragmentInstance = doc.createElement(NODE_NAME_FRAGMENT_INSTANCE);
		fragmentInstance.setAttribute(Fragment.ATTRIBUTE_NAME_NAMESPACE_R, Fragment.ATTRIBUTE_VALUE_NAMESPACE_R);
		fragmentInstance.setAttribute(ATTRIBUTE_NAME_NAMESPACE_DDI, ATTRIBUTE_VALUE_NAMESPACE_DDI);

		doc.appendChild(fragmentInstance);
		doc.getDocumentElement().normalize();

		for (Fragment fragment : fragmentList) {
			fragment.appendToElement(fragmentInstance, doc);
		}
		return doc;
	}
}