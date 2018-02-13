package se.treehou.ng.ohcommunicator.connector;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MyOpenHabService {

    @Headers("Accept: text/plain")
    @GET("/addAndroidRegistration")
    Observable<String> registerGCM(@Query("deviceId") String deviceId, @Query("deviceModel") String deviceModel, @Query("regId") String regId);
}