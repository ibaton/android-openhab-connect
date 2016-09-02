package se.treehou.ng.ohcommunicator.connector.models;

public class OHStateDescription {

    private String pattern = "";
    private boolean readOnly = false;

    /**
     * Create a description of item state.
     */
    public OHStateDescription() {
    }

    /**
     * Create a description of item state.
     *
     * @param pattern pattern for value.
     * @param readOnly true if state is read only, else false.
     */
    public OHStateDescription(String pattern, boolean readOnly) {
        this.pattern = pattern;
        this.readOnly = readOnly;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Set if the value can be written to.
     * State of item cant be modified if read only.
     *
     * @return true if state is read only, else false.
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Set if the value can be written to.
     * State of item cant be modified if read only.
     *
     * @param readOnly true if read only, else false.
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
