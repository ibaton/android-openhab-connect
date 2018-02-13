package se.treehou.ng.ohcommunicator.services;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import se.treehou.ng.ohcommunicator.connector.models.OHBinding;
import se.treehou.ng.ohcommunicator.connector.models.OHInboxItem;
import se.treehou.ng.ohcommunicator.connector.models.OHItem;
import se.treehou.ng.ohcommunicator.connector.models.OHLink;
import se.treehou.ng.ohcommunicator.connector.models.OHLinkedPage;
import se.treehou.ng.ohcommunicator.connector.models.OHSitemap;
import se.treehou.ng.ohcommunicator.connector.models.OHThing;

public interface IServerHandler {

    /**
     * Request binding as observable.
     *
     * @return binding observable.
     */
    Observable<List<OHBinding>> requestBindingsRx();

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
     * @return Observable with response
     */
    Observable<Response<ResponseBody>> createLinkRx(OHLink link);

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
     * @return Observable with response
     */
    Observable<Response<ResponseBody>> deleteLinkRx(OHLink link);

    /**
     * Request updates for page rx
     *
     * @param page the page to fetch updates for
     * @return observable returning updates for page.
     */
    Observable<OHLinkedPage> requestPageUpdatesRx(OHLinkedPage page);

    /**
     * Request items as observable
     *
     * @return item observable.
     */
    Observable<List<OHItem>> requestItemsRx();

    /**
     * Request item as observable stream.
     *
     * @param itemName the name of item to fetch.
     * @return observable emitting openhab items.
     */
    Observable<OHItem> requestItemRx(String itemName);

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
     * Request sitemap as observable.
     *
     * @return sitemap as observable.
     */
    Observable<List<OHSitemap>> requestSitemapRx();

    /**
     * Request things from server.
     *
     * @return observable returning things.
     */
    Observable<List<OHThing>> requestThingsRx();


    /**
     * Register to receive gcm events
     */
    Observable<String> registerGcm(String deviceId, String deviceModel, String regId);
}
