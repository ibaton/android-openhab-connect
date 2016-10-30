package se.treehou.ng.ohcommunicator.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;
import se.treehou.ng.ohcommunicator.connector.BasicAuthServiceGenerator;
import se.treehou.ng.ohcommunicator.util.ConnectorUtil;
import se.treehou.ng.ohcommunicator.connector.OpenHabService;
import se.treehou.ng.ohcommunicator.connector.models.OHBinding;
import se.treehou.ng.ohcommunicator.connector.models.OHInboxItem;
import se.treehou.ng.ohcommunicator.connector.models.OHItem;
import se.treehou.ng.ohcommunicator.connector.models.OHLink;
import se.treehou.ng.ohcommunicator.connector.models.OHLinkedPage;
import se.treehou.ng.ohcommunicator.connector.models.OHServer;
import se.treehou.ng.ohcommunicator.connector.models.OHSitemap;
import se.treehou.ng.ohcommunicator.connector.models.OHThing;
import se.treehou.ng.ohcommunicator.util.RxUtil;

public class Connector implements IConnector {

    private static final String TAG = Connector.class.getSimpleName();

    private static final int LONG_POLLING_TIMEOUT = 30*1000;

    private Context context;

    public Connector(Context context) {
        this.context = context;
    }

    /**
     * Create handler used to connect to openhab.
     * @param server the server to connect to.
     * @param url the url to connect to
     * @return Service used to communicate with openhab.
     */
    private static OpenHabService generateOpenHabService(OHServer server, String url) throws IllegalArgumentException {
        return BasicAuthServiceGenerator.createService(OpenHabService.class, server.getUsername(), server.getPassword(), url);
    }

    private static OpenHabService generateOpenHabServiceLongPolling(OHServer server, String url) throws IllegalArgumentException {
        return BasicAuthServiceGenerator.createService(OpenHabService.class, server.getUsername(), server.getPassword(), url, LONG_POLLING_TIMEOUT);
    }

    @Override
    public IServerHandler getServerHandler(OHServer server){
        return new ServerHandler(server, context);
    }

    public static class ServerHandler implements IServerHandler {

        private OHServer server;
        private Context context;

        private OpenHabService openHabService;

        public ServerHandler(OHServer server, Context context) {
            this.server = server;
            this.context = context;

            try {
                openHabService = generateOpenHabService(server, getUrl());
            } catch (Exception e){
                Log.e(TAG, "Error while generating server", e);
                openHabService = null;
            }
        }

        public boolean validSetup(){
            return openHabService != null;
        }

        /**
         * Generate an openhab service used to connect to server.
         *
         * @return openhab service.
         */
        private OpenHabService getService(){
            openHabService = generateOpenHabService(server, getUrl());

            return openHabService;
        }

        /**
         * Generate an openhab service used to connect to server using long polling.
         *
         * @return openhab service.
         */
        private OpenHabService getLongPollingService(){
            return generateOpenHabServiceLongPolling(server, getUrl());
        }

        /**
         * Request bindings through rx.
         *
         * @return observable for bindings.
         */
        @Override
        public Observable<List<OHBinding>> requestBindingsRx(){
            OpenHabService service = getService();
            if(!validSetup()) return Observable.empty();

            return service.listBindingsRx();
        }

        /**
         * Ask server for inbox items as rx observable.
         * @return observable for inbox items.
         */
        @Override
        public Observable<List<OHInboxItem>> requestInboxItemsRx(){
            OpenHabService service = getService();
            if(!validSetup()) return Observable.empty();

            return service.listInboxItemsRx();
        }

        @Override
        public Observable<OHItem> requestItemRx(String itemName){
            OpenHabService service = getService();
            if(!validSetup()) return Observable.never();

            return service.getItemRx(itemName).asObservable().map(new Func1<OHItem, OHItem>() {
                @Override
                public OHItem call(OHItem item) {
                    item.setServer(server);
                    return item;
                }
            });
        }

        @Override
        public Observable<List<OHLink>> requestLinksRx(){
            OpenHabService service = getService();
            if(!validSetup()) return Observable.never();

            return service.listLinksRx();
        }

        @Override
        public void createLink(OHLink link){
            OpenHabService service = getService();
            if(!validSetup() || link == null) return;
            service.createLink(link.getItemName(), link.getChannelUID());
        }

        @Override
        public Observable<Response<ResponseBody>> createLinkRx(OHLink link){
            OpenHabService service = getService();
            if(!validSetup() || link == null) return Observable.error(new IOException("No server found"));
            return service.createLinkRx(link.getItemName(), link.getChannelUID());
        }

        @Override
        public void deleteLink(OHLink link){
            OpenHabService service = getService();
            if(!validSetup() || link == null) return;
            service.deleteLink(link.getItemName(), link.getChannelUID());
        }

        @Override
        public Observable<Response<ResponseBody>> deleteLinkRx(OHLink link){
            OpenHabService service = getService();
            if(!validSetup() || link == null) return Observable.error(new IOException("No server found"));
            return service.deleteLinkRx(link.getItemName(), link.getChannelUID());
        }

