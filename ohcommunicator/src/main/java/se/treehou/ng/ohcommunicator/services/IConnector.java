package se.treehou.ng.ohcommunicator.services;

import se.treehou.ng.ohcommunicator.connector.models.OHServer;

public interface IConnector {

    /**
     * Create a handler for connecting to server.
     *
     * @param server the server to connect to.
     * @return server handler.
     */
    IServerHandler getServerHandler(OHServer server);
}
