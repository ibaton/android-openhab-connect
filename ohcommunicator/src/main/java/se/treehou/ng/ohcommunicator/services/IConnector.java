package se.treehou.ng.ohcommunicator.services;

import se.treehou.ng.ohcommunicator.connector.models.OHServer;

public interface IConnector {
    IServerHandler getServerHandler(OHServer server);
}
