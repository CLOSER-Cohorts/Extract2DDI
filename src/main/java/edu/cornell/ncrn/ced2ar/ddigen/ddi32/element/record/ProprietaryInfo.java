package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class ProprietaryInfo implements Appendable {

	public static final String NODE_NAME_PROPRIETARY_INFO = "r:ProprietaryInfo";

	private List<ProprietaryProperty> proprietaryPropertyList = new ArrayList<>();

	public void addProprietaryProperty(String key, String value) {
		synchronized (proprietaryPropertyList) {
			ProprietaryProperty proprietaryProperty = new ProprietaryProperty(key, value);
			proprietaryPropertyList.add(proprietaryProperty);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element proprietaryInfo = doc.createElement(NODE_NAME_PROPRIETARY_INFO);

		for (ProprietaryProperty proprietaryProperty : getProprietaryPropertyList()) {
			proprietaryProperty.appendToElement(proprietaryInfo, doc);
		}

		element.appendChild(proprietaryInfo);
	}

	public List<ProprietaryProperty> getProprietaryPropertyList() {
		return proprietaryPropertyList;
	}
}
