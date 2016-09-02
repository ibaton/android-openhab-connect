package se.treehou.ng.ohcommunicator.connector.models;

public class OHBinding {

    private String id;
    private String name;
    private String author;
    private String description;

    /**
     * Get id of binding.
     * @return binding id.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name of Openhab binding.
     * @return openhab binding name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get binding description.
     * @return binding description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the author of the binding.
     * @return binding author.
     */
    public String getAuthor() {
        return author;
    }

}
