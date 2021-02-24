package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

public class FragmentUtil {

	public static String generateUrn(String id, String agency, int version) {
		return "urn:ddi:" + agency + ":" + id + ":" + version;
	}
}