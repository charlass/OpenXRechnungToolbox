package org.oxt.toolbox.cli;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class CLIJob {

    /**
     * Processes a given jobFile
     * @param jobFilePath  Path to the xml jobfile, which contains the {Tasks}
     * @param config  Path to the App configuration file
     * @throws Exception Unspecific exception
     */
    public void processJobFile(String jobFilePath, String config) throws Exception {

        System.out.println("Job started...");

        // Load the task XML file
        JAXBContext jaxbContext = JAXBContext.newInstance(Tasks.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Tasks tasks = (Tasks) jaxbUnmarshaller.unmarshal(new File(jobFilePath));

        CLIValidation val = new CLIValidation();
        CLIVisualization viz = new CLIVisualization();

        // Process each task
        Task[] taskArray = tasks.getTasks();
        System.out.println("Processing " + taskArray.length + " tasks...");
        for (int i = 0; i < taskArray.length; i++) {
            Task task = taskArray[i];
            System.out.println("Processing task " + (i + 1) + "/" + taskArray.length + ": " + task.getInput());

            if (task.getValidate() != null) {
                System.out.println("Processing task " + (i + 1) + "/" + taskArray.length + ": validating...");
                val.cliValidation(task.getInput(), task.getValidate(), task.getValiversion(), config);
                System.out.println("Processing task " + (i + 1) + "/" + taskArray.length + ": created validation: " + task.getValidate());
            }
            if (task.getVisualize() != null) {
                System.out.println("Processing task " + (i + 1) + "/" + taskArray.length + ": visualizing...");
                viz.cliVisualization(task.getInput(), task.getVisualize(), false, config);
                System.out.println("Processing task " + (i + 1) + "/" + taskArray.length + ": created visualization: " + task.getVisualize());
            }
        }

        System.out.println("Job finished successfully.");
    }

}

