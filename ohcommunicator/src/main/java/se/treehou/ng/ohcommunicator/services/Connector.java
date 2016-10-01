package se.treehou.ng.ohcommunicator.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;
import se.treehou.ng.ohcommunicator.connector.BasicAuthServiceGenerator;
import se.treehou.ng.ohcommunicator.connector.ConnectorUtil;
import se.treehou.ng.ohcommunicator.connector.GsonHelper;
import se.treehou.ng.ohcommunicator.connector.OpenHabService;
import se.treehou.ng.ohcommunicator.connector.models.OHBinding;
import se.treehou.ng.ohcommunicator.connector.models.OHInboxItem;
import se.treehou.ng.ohcommunicator.connector.models.OHItem;
import se.treehou.ng.ohcommunicator.connector.models.OHLink;
import se.treehou.ng.ohcommunicator.connector.models.OHLinkedPage;
import se.treehou.ng.ohcommunicator.connector.models.OHServer;
import se.treehou.ng.ohcommunicator.connector.models.OHSitemap;
import se.treehou.ng.ohcommunicator.connector.models.OHThing;
import se.treehou.ng.ohcommunicator.services.callbacks.OHCallback;
import se.treehou.ng.ohcommunicator.services.callbacks.OHResponse;

public class Connector implements IConnector {

    private static final String TAG = Connector.class.getSimpleName();

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

