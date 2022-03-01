package edu.cornell.ncrn.ced2ar.ddigen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Appendable {

	public static final String ATTRIBUTE_NAME_VERSION_DATE = "versionDate";

	public void appendToElement(Element element, Document doc);
}