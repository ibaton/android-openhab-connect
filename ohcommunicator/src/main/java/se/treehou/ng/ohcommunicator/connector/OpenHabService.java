package se.treehou.ng.ohcommunicator.connector;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;
import se.treehou.ng.ohcommunicator.connector.models.OHBinding;
import se.treehou.ng.ohcommunicator.connector.models.OHInboxItem;
import se.treehou.ng.ohcommunicator.connector.models.OHItem;
import se.treehou.ng.ohcommunicator.connector.models.OHLink;
import se.treehou.ng.ohcommunicator.connector.models.OHLinkedPage;
import se.treehou.ng.ohcommunicator.connector.models.OHSitemap;
import se.treehou.ng.ohcommunicator.connector.models.OHThing;

public interface OpenHabService {

    @Headers("Accept: application/json")
    @GET("/rest/links")
    Call<List<OHLink>> listLinks();

    @Headers("Accept: application/json")
    @GET("/rest/links")
    Observable<List<OHLink>> listLinksRx();

    @PUT("/rest/links/{itemName}/{channelUID}")
    Call<Void> createLink(@Path("itemName") String itemName, @Path("channelUID") String channelUID);

    @PUT("/rest/links/{itemName}/{channelUID}")
    Observable<Response<ResponseBody>> createLinkRx(@Path("itemName") String itemName, @Path("channelUID") String channelUID);

    @DELETE("/rest/links/{itemName}/{channelUID}")
    Call<Void> deleteLink(@Path("itemName") String itemName, @Path("channelUID") String channelUID);

    @DELETE("/rest/links/{itemName}/{channelUID}")
    Observable<Response<ResponseBody>> deleteLinkRx(@Path("itemName") String itemName, @Path("channelUID") String channelUID);

    @Headers("Accept: application/json")
    @GET("/rest/inbox")
    Call<List<OHInboxItem>> listInboxItems();

    @Headers("Accept: application/json")
    @GET("/rest/inbox")
    Observable<List<OHInboxItem>> listInboxItemsRx();

    @Headers("Accept: application/json")
    @POST("/rest/inbox/{thingUID}/ignore")
    Call<Void> ignoreInboxItems(@Path("thingUID") String thingUID);

    @Headers("Accept: application/json")
    @POST("/rest/inbox/{thingUID}/unignore")
    Call<Void> unignoreInboxItems(@Path("thingUID") String thingUID);

    @Headers("Accept: application/json")
    @POST("/rest/inbox/{thingUID}/approve")
    Call<Void> approveInboxItems(@Path("thingUID") String thingUID);

    @Headers("Accept: application/json")
    @GET("/rest/bindings")
    Call<List<OHBinding>> listBindings();

    @Headers("Accept: application/json")
    @GET("/rest/bindings")
    Observable<List<OHBinding>> listBindingsRx();

    @Headers("Accept: application/json")
    @GET("/rest/items/{id}")
    Call<OHItem> getItem(@Path("id") String id);


    @Headers("Accept: application/json")
    @GET("/rest/items/{id}")
    Observable<OHItem> getItemRx(@Path("id") String id);

    @Headers("Accept: application/json")
    @GET("/rest/items/")
    Call<List<OHItem>> listItems();

    @Headers("Accept: application/json")
    @GET("/rest/items/")
    Observable<List<OHItem>> listItemsRx();

    @Headers("Accept: application/json")
    @GET("/rest/sitemaps")
    Call<List<OHSitemap>> listSitemaps();

    @Headers("Accept: application/json")
    @GET("/rest/sitemaps")
    Observable<List<OHSitemap>> listSitemapsRx();

    @Headers("Accept: application/json")
    @GET("/rest/sitemaps/{id}")
    Call<OHSitemap> getSitemap(@Path("id") String id);

    @Headers("Accept: application/json")
    @GET
    Call<OHLinkedPage> getPage(@Url String url);

    @Headers(
            {"Accept: application/json",
            "Accept:application/json",
            "Accept-Charset:utf-8",
            "X-Atmosphere-Framework: 1.0",
            "X-Atmosphere-Transport: long-polling"})
    @GET
    Observable<OHLinkedPage> getPageUpdatesRx(@Header("X-Atmosphere-tracking-id") String atmosphereId, @Url String url);

    @Headers("Accept: application/json")
    @GET
    Observable<OHLinkedPage> getPageRx(@Url String url);

    @Headers({
            "Accept: application/text",
            "Content-Type: text/plain"
    })
    @POST("/rest/items/{id}")
    Call<Void> sendCommand(@Body String command, @Path("id") String id);

    @Headers("Accept: application/json")
    @GET("/rest/things")
    Observable<List<OHThing>> listThingsRx();
}
