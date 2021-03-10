package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Appendable {

	public void appendToElement(Element element, Document doc);
}