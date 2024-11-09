package org.oxt.toolbox.cli;

import java.io.File;
import java.io.FilenameFilter;

import org.oxt.toolbox.helpers.AppProperties;
import org.oxt.toolbox.visualization.VisualizerImpl;

/**
 * Class implementing the visualization processing from command line (implements the ICLIVisualization interface).
 * @author Dr. Jan C. Thiele
 * @since OpenXRechnungToolbox v3.0.0 
 */
public class CLIVisualization implements ICLIVisualization {

	/**
	 * Constructor
	 */
	public CLIVisualization() {
		
	}
	
	/**
	 * Method to run visualization from command line.
	 * @param inputInvoice Path to invoice file to be visualized
	 * @param outputViz Path where the output visualization file should be written to (pdf or html)
	 * @param pdf Generate a pdf visualization (if missing, a html visualization will be generated)
	 * @param config Path to the App configuration file
	 * @throws Exception Unspecific exception
	 */
	public void cliVisualization(String inputInvoice, String outputViz, boolean pdf, String config) throws Exception {

		System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize","true");		
		AppProperties.initializeProperties(config);
		File inputFile = new File(inputInvoice);
		if (!inputFile.exists()) {
			throw new Exception("inputInvoice not found at: "+inputInvoice);
		}
		VisualizerImpl viz = new VisualizerImpl();			
		viz.runVisualization(inputFile.getAbsolutePath(), AppProperties.prop.getProperty("viz.intermediate.ubl.xsl"), AppProperties.prop.getProperty("viz.intermediate.ublcn.xsl"), AppProperties.prop.getProperty("viz.intermediate.cii.xsl"), AppProperties.prop.getProperty("viz.html.xsl"));

		File outputFile = new File(outputViz);
		if (outputFile.exists()) {
			outputFile.delete();
		}
		
		if (pdf) {				
			viz.transformAndSaveToPDF(AppProperties.prop.getProperty("viz.pdf.xsl"), outputViz);						
		} else {
			viz.saveAs(outputViz);			
		}
		System.out.println("Visualization processing finished successfully.");								
	}
	

	/**
	 * Method to run visualization from command line.
	 * @param inputFolder Path of a folder, all .xml files will to be visualized
	 * @param outputFolder Path of a folder where the visualized file (html) should be written, the output filename will be {{filename}}.visualization.{{html|pdf}}
	 * @param pdf Generate a pdf visualization (if missing, a html visualization will be generated)
	 * @param config Path to the App configuration file
	 * @throws Exception Unspecific exception
	 */
	public void cliVisualizationFolder(String inputFolder, String outputFolder, boolean pdf, String config) throws Exception {

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

		File[] inputFiles = inputDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		if (inputFiles == null) {
			throw new Exception("Failed to read the content of the inputFolder: " + inputFolder);
		}

		System.out.println("Processing " + inputFiles.length + " files...");
		for (int i = 0; i < inputFiles.length; i++) {

			File inputFile = inputFiles[i];
			System.out.println("Processing file " + (i+1) + "/" + inputFiles.length + ": " + inputFile.getAbsolutePath());

			String outputViz = outputDir.getAbsolutePath() + File.separatorChar + inputFile.getName().replace(".xml", ".visualization.html");

			VisualizerImpl viz = new VisualizerImpl();
			viz.runVisualization(inputFile.getAbsolutePath(), AppProperties.prop.getProperty("viz.intermediate.ubl.xsl"), AppProperties.prop.getProperty("viz.intermediate.ublcn.xsl"), AppProperties.prop.getProperty("viz.intermediate.cii.xsl"), AppProperties.prop.getProperty("viz.html.xsl"));

			File outputFile = new File(outputViz);
			if (outputFile.exists()) {
				outputFile.delete();
			}

			if (pdf) {
				viz.transformAndSaveToPDF(AppProperties.prop.getProperty("viz.pdf.xsl"), outputViz);
			} else {
				viz.saveAs(outputViz);
			}
			System.out.println("Visualized file " + (i+1) + "/" + inputFiles.length + ": " + outputFile.getAbsolutePath());

		}

		System.out.println("Visualization processing finished successfully.");
	}


}
