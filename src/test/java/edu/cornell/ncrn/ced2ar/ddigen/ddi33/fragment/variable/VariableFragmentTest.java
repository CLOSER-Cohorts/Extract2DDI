package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Label;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Arrays;

public class VariableFragmentTest {
	@Test
	public void testToDocument() {
		VariableFragment variableFragment = new VariableFragment("id", "agency", 1);
		variableFragment.setName(new StringElement("name", "ddi-lang"));
		variableFragment.setLabel(new Label("name", "ddi-lang"));
		FragmentInstanceGenerator generator = new FragmentInstanceGenerator(Arrays.asList(variableFragment));
		try {
			Document fragmentInstanceDocument = generator.toDocument();
			Node fragmentInstance = fragmentInstanceDocument.getFirstChild();
			Node fragment = fragmentInstance.getFirstChild();
			Node variable = fragment.getFirstChild();
			Assert.assertEquals("VariableName", variable.getChildNodes().item(4).getNodeName());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