        @Override
        public void requestBindings(final OHCallback<List<OHBinding>> bindingCallback){
            OpenHabService service = getService();
            if(bindingCallback == null){
                return;
            } else if(!validSetup()) {
                bindingCallback.onError();
                return;
            }

            service.listBindings().enqueue(new Callback<List<OHBinding>>() {
                @Override
                public void onResponse(Call<List<OHBinding>> call, Response<List<OHBinding>> response) {
                    List<OHBinding> items = response.body();
                    if(items == null) items = new ArrayList<>();
                    bindingCallback.onUpdate(new OHResponse.Builder<>(items).build());
                }

                @Override
                public void onFailure(Call<List<OHBinding>> call, Throwable t) {
                    bindingCallback.onError();
                }
            });
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
         * Ask server for inbox items.
         *
         * @param inboxCallback server response callback.
         */
        @Override
        public void requestInboxItems(final OHCallback<List<OHInboxItem>> inboxCallback){
            OpenHabService service = getService();
            if(inboxCallback == null){
                return;
            } else if(!validSetup()){
                inboxCallback.onError();
                return;
            }

            service.listInboxItems().enqueue(new Callback<List<OHInboxItem>>() {
                @Override
                public void onResponse(Call<List<OHInboxItem>> call, Response<List<OHInboxItem>> response) {
                    List<OHInboxItem> inboxItems = response.body();
                    if(inboxItems == null) inboxItems = new ArrayList<>();
                    inboxCallback.onUpdate(new OHResponse.Builder<>(inboxItems).build());
                }

                @Override
                public void onFailure(Call<List<OHInboxItem>> call, Throwable t) {
                    inboxCallback.onError();
                }
            });
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
        public void requestItem(String itemName, final OHCallback<OHItem> itemCallback){
            OpenHabService service = getService();
            if(itemCallback == null){
                return;
            } else if (!validSetup()){
                itemCallback.onError();
                return;
            }

            service.getItem(itemName).enqueue(
                    new Callback<OHItem>() {
                        @Override
                        public void onResponse(Call<OHItem> call, Response<OHItem> response) {
                            itemCallback.onUpdate(new OHResponse.Builder<>(response.body()).build());
                        }

                        @Override
                        public void onFailure(Call<OHItem> call, Throwable t) {
                            itemCallback.onError();
                        }
                    });

        }

        @Override
        public Observable<OHItem> requestItemRx(String itemName){
            OpenHabService service = getService();
            if(!validSetup()) return Observable.never();

            return service.getItemRx(itemName).asObservable();
        }

        @Override
        public void requestLinks(final OHCallback<List<OHLink>> callback){
            OpenHabService service = getService();
            if(callback == null){
                return;
            } else if(!validSetup()){
                callback.onError();
                return;
            }

            service.listLinks().enqueue(new Callback<List<OHLink>>() {
                @Override
                public void onResponse(Call<List<OHLink>> call, Response<List<OHLink>> response) {
                    List<OHLink> items = response.body();
                    if(items == null) items = new ArrayList<>();
                    callback.onUpdate(new OHResponse.Builder<>(items).build());
                }

                @Override
                public void onFailure(Call<List<OHLink>> call, Throwable t) {
                    callback.onError();
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

        @Override
        public PageRequestTask requestPageUpdates(final OHServer server, final OHLinkedPage page, final OHCallback<OHLinkedPage> callback) {
            PageRequestTask task = new PageRequestTask(server, page, callback);
            task.start(context);
            return task;
        }

        /**
         * Creates rx observable listening for page updates.
         *
         * @param server the server to connect to.
         * @param page the page to listen for.
         * @return page observable.
         */
        @Override
        public Observable<OHLinkedPage> requestPageUpdatesRx(final OHServer server, final OHLinkedPage page) {

            return Observable.create(new Observable.OnSubscribe<OHLinkedPage>(){
                @Override
                public void call(final Subscriber<? super OHLinkedPage> subscriber) {
                    final PageRequestTask pageRequestTask = requestPageUpdates(server, page, new OHCallback<OHLinkedPage>() {
                        @Override
                        public void onUpdate(OHResponse<OHLinkedPage> items) {
                            if(!subscriber.isUnsubscribed()) {
                                subscriber.onNext(items.body());
                            }
                        }

                        @Override
                        public void onError() {
                            subscriber.onError(new IOException());
                        }
                    });

                    subscriber.add(Subscriptions.create(new Action0() {
                        @Override
                        public void call() {
                            pageRequestTask.stop();
                        }
                    }));
                }
            }).repeat();
        }

        @Override
        public void requestItems(final OHCallback<List<OHItem>> itemCallback){
            OpenHabService service = getService();
            if(itemCallback == null){
                return;
            } else if(!validSetup()){
                itemCallback.onError();
                return;
            }

            service.listItems().enqueue(new Callback<List<OHItem>>() {
                @Override
                public void onResponse(Call<List<OHItem>> call, Response<List<OHItem>> response) {
                    for(OHItem item : response.body()) item.setServer(server);
                    itemCallback.onUpdate(new OHResponse.Builder<>(response.body()).build());
                }

                @Override
                public void onFailure(Call<List<OHItem>> call, Throwable t) {
                    itemCallback.onError();
                }
            });
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

            return service.listItemsRx();
        }

        /**
         * Request page for from server
         *
         * @param page the page to fetch.
         * @param responseListener response listener.
         */
        @Override
        public void requestPage(OHLinkedPage page, final OHCallback<OHLinkedPage> responseListener) {
            OpenHabService service = getService();
            if(responseListener == null){
                return;
            } else if(!validSetup()){
                responseListener.onError();
                return;
            }

            service.getPage(page.getLink()).enqueue(new Callback<OHLinkedPage>() {
                @Override
                public void onResponse(Call<OHLinkedPage> call, Response<OHLinkedPage> response) {
                    Log.d(TAG, "Received page " + response.message());
                    responseListener.onUpdate(new OHResponse.Builder<>(response.body()).build());
                }

                @Override
                public void onFailure(Call<OHLinkedPage> call, Throwable t) {
                    Log.e(TAG, "Received page error ", t);
                    responseListener.onError();
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
         * Request sitemap from server asyncronusly.
         * @param sitemapsCallback
         */
        @Override
        public void requestSitemaps(final OHCallback<List<OHSitemap>> sitemapsCallback){
            OpenHabService service = getService();
            if(sitemapsCallback == null){
                return;
            } else if(!validSetup()) {
                Log.d(TAG, "Failed to request sitemap, service is null");
                sitemapsCallback.onError();
                return;
            }

            service.listSitemaps().enqueue(new Callback<List<OHSitemap>>() {
                @Override
                public void onResponse(Call<List<OHSitemap>> call, Response<List<OHSitemap>> response) {
                    OHResponse<List<OHSitemap>> sitemapResponse = new OHResponse.Builder<>(response.body())
                            .fromCache(false)
                            .build();
                    sitemapsCallback.onUpdate(sitemapResponse);
                }

                @Override
                public void onFailure(Call<List<OHSitemap>> call, Throwable t) {
                    sitemapsCallback.onError();
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

        public static class PageRequestTask {

            private final OHServer server;
            private final OHLinkedPage page;
            private AsyncHttpClient asyncHttpClient;
            private final OHCallback<OHLinkedPage> callback;
            private List<BasicHeader> headers;

            public PageRequestTask(OHServer server, OHLinkedPage page, OHCallback<OHLinkedPage> callback) {
                this.server = server;
                this.page = page;
                this.callback = callback;
            }

            protected void start(Context context) {

                asyncHttpClient = createAsyncClient(server);
                headers = generateHeaders();
                requestPage(context);
            }

            public void stop() {
                asyncHttpClient.cancelAllRequests(true);
            }

            private void requestPage(final Context context){
                asyncHttpClient.get(context, page.getLink(), headers.toArray(new BasicHeader[] {}), null,  new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String jsonString = new String(responseBody, "UTF-8");
                            Log.d(TAG, "HttpClient onSuccess received " + jsonString);
                            Gson gson = GsonHelper.createGsonBuilder();
                            OHLinkedPage linkedPage = gson.fromJson(jsonString, OHLinkedPage.class);
                            callback.onUpdate(new OHResponse.Builder<>(linkedPage).build());

                            requestPage(context);
                        } catch (Exception error) {
                            error.printStackTrace();
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        error.printStackTrace();
                        callback.onError();
                    }
                });
            }

            /**
             * Creates a async http client using server data for authentication.
             * @param server the server to connect to.
             * @return http server.
             */
            private AsyncHttpClient createAsyncClient(OHServer server){

                AsyncHttpClient client;
                if (Looper.myLooper() == null) {
                    client = new SyncHttpClient();
                } else {
                    client = new AsyncHttpClient();
                }

                if(server.requiresAuth()) {
                    client.setBasicAuth(server.getUsername(), server.getPassword());
                }
                client.setTimeout(30000);

                return client;
            }

            private List<BasicHeader> generateHeaders(){
                UUID atmosphereId = UUID.randomUUID();

                List<BasicHeader> headers = new ArrayList<>();
                headers.add(new BasicHeader("Accept", "application/json"));
                headers.add(new BasicHeader("Accept-Charset", "utf-8"));
                headers.add(new BasicHeader("X-Atmosphere-Framework", "1.0"));
                headers.add(new BasicHeader("X-Atmosphere-Transport", "long-polling"));
                headers.add(new BasicHeader("X-Atmosphere-tracking-id", atmosphereId.toString()));

                return headers;
            }
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
