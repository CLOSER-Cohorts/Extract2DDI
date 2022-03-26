package edu.cornell.ncrn.ced2ar.ddigen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class FileUtil {

	public static final String RESOURCE_FILE_PATH = "edu/cornell/ncrn/ced2ar/ddigen/ddi/fragment/application.properties";

	public static void createFile(String csv, String fileName) throws IOException {
		BufferedWriter bw = null;
		try {
			File varsFile = new File(fileName);
			varsFile.createNewFile();
			FileWriter fw = new FileWriter(varsFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(csv);
		} finally {
			bw.close();
		}
	}

	public static File getFileFromResource(Class cl, String fileName) throws URISyntaxException {
		ClassLoader classLoader = cl.getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return new File(resource.toURI());
		}
	}

	public static InputStream getInputStreamFromResource(Class cl, String fileName) {
		ClassLoader classLoader = cl.getClassLoader();
		InputStream inputStream =  classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}

	public static Properties getPropertiesFromResource(Class cl) {
		InputStream stream = getInputStreamFromResource(cl, RESOURCE_FILE_PATH);
		Properties properties = new Properties();
		try
		{
			if (stream != null) {
				properties.load(stream);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}

	public static Properties getPropertiesFromFile(String fileName) {
		Properties properties = new Properties();
		try {
			InputStream stream = new FileInputStream(fileName);
			if (stream != null) {
				properties.load(stream);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}
}