package se.treehou.ng.ohcommunicator.connector.models;

public class OHStateDescription {

    private String pattern = "";
    private boolean readOnly = false;

    public OHStateDescription() {
    }

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

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
