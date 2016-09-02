package se.treehou.ng.ohcommunicator.connector.models;

import java.util.List;

public class OHChannel {

    private String description;
    private String id;
    private String label;
    private List<String> tags;
    private String category;
    private boolean advance;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

}
