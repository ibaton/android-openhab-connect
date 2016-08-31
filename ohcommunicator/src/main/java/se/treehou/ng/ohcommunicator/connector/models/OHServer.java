package se.treehou.ng.ohcommunicator.connector.models;

import android.text.TextUtils;

public class OHServer {

    private long id;
    private String name;
    private String username;
    private String password;
    private String localurl;
    private String remoteUrl;
    private int majorversion;

    public OHServer() {}

    public OHServer(long id, String name, String username, String password, String localurl, String remoteUrl, int majorversion) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.localurl = localurl;
        this.remoteUrl = remoteUrl;
        this.majorversion = majorversion;
    }

    /**
     * Get id used to identify server.
     *
     * @return server id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set id used to identify server.
     *
     * @param id server id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the name of the server.
     *
     * @return name of server.
     */
    public String getName() {
        return name;
    }

    /**
     * Set server name.
     *
     * @param name the server name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the username to use when connecting to server.
     *
     * @return username used to connect to server.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the server name that should be used when connecting to server.
     *
     * @param username the username, null if no value is set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password that should be used when connecting to server.
     *
     * @return password, null if no password provided.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password used when connecting to server.
     *
     * @param password the password to use when connecting to server.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalUrl() {
        return localurl;
    }

    /**
     * Get the url that should be used when on same network as server.
     * @param localurl url to local server.
     */
    public void setLocalurl(String localurl) {
        this.localurl = localurl;
    }

    /**
     * Get url used when not able to connect on local network.
     *
     * @return remote url.
     */
    public String getRemoteUrl() {
        return remoteUrl;
    }

    /**
     * Set the url to use when connecting to a remote server.
     *
     * @param remoteUrl remote url.
     */
    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    /**
     * Set the major version of server
     * @param majorversion
     */
    public void setMajorversion(int majorversion){
        this.majorversion = majorversion;
    }

    /**
     * Get the minor server version.
     *
     * @return minor server version
     */
    public int getMajorversion() {
        return majorversion;
    }

    /**
     * Get the display name for server.
     * Nicely formatted for displaying in user interface.
     *
     * @return name of server.
     */
    public String getDisplayName(){
        return getName();
    }

    /**
     * Check if basic auth should be used when connecting to server.
     *
     * @return true if auth should be used, else false.
     */
    public boolean requiresAuth() {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }

    /**
     * Check if server has a local url.
     * @return true if there is a local url registered. Else false.
     */
    public boolean haveLocal() {
        return !TextUtils.isEmpty(getLocalUrl());
    }

    /**
     * Check if server has a remote url.
     * @return true if there is a remote url registered. Else false.
     */
    public boolean haveRemote() {
        return !TextUtils.isEmpty(getRemoteUrl());
    }

    @Override
    public int hashCode() {
        return (""+name+":"+localurl+":"+ remoteUrl).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }
}
