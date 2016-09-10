package se.treehou.ng.ohcommunicator.connector.models;

public class OHLink {

    private String channelUID;
    private String itemName;

    public OHLink() {
    }

    public String getChannelUID() {
        return channelUID;
    }

    public void setChannelUID(String channelUID) {
        this.channelUID = channelUID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OHLink ohLink = (OHLink) o;

        if (channelUID != null ? !channelUID.equals(ohLink.channelUID) : ohLink.channelUID != null)
            return false;
        return itemName != null ? itemName.equals(ohLink.itemName) : ohLink.itemName == null;

    }

    @Override
    public int hashCode() {
        int result = channelUID != null ? channelUID.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        return result;
    }
}
