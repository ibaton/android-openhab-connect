package se.treehou.ng.ohcommunicator.connector.models;

import com.google.gson.annotations.Expose;

public class OHItem {

    public static final String TYPE_SWITCH = "SwitchItem";
    public static final String TYPE_STRING = "StringItem";
    public static final String TYPE_COLOR = "ColorItem";
    public static final String TYPE_NUMBER = "NumberItem";
    public static final String TYPE_CONTACT = "ContactItem";
    public static final String TYPE_ROLLERSHUTTER = "RollershutterItem";
    public static final String TYPE_GROUP = "GroupItem";
    public static final String TYPE_DIMMER = "DimmerItem";

    @Expose(serialize = false, deserialize = false)
    private long id;

    @Expose(serialize = false, deserialize = false)
    private OHServer server;

    private String type;
    private String name;
    private String label;
    private String link;
    private String state;
    private OHStateDescription stateDescription = new OHStateDescription();

    /**
     * Get id of item
     *
     * @return id of item
     */
    public long getId() {
        return id;
    }

    /**
     * Set the item id.
     * @param id value to identify item.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get server that holds item.
     *
     * @return the server that holds item.
     */
    public OHServer getServer() {
        return server;
    }

    /**
     * Set the server that holds this item.
     * @param server the item holding this item.
     */
    public void setServer(OHServer server) {
        this.server = server;
    }

    /**
     * Get the type of item.
     *
     * @return the type of item.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the item type for this item.
     *
     * @param type the type of item.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the name of this item
     *
     * @return name of item.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this item.
     * @param name item name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the label for this item.
     * @return label that should be used when displaying this item with data.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label used when displaying item.
     *
     * @param label text used to display item.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Url to connect to item.
     *
     * @return url identifying item.
     */
    public String getLink() {
        return link;
    }

    /**
     * Set the link used when accessing item.
     *
     * @param link the url used to connect to item.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * The state of this item.
     *
     * @return the state of item.
     */
    public String getState() {
        return state;
    }

    /**
     * Set the state of this item.
     *
     * @param state item state.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Get a description of the state.
     * Provides data like if the state can be modified.
     *
     * @return state description.
     */
    public OHStateDescription getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(OHStateDescription stateDescription) {
        if(stateDescription == null){
            stateDescription = new OHStateDescription();
        }
        this.stateDescription = stateDescription;
    }

    /**
     * Get formated version of state.
     *
     * @return formated state.
     */
    public String getFormatedValue(){
        if(getStateDescription() != null && getStateDescription().getPattern() != null){

            String pattern = getStateDescription().getPattern();
            try {
                return String.format(pattern, Float.valueOf(getState()));
            } catch (Exception e){}

            try {
                return String.format(pattern, Integer.valueOf(getState()));
            } catch (Exception e){}

            try {
                return String.format(pattern, getState());
            } catch (Exception e){}
        }

        return getState();
    }

    /**
     * Get name that should be used when displaying item.
     *
     * @return printable name
     */
    public String printableName(){
        if(server != null) {
            return server.getDisplayName() + " \n"  + name.replaceAll("_|-", " ");
        }
        return name.replaceAll("_|-", " ");
    }

    @Override
    public String toString() {
        return printableName();
    }
}
