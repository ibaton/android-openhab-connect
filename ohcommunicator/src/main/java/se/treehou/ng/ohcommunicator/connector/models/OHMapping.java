package se.treehou.ng.ohcommunicator.connector.models;

public class OHMapping {

    private String command;
    private String label;

    public OHMapping() {
    }

    /**
     * Get command to trigger when state is activated.
     * @return command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Set the command to trigger when state is activated.
     * @param command the command to send to server.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * The label to map command to.
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label to map the command to
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
