package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.util.HashMap;

public class Invalid118FormatTest {

	private static final Logger logger = Logger.getLogger(Invalid118FormatTest.class);
	private static final boolean IS_SUMMARY_STATISTICS_ENABLED = false;
	private static final long RECORD_LIMIT = 100;
	private final String dataFileName = "test-file-data-types-118-new.dta";

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidValueLabels() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, dataFileName);

		GenerateDDI33 generateDDI33 = new GenerateDDI33("uk.closer", "en-GB", new HashMap<>(),"max,min,valid,invalid,freq,stdev");

		// Act
		DDI ddi = generateDDI33.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		Assert.assertTrue(false);
	}
}