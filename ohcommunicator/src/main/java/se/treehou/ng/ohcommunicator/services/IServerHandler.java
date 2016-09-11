package se.treehou.ng.ohcommunicator.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import se.treehou.ng.ohcommunicator.connector.models.OHBinding;
import se.treehou.ng.ohcommunicator.connector.models.OHInboxItem;
import se.treehou.ng.ohcommunicator.connector.models.OHItem;
import se.treehou.ng.ohcommunicator.connector.models.OHLink;
import se.treehou.ng.ohcommunicator.connector.models.OHLinkedPage;
import se.treehou.ng.ohcommunicator.connector.models.OHServer;
import se.treehou.ng.ohcommunicator.connector.models.OHSitemap;
import se.treehou.ng.ohcommunicator.services.callbacks.OHCallback;

public interface IServerHandler {
    void requestBindings(OHCallback<List<OHBinding>> bindingCallback);

    /**
     * Request binding as observable.
     *
     * @return binding observable.
     */
    Observable<List<OHBinding>> requestBindingsRx();

    /**
     * Request links from server.
     * @param callback callback for links.
     */
    void requestLinks(OHCallback<List<OHLink>> callback);

    /**
     * Request obserbable emitting links.
     *
     * @return observable emitting link objects.
     */
    Observable<List<OHLink>> requestLinksRx();

    /**
     * Create a link object.
     *
     * @param link the link to create
     */
    void createLink(OHLink link);

    /**
     * Create a link object.
     *
     * @param link the link to create
     * @param callback listener for response
     */
    void createLink(OHLink link, Callback<Void> callback);

    /**
     * Delete a link object.
     *
     * @param link the link to delete
     */
    void deleteLink(OHLink link);

    /**
     * Delete a link object.
     *
     * @param link the link to delete
     * @param callback listener for response
     */
    void deleteLink(OHLink link, Callback<Void> callback);

    /**
     * Request updates to page.
     *
     * @param server   the server to connect to.
     * @param page     the page.
     * @param callback request callback.
     * @return cancelable request.
     */
    Connector.ServerHandler.PageRequestTask requestPageUpdates(OHServer server, OHLinkedPage page, OHCallback<OHLinkedPage> callback);

    /**
     * Request updates for page rx
     *
     * @param server the server to connect to.
     * @param page   the page to fetch updates for
     * @return observable returning updates for page.
     */
    Observable<OHLinkedPage> requestPageUpdatesRx(OHServer server, OHLinkedPage page);

    /**
     * Request item data.
     *
     * @param itemCallback
     */
    void requestItems(OHCallback<List<OHItem>> itemCallback);

    /**
     * Request items as observable
     *
     * @return item observable.
     */
    Observable<List<OHItem>> requestItemsRx();

    /**
     * Request item with callback.
     *
     * @param itemName     the name of item to fetch.
     * @param itemCallback callback for item request.
     */
    void requestItem(String itemName, OHCallback<OHItem> itemCallback);

    /**
     * Request item as observable stream.
     *
     * @param itemName the name of item to fetch.
     * @return observable emitting openhab items.
     */
    Observable<OHItem> requestItemRx(String itemName);

    /**
     * Request page with with callback.
     *
     * @param page             the page to fetch.
     * @param responseListener litener for page response.
     */
    void requestPage(OHLinkedPage page, OHCallback<OHLinkedPage> responseListener);

    /**
     * Request page as observable.
     *
     * @param page the page to fetch.
     * @return observable returning page from server.
     */
    Observable<OHLinkedPage> requestPageRx(OHLinkedPage page);

    /**
     * Get used to connect to server.
     *
     * @return server url-
     */
    String getUrl();

    /**
     * Request inbox items from server.
     *
     * @param inboxCallback callback from server.
     */
    void requestInboxItems(OHCallback<List<OHInboxItem>> inboxCallback);

    /**
     * Request inbox items from server
     *
     * @return observaable returning inbox items.
     */
    Observable<List<OHInboxItem>> requestInboxItemsRx();

    /**
     * Approve inbox item.
     *
     * @param inboxItem the inbox item to approve.
     */
    void approveInboxItem(OHInboxItem inboxItem);

    /**
     * Ignore inbox item.
     *
     * @param inboxItem ignore inbox item.
     */
    void ignoreInboxItem(OHInboxItem inboxItem);

    /**
     * Unignore ignored inbox item.
     *
     * @param inboxItem the item to unignore.
     */
    void unignoreInboxItem(OHInboxItem inboxItem);

    /**
     * Send command to item on server.
     * {@link se.treehou.ng.ohcommunicator.util.Commands}
     *
     * @param item    item to send command to.
     * @param command the command to send.
     */
    void sendCommand(String item, String command);

    /**
     * Request sitemap from server.
     *
     * @param sitemapsCallback sitemap response.
     */
    void requestSitemaps(OHCallback<List<OHSitemap>> sitemapsCallback);

    /**
     * Request sitemap as observable.
     *
     * @return sitemap as observable.
     */
    Observable<List<OHSitemap>> requestSitemapRx();
}
