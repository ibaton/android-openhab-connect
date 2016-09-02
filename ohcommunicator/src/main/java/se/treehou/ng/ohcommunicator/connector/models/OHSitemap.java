package se.treehou.ng.ohcommunicator.connector.models;

import android.net.Uri;

public class OHSitemap {

    private String name;
    private String label;
    private String link;
    private OHServer server;
    private OHLinkedPage homepage;

    /**
     * Get the sitemap name.
     * @return sitemap name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of sitemap.
     *
     * @param name sitemap name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get sitemap label.
     *
     * @return sitemap label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label used to display sitemap.
     *
     * @param label sitemap label.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get sitemap link.
     *
     * @return sitemap link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Set link to sitemap.
     *
     * @param link sitemap link.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Get sitemap for homepage
     *
     * @return sitemap homepage.
     */
    public OHLinkedPage getHomepage() {
        return homepage;
    }

    /**
     * Set the homepage for sitemap.
     *
     * @param homepage
     */
    public void setHomepage(OHLinkedPage homepage) {
        this.homepage = homepage;
    }

    /**
     * Get server for sitemap
     *
     * @return serverf sitemap.
     */
    public OHServer getServer() {
        return server;
    }

    /**
     * Set the server used by sitemap.
     * @param server server used by sitemap.
     */
    public void setServer(OHServer server) {
        this.server = server;
    }

    /**
     * Check if sitemap has a local url.
     *
     * @param sitemap the sitemap to check.
     * @return true if local sitemap, else false.
     */
    public static boolean isLocal(OHSitemap sitemap){
        Uri uri = Uri.parse(sitemap.getLink());

        try{
            return uri.getHost().equals(Uri.parse(sitemap.getServer().getLocalUrl()).getHost());
        }catch (Exception e){}

        return false;
    }

    /**
     * Get name used in lists etc.
     * @return display name for sitemap.
     */
    public String getDisplayName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return (""+name+":"+getServer().getName()).hashCode();
    }
}
