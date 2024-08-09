package edu.cornell.ncrn.ced2ar.ddigen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataFileIdentification implements Appendable {

	public static final String ATTRIBUTE_NAME_IS_PUBLIC = "isPublic";
	public static final String NODE_NAME_DATA_FILE_IDENTIFICATION = "DataFileIdentification";
	public static final String NODE_NAME_DATA_FILE_URI = "DataFileURI";

	private String dataFileUri;
	private String prefix;
	private String isPublic;

	public DataFileIdentification(String dataFileUri) {
		this.dataFileUri = dataFileUri;
	}

	public DataFileIdentification(String dataFileUri, String prefix, String isPublic) {
		this(dataFileUri);
		this.prefix = prefix;
		this.isPublic = isPublic;
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

		if (isPublic != null && !isPublic.isEmpty()) {
			dataFileUriElement.setAttribute(ATTRIBUTE_NAME_IS_PUBLIC, isPublic);
		}
		fragment.appendChild(dataFileUriElement);

		parent.appendChild(fragment);
	}
}