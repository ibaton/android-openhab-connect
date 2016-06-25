package se.treehou.ng.ohcommunicator.services;

import rx.Observable;
import se.treehou.ng.ohcommunicator.connector.models.OHServer;

/**
 * Created by matti on 2016-06-25.
 */
public interface IScanner {
    Observable<OHServer> registerRx();
}
