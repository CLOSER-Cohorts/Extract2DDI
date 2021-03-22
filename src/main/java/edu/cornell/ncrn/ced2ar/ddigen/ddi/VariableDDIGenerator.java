package edu.cornell.ncrn.ced2ar.ddigen.ddi;

import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class VariableDDIGenerator {
	
	public String domToString(Document doc){
		DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
	    LSSerializer serializer = domImplementation.createLSSerializer();
	    serializer.getDomConfig().setParameter("format-pretty-print", true);
	    serializer.setNewLine("\r\n");

		LSOutput lsOutput = domImplementation.createLSOutput();
		lsOutput.setEncoding("UTF-8");
		Writer stringWriter = new StringWriter();
		lsOutput.setCharacterStream(stringWriter);
		serializer.write(doc, lsOutput);

		return stringWriter.toString();
	}

	public String domToString(Document doc, String encoding){
		DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplementation.createLSSerializer();
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.setNewLine("\r\n");

		LSOutput lsOutput = domImplementation.createLSOutput();
		lsOutput.setEncoding(encoding);
		Writer stringWriter = new StringWriter();
		lsOutput.setCharacterStream(stringWriter);
		serializer.write(doc, lsOutput);

		return stringWriter.toString();
	}
}