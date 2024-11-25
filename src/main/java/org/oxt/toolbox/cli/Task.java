package org.oxt.toolbox.cli;

/**
 * Describe a task of a jobfile.
 * "input" is the path to the input invoice xml file, required.
 * "validate" is the path to the validation output file, optional, leave empty if validation is not required.
 * "visualize" is the path to the visualization output file, optional, leave empty if visualization is not required.
 * "valiversion" is the version against which should be validated, required for validation.
 */
public class Task {
    private String input;
    private String validate;
    private String visualize;
    private String valiversion;

    /**
     * Returns the path of the input invoice file.
     * @return A file path
     */
    public String getInput() {
        return input != null ? input.trim() : null;
    }

    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Returns the path of the output validation file.
     * If empty then the file will not be validated.
     * @return A file path
     */
    public String getValidate() {
        return validate != null ? validate.trim() : null;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getVisualize() {
        return visualize != null ? visualize.trim() : null;
    }

    /**
     * Returns the path of the output visualization file.
     * If empty then the file will not be visualized.
     * @return A file path
     */
    public void setVisualize(String visualize) {
        this.visualize = visualize;
    }

    public String getValiversion() {
        return valiversion != null ? valiversion.trim() : null;
    }

    /**
     * Version against which should be validated (must be a valid version number from the list of versions [available_valiVersions] in the App configuration file)
     * @return ValiVersion
     */
    public void setValiversion(String valiversion) {
        this.valiversion = valiversion;
    }
}
