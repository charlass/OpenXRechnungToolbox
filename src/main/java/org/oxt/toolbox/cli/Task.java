package org.oxt.toolbox.cli;

/**
 * Describe a task of a jobfile.
 * "input" is the path to the input invoice xml file, required.
 * "validate" is the path to the validation output file, optional, leave empty if validation is not required.
 * "visualize" is the path to the visualization output file, optional, leave empty if visualization is not required.
 * "valiversion" is the version against which should be validated, required for validation.
 */
@SuppressWarnings("LombokSetterMayBeUsed")
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
     * Returns the path of the validation output file.
     * Same as the "-val -input {{path}}" cli parameter.
     * May be empty, in this case the input file will not be validated.
     * @return A file path
     */
    public String getValidate() {
        return validate != null ? validate.trim() : null;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    /**
     * Returns the path of the visualization output file.
     * Same as the "-viz --i {{path}}" cli parameter.
     * May be empty, in this case the input file will not be visualized.
     * @return A file path
     */
    public String getVisualize() {
        return visualize != null ? visualize.trim() : null;
    }

    @SuppressWarnings("unused")
    public void setVisualize(String visualize) {
        this.visualize = visualize;
    }

    /**
     * Version against which should be validated.
     * This must be a valid version number from the list of versions [available_valiVersions] in the App configuration file.
     * If empty then the version "3.0.2" will be used.
     * @return An X-Rechnung version
     */
    public String getValiversion() {
        return valiversion != null ? valiversion.trim() : "3.0.2";
    }

    public void setValiversion(String valiversion) {
        this.valiversion = valiversion;
    }
}
