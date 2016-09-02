package se.treehou.ng.ohcommunicator.services;

import rx.Observable;
import se.treehou.ng.ohcommunicator.connector.models.OHServer;

public interface IScanner {
    Observable<OHServer> registerRx();
}
