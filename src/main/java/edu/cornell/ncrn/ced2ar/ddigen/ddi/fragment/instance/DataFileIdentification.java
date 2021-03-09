package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.instance;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataFileIdentification implements Appendable {

	public static final String NODE_NAME_DATA_FILE_IDENTIFICATION = "DataFileIdentification";
	public static final String NODE_NAME_DATA_FILE_URI = "DataFileURI";

	private String dataFileUri;

	public DataFileIdentification(String dataFileUri) {
		setDataFileUri(dataFileUri);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_DATA_FILE_IDENTIFICATION);

		Element dataFileUri = doc.createElementNS(namespace, NODE_NAME_DATA_FILE_URI);
		dataFileUri.setTextContent(getDataFileUri());
		fragment.appendChild(dataFileUri);

		element.appendChild(fragment);
	}

	public String getDataFileUri() {
		return dataFileUri;
	}

	public void setDataFileUri(String dataFileUri) {
		this.dataFileUri = dataFileUri;
	}
}