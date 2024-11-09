package org.oxt.toolbox.cli;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import org.oxt.toolbox.helpers.AppProperties;
import org.oxt.toolbox.validation.ValidatorImpl;

/**
 * Class implementing the validation processing from command line (implements the ICLIValidation interface).
 * @author Dr. Jan C. Thiele
 * @since OpenXRechnungToolbox v3.0.0 
 */
public class CLIValidation implements ICLIValidation {

	/**
	 * Constructor
	 */
	public CLIValidation() {
		
	}
	
	/**
	 * Method to run the validation from command line.
	 * @param inputInvoice Path of invoice file to be validated
	 * @param outputReport Path where the report file (html) should be written
	 * @param valiVersion Version against which should be validated (must be a valid version number from the list of versions [available_valiVersions] in the App configuration file)
	 * @param config Path to the App configuration file
	 * @throws Exception Unspecific exception
	 */
	public void cliValidation(String inputInvoice, String outputReport, String valiVersion, String config) throws Exception {
		
		System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize","true");		
		AppProperties.initializeProperties(config);
		
		File inputFile = new File(inputInvoice);
		if (!inputFile.exists()) {
			throw new Exception("inputInvoice not found at: "+inputInvoice);
		}
		String valiVersions = AppProperties.prop.getProperty("available_valiVersions");
		List<String> valiVersionsList = Arrays.asList(valiVersions.split(","));			
		if (valiVersionsList.contains(valiVersion)) {				
			ValidatorImpl vali = new ValidatorImpl();
			vali.runValidation(inputFile.getAbsolutePath(), valiVersion);
			File outputFile = new File(outputReport);
			if (outputFile.exists()) {
				outputFile.delete();
			}			
			vali.saveAs(outputReport);							
		} else {
			throw new Exception("Unknown valiVersion: "+valiVersion);
		}		
		System.out.println("Validation processing finished successfully.");						
	}

	/**
	 * Method to run the validation from command line.
	 * @param inputFolder Path of a folder, all .xml files will be validated as an x-rechnung
	 * @param outputFolder Path of a folder where the report file (html) should be written, the output filename will be {{filename}}.report.html
	 * @param valiVersion Version against which should be validated (must be a valid version number from the list of versions [available_valiVersions] in the App configuration file)
	 * @param config Path to the App configuration file
	 * @throws Exception Unspecific exception
	 */
	public void cliValidationFolder(String inputFolder, String outputFolder, String valiVersion, String config) throws Exception {

		System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize","true");
		AppProperties.initializeProperties(config);

		File inputDir = new File(inputFolder);
		if (!inputDir.exists()) {
			throw new Exception("inputFolder not found at: "+inputFolder);
		}

		File outputDir = new File(outputFolder);
		if (!outputDir.exists()) {
			throw new Exception("outputFolder not found at: "+outputFolder);
		}

		String valiVersions = AppProperties.prop.getProperty("available_valiVersions");
		List<String> valiVersionsList = Arrays.asList(valiVersions.split(","));
        if (!valiVersionsList.contains(valiVersion)) {
			throw new Exception("Unknown valiVersion: " + valiVersion);
		}

		File[] inputFiles = inputDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		if (inputFiles == null) {
            throw new Exception("Failed to read the content of the inputFolder: " + inputFolder);
        }

		System.out.println("Loading the Kosit Validator...");
		ValidatorImpl vali = new ValidatorImpl();
		vali.loadConfiguration(valiVersion);

		System.out.println("Processing " + inputFiles.length + " files...");
		for (int i = 0; i < inputFiles.length; i++) {

			File inputFile = inputFiles[i];
			System.out.println("Processing file " + (i+1) + "/" + inputFiles.length + ": " + inputFile.getAbsolutePath());

			String outputReport = outputDir.getAbsolutePath() + '\\' + inputFile.getName().replace(".xml", ".report.html");

			vali.runValidationForValiVersion(inputFile.getAbsolutePath(), valiVersion);
			File outputFile = new File(outputReport);
			if (outputFile.exists()) {
				outputFile.delete();
			}
			vali.saveAs(outputReport);
			System.out.println("Created report  " + (i+1) + "/" + inputFiles.length + ": " + outputFile.getAbsolutePath());
		}

        System.out.println("Validation processing finished successfully.");
	}
}
