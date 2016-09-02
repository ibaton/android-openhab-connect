package se.treehou.ng.ohcommunicator.connector.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OHThingType {

    private List<OHChannel> channels = new ArrayList<>();
    private String description;
    private String label;

    @SerializedName("UID")
    private String uID;

    private boolean bridge;

    /**
     * Get label of thing.
     * @return thing label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the description of thing.
     * @return thing description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get UID of thing.
     * @return thing uid.
     */
    public String getUID() {
        return uID;
    }

    /**
     * Get channels of thing.
     * @return channels for thing.
     */
    public List<OHChannel> getChannels() {
        return channels;
    }

    /**
     * Check if thing is of type bridge.
     *
     * @return true if bridge, else false.
     */
    public boolean isBridge() {
        return bridge;
    }
}
