package se.treehou.ng.ohcommunicator.connector.models;

import java.util.Map;

public class OHInboxItem {

    public static final String FLAG_NEW = "NEW";
    public static final String FLAG_IGNORED = "IGNORED";

    private String flag;
    private String label;
    private Map<String, String> properties;
    private String thingUID;

    public OHInboxItem() {}

    /**
     * Set flags for inbox item.
     * @param flag inbox item flags.
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * Additional flags for inbox item.
     * @return flags for inbox item.
     */
    public String getFlag() {
        return flag;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Set inbox label.
     * @param label the label to display label as.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get label of inbox item.
     * @return label of inbox item.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Check if user has set to ignore inbox item.
     * @return true if user ignored thing, else false.
     */
    public boolean isIgnored(){
        return FLAG_IGNORED.equals(flag);
    }

    /**
     * Get inbox properties.
     * @return inbox properties.
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Set thing id.
     * @param thingUID id of thing.
     */
    public void setThingUID(String thingUID) {
        this.thingUID = thingUID;
    }

    /**
     * Get the id of thing.
     *
     * @return thing id.
     */
    public String getThingUID() {
        return thingUID;
    }
}