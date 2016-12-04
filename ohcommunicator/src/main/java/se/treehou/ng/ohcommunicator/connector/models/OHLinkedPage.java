package se.treehou.ng.ohcommunicator.connector.models;

import java.util.ArrayList;
import java.util.List;

import se.treehou.ng.ohcommunicator.util.ConnectorUtil;

public class OHLinkedPage {

    private String id = "";
    private String link;
    private String title = "";
    private boolean leaf;
    private List<OHWidget> widget = new ArrayList<>();

    /**
     * Set the id used to identify page
     * @return page id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id used to identify page
     * @param id the id of page.
     */
    public void setId(String id) {
        if(id == null) id = "";
        this.id = id;
    }

    /**
     * Get the url to page.
     *
     * @return url to access page
     */
    public String getLink() {
        return link;
    }

    public boolean validLink(){
        return link != null;
    }

    /***
     * The url to use when accessing page.
     * @param link url for page
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Get the title to use when accessing page.
     * @return page title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of page.
     * @param title page title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * Get widgets in page list.
     * @return widgets on page
     */
    public List<OHWidget> getWidgets() {
        if(widget == null){
            return new ArrayList<>();
        }
        return widget;
    }

    /**
     * Set the widgets on page.
     * @param widget the widgets on page.
     */
    public void setWidgets(List<OHWidget> widget) {
        this.widget = widget;
    }

    /**
     * The base url for page.
     * @return page base url.
     */
    public String getBaseUrl(){
        return ConnectorUtil.getBaseUrl(getLink());
    }
}
