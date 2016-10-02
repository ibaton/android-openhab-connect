package se.treehou.ng.ohcommunicator.connector;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import se.treehou.ng.ohcommunicator.util.ConnectorUtil;
import se.treehou.ng.ohcommunicator.util.GsonHelper;

public class BasicAuthServiceGenerator {

    private static final String TAG = BasicAuthServiceGenerator.class.getSimpleName();

    // No need to instantiate this class.
    private BasicAuthServiceGenerator() {}

    public static <S> S createService(Class<S> serviceClass, final String usernarname, final String password, String url) {
        return createService(serviceClass, usernarname, password, url, -1);
    }

    public static <S> S createService(Class<S> serviceClass, final String usernarname, final String password, String url, int timeout) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        if(timeout > 0){
            client.readTimeout(timeout, TimeUnit.MILLISECONDS);
        }

        if(!TextUtils.isEmpty(usernarname) && !TextUtils.isEmpty(password)) {
            client.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", Credentials.basic(usernarname, password))
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        try {
            SSLSocketFactory socketFactory = TrustModifier.createFactory();
            X509TrustManager trustManager = Platform.get().trustManager(socketFactory);
            client.sslSocketFactory(socketFactory, trustManager);
            client.hostnameVerifier(new TrustModifier.NullHostNameVerifier());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        if(!ConnectorUtil.isValidServerUrl(url)){
            url = "http://127.0.0.1:8080";
        }

        Retrofit retrofit;
        try {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(client.build())
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonHelper.createGsonBuilder()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retrofit = builder.build();
        } catch (Exception e) {
            Log.e(TAG, "Failed to generate service", e);
            return null;
        }

        return retrofit.create(serviceClass);
    }
}
