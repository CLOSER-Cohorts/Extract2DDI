package edu.cornell.ncrn.ced2ar.ddigen;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator.ATTRIBUTE_VALUE_NAMESPACE_DDI;
import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator.ATTRIBUTE_VALUE_NAMESPACE_DDI1;
import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator.ATTRIBUTE_VALUE_NAMESPACE_G;
import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator.ATTRIBUTE_VALUE_NAMESPACE_P;
import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator.ATTRIBUTE_VALUE_NAMESPACE_PI;
import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator.ATTRIBUTE_VALUE_NAMESPACE_R;
import static edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator.ATTRIBUTE_VALUE_NAMESPACE_XMLNS;

public class Ddi32NamespaceContext implements NamespaceContext {

	@Override
	public String getNamespaceURI(String prefix) {
		if ("ddi".equals(prefix)) {
			return ATTRIBUTE_VALUE_NAMESPACE_DDI;
		} else if("g".equals(prefix)) {
			return ATTRIBUTE_VALUE_NAMESPACE_G;
		} else if("p".equals(prefix)) {
			return ATTRIBUTE_VALUE_NAMESPACE_P;
		} else if("ddi1".equals(prefix)) {
			return ATTRIBUTE_VALUE_NAMESPACE_DDI1;
		} else if("pi".equals(prefix)) {
			return ATTRIBUTE_VALUE_NAMESPACE_PI;
		} else if("r".equals(prefix)) {
			return ATTRIBUTE_VALUE_NAMESPACE_R;
		} else if("ns".equals(prefix)) {
			return ATTRIBUTE_VALUE_NAMESPACE_XMLNS;
		}
		return XMLConstants.NULL_NS_URI;
	}

	@Override
	public String getPrefix(String namespaceURI) {
		if (ATTRIBUTE_VALUE_NAMESPACE_DDI.equals(namespaceURI)) {
			return "ddi";
		} else if (ATTRIBUTE_VALUE_NAMESPACE_G.equals(namespaceURI)) {
			return "g";
		} else if (ATTRIBUTE_VALUE_NAMESPACE_P.equals(namespaceURI)) {
			return "p";
		} else if (ATTRIBUTE_VALUE_NAMESPACE_DDI1.equals(namespaceURI)) {
			return "ddi1";
		} else if (ATTRIBUTE_VALUE_NAMESPACE_PI.equals(namespaceURI)) {
			return "pi";
		} else if (ATTRIBUTE_VALUE_NAMESPACE_R.equals(namespaceURI)) {
			return "r";
		} else if (ATTRIBUTE_VALUE_NAMESPACE_XMLNS.equals(namespaceURI)) {
			return "ns";
		}
		return null;
	}

	@Override
	public Iterator<String> getPrefixes(String namespaceURI) {
		return null;
	}
};
