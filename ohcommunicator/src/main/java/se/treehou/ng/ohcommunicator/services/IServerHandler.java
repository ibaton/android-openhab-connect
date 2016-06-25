package se.treehou.ng.ohcommunicator.services;

import java.util.List;

import rx.Observable;
import se.treehou.ng.ohcommunicator.connector.models.OHBinding;
import se.treehou.ng.ohcommunicator.connector.models.OHInboxItem;
import se.treehou.ng.ohcommunicator.connector.models.OHItem;
import se.treehou.ng.ohcommunicator.connector.models.OHLinkedPage;
import se.treehou.ng.ohcommunicator.connector.models.OHServer;
import se.treehou.ng.ohcommunicator.connector.models.OHSitemap;
import se.treehou.ng.ohcommunicator.services.callbacks.OHCallback;

/**
 * Created by matti on 2016-06-25.
 */
public interface IServerHandler {
        void requestBindings(OHCallback<List<OHBinding>> bindingCallback);

        Observable<List<OHBinding>> requestBindingsRx();

        void requestInboxItems(OHCallback<List<OHInboxItem>> inboxCallback);

        Observable<List<OHInboxItem>> requestInboxItemsRx();

        void requestItem(String itemName, OHCallback<OHItem> itemCallback);

        Connector.ServerHandler.PageRequestTask requestPageUpdates(OHServer server, OHLinkedPage page, OHCallback<OHLinkedPage> callback);

        Observable<OHLinkedPage> requestPageUpdatesRx(OHServer server, OHLinkedPage page);

        void requestItem(OHCallback<List<OHItem>> itemCallback);

        Observable<List<OHItem>> requestItemsRx();

        void requestPage(OHLinkedPage page, OHCallback<OHLinkedPage> responseListener);

        Observable<OHLinkedPage> requestPageRx(OHLinkedPage page);

        String getUrl();

        void approveInboxItem(OHInboxItem inboxItem);

        void ignoreInboxItem(OHInboxItem inboxItem);

        void unignoreInboxItem(OHInboxItem inboxItem);

        void sendCommand(String item, String command);

        void requestSitemaps(OHCallback<List<OHSitemap>> sitemapsCallback);

        Observable<List<OHSitemap>> requestSitemapObservable();
}
