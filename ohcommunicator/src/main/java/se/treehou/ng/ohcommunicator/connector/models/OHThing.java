package se.treehou.ng.ohcommunicator.connector.models;


import java.util.List;
import java.util.Map;

public class OHThing {

    private OHStatusInfo statusInfo;
    private String label;
    private String bridgeUID;
    //private Map<String, String> configuration;
    //private Map<String, String> properties;
    private String UID;
    private String thingTypeUID;
    //private List<OHChannel> channels;

    public OHStatusInfo getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(OHStatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBridgeUID() {
        return bridgeUID;
    }

    public void setBridgeUID(String bridgeUID) {
        this.bridgeUID = bridgeUID;
    }

    /*public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }*/

    /*public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }*/

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getThingTypeUID() {
        return thingTypeUID;
    }

    public void setThingTypeUID(String thingTypeUID) {
        this.thingTypeUID = thingTypeUID;
    }

    /*public List<OHChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<OHChannel> channels) {
        this.channels = channels;
    }*/



    public static class OHStatusInfo{
        private String status;
        private String statusDetail;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusDetail() {
            return statusDetail;
        }

        public void setStatusDetail(String statusDetail) {
            this.statusDetail = statusDetail;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OHStatusInfo that = (OHStatusInfo) o;

            if (status != null ? !status.equals(that.status) : that.status != null) return false;
            return statusDetail != null ? statusDetail.equals(that.statusDetail) : that.statusDetail == null;

        }

        @Override
        public int hashCode() {
            int result = status != null ? status.hashCode() : 0;
            result = 31 * result + (statusDetail != null ? statusDetail.hashCode() : 0);
            return result;
        }
    }

    public static class OHChannel{
        private List<String> linkedItems;
        private String uid;
        private String id;
        private String channelTypeUID;
        private String itemType;
        private List<String> defaultTags;
        private Map<String, String> configuration;
        private Map<String, String> properties;

        public List<String> getLinkedItems() {
            return linkedItems;
        }

        public void setLinkedItems(List<String> linkedItems) {
            this.linkedItems = linkedItems;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChannelTypeUID() {
            return channelTypeUID;
        }

        public void setChannelTypeUID(String channelTypeUID) {
            this.channelTypeUID = channelTypeUID;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public List<String> getDefaultTags() {
            return defaultTags;
        }

        public void setDefaultTags(List<String> defaultTags) {
            this.defaultTags = defaultTags;
        }

        public Map<String, String> getConfiguration() {
            return configuration;
        }

        public void setConfiguration(Map<String, String> configuration) {
            this.configuration = configuration;
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, String> properties) {
            this.properties = properties;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OHChannel ohChannel = (OHChannel) o;

            if (linkedItems != null ? !linkedItems.equals(ohChannel.linkedItems) : ohChannel.linkedItems != null)
                return false;
            if (uid != null ? !uid.equals(ohChannel.uid) : ohChannel.uid != null) return false;
            if (id != null ? !id.equals(ohChannel.id) : ohChannel.id != null) return false;
            if (channelTypeUID != null ? !channelTypeUID.equals(ohChannel.channelTypeUID) : ohChannel.channelTypeUID != null)
                return false;
            if (itemType != null ? !itemType.equals(ohChannel.itemType) : ohChannel.itemType != null)
                return false;
            if (defaultTags != null ? !defaultTags.equals(ohChannel.defaultTags) : ohChannel.defaultTags != null)
                return false;
            if (configuration != null ? !configuration.equals(ohChannel.configuration) : ohChannel.configuration != null)
                return false;
            return properties != null ? properties.equals(ohChannel.properties) : ohChannel.properties == null;

        }

        @Override
        public int hashCode() {
            int result = linkedItems != null ? linkedItems.hashCode() : 0;
            result = 31 * result + (uid != null ? uid.hashCode() : 0);
            result = 31 * result + (id != null ? id.hashCode() : 0);
            result = 31 * result + (channelTypeUID != null ? channelTypeUID.hashCode() : 0);
            result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
            result = 31 * result + (defaultTags != null ? defaultTags.hashCode() : 0);
            result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
            result = 31 * result + (properties != null ? properties.hashCode() : 0);
            return result;
        }
    }
}
