package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Description;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Software implements Appendable {

	public static final String NODE_NAME_SYSTEM_SOFTWARE = "Software";
	public static final String NODE_NAME_SYSTEM_NAME = "r:SoftwareName";

	private final StringElement softwareName;
	private final Description description;
	private final String prefix;
	private final SoftwareType softwareType;

	public Software(StringElement softwareName, String description, String prefix, SoftwareType softwareType) {
		this.softwareName = softwareName;
		this.description = new Description(description);
		this.prefix = prefix;
		this.softwareType = softwareType;
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element systemSoftware = doc.createElement(prefix + ":" + softwareType.toString() + NODE_NAME_SYSTEM_SOFTWARE);

		Element systemName = doc.createElement(NODE_NAME_SYSTEM_NAME);
		softwareName.appendToElement(systemName, doc);
		systemSoftware.appendChild(systemName);

		// Description
		description.appendToElement(systemSoftware, doc);

		parent.appendChild(systemSoftware);
	}
}
