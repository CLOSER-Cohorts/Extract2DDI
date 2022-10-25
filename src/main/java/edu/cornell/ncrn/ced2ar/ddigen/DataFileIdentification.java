package edu.cornell.ncrn.ced2ar.ddigen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataFileIdentification implements Appendable {

	public static final String NODE_NAME_DATA_FILE_IDENTIFICATION = "DataFileIdentification";
	public static final String NODE_NAME_DATA_FILE_URI = "DataFileURI";

	private String dataFileUri;
	private String prefix;

	public DataFileIdentification(String dataFileUri) {
		this.dataFileUri = dataFileUri;
	}

	public DataFileIdentification(String dataFileUri, String prefix) {
		this(dataFileUri);
		this.prefix = prefix;
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element fragment;
		Element dataFileUriElement;
		if (prefix != null) {
			fragment = doc.createElement(prefix + ":" + NODE_NAME_DATA_FILE_IDENTIFICATION);
			dataFileUriElement = doc.createElement(prefix + ":" + NODE_NAME_DATA_FILE_URI);
		} else {
			fragment = doc.createElement(NODE_NAME_DATA_FILE_IDENTIFICATION);
			dataFileUriElement = doc.createElement(NODE_NAME_DATA_FILE_URI);
		}

		dataFileUriElement.setTextContent(dataFileUri);
		fragment.appendChild(dataFileUriElement);

		parent.appendChild(fragment);
	}
}