        /**
         * Creates rx observable listening for page updates.
         *
         * @param page the page to listen for.
         * @return page observable.
         */
        @Override
        public Observable<OHLinkedPage> requestPageUpdatesRx(final OHLinkedPage page) {
            OpenHabService service = getLongPollingService();
            if(!validSetup()) return Observable.never();

            UUID atmosphereId = UUID.randomUUID();
            return service.getPageUpdatesRx(atmosphereId.toString(), page.getLink())
                    .retryWhen(RxUtil.RetryOnTimeout)
                    .repeat();
        }

        /**
         * Request items observable.
         *
         * @return items observable.
         */
        @Override
        public Observable<List<OHItem>> requestItemsRx(){
            OpenHabService service = getService();
            if(!validSetup()){
                return Observable.empty();
            }

            return service.listItemsRx().map(new Func1<List<OHItem>, List<OHItem>>() {
                @Override
                public List<OHItem> call(List<OHItem> items) {
                    for(OHItem item : items){
                        item.setServer(server);
                    }
                    return items;
                }
            });
        }

        /**
         * Request page for from server
         *
         * @param page the page to fetch.
         * @return observable for page
         */
        @Override
        public Observable<OHLinkedPage> requestPageRx(OHLinkedPage page) {
            OpenHabService service = getService();
            if(!validSetup()){
                return Observable.empty();
            }
            return service.getPageRx(page.getLink());
        }

        /**
         * Get url from server.
         *
         * @return url for server.
         */
        @Override
        public String getUrl(){
            return getUrl(context, server);
        }

        /**
         * Get url from server.
         * @param context calling context.
         * @param server the server to connect to.
         * @return
         */
        public static String getUrl(Context context, OHServer server){
            String url = server.getLocalUrl();
            String remoteUrl = server.getRemoteUrl();
            NetworkInfo networkInfo = getNetworkInfo(context);
            if(networkInfo == null || !networkInfo.isConnected()){
                return "";
            }
            if(!isConnectedWifi(context) || !ConnectorUtil.isValidServerUrl(url)){
                url = remoteUrl;
            }

            if(url == null) url = "";

            return url;
        }

        /**
         * Get network information.
         *
         * @param context the calling context.
         * @return information for the network.
         */
        public static NetworkInfo getNetworkInfo(Context context){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        }

        /**
         * Check if device is connected to network.
         *
         * @param context the calling context.
         * @return true if connected to wifi.
         */
        public static boolean isConnectedWifi(Context context){
            NetworkInfo info = getNetworkInfo(context);
            return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
        }

        /**
         * Send an approve request to inbox.
         * Update all listeners.
         *
         * @param inboxItem the inbox item to approve.
         */
        @Override
        public void approveInboxItem(OHInboxItem inboxItem){
            OpenHabService service = getService();
            if(!validSetup()) return;

            service.approveInboxItems(inboxItem.getThingUID()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }

        /**
         * Send an ignore request to inbox.
         * Update all listeners.
         *
         * @param inboxItem the inbox item to ignore.
         */
        @Override
        public void ignoreInboxItem(OHInboxItem inboxItem){
            OpenHabService service = getService();
            if(!validSetup()) return;

            service.ignoreInboxItems(inboxItem.getThingUID()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            inboxItem.setFlag(OHInboxItem.FLAG_IGNORED);
        }

        /**
         * Send an unignore request to inbox.
         * Update all listeners.
         *
         * @param inboxItem the inbox item to unignore.
         */
        @Override
        public void unignoreInboxItem(OHInboxItem inboxItem){
            OpenHabService service = getService();
            if(!validSetup()) return;

            service.unignoreInboxItems(inboxItem.getThingUID()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            inboxItem.setFlag(OHInboxItem.FLAG_NEW);
        }

        @Override
        public void sendCommand(final String item, final String command){
            OpenHabService service = getService();
            if(!validSetup()) return;

            service.sendCommand(command, item).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d(TAG, "Sent sendCommand " + command);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable e) {
                    Log.e(TAG, "Error: sending command " + server.getLocalUrl() + " body: " + command, e);
                }
            });
        }

        /**
         * Request sitemaps from servera
         * @return observer for remote sitemaps
         */
        @Override
        public Observable<List<OHSitemap>> requestSitemapRx(){
            OpenHabService service = getService();
            if(!validSetup()) return Observable.error(new NoServerFoundException());
            return service.listSitemapsRx();
        }

        @Override
        public Observable<List<OHThing>> requestThingsRx() {
            OpenHabService service = getService();
            if(!validSetup()) return Observable.error(new NoServerFoundException());
            return service.listThingsRx();
        }
    }

    public static class NoServerFoundException extends Exception{}

    static class NullCallback<T> implements Callback<T>{
        @Override
        public void onResponse(Call<T> call, Response<T> response) {

        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {

        }
    }
}
