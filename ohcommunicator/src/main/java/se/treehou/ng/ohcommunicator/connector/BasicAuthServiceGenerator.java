package se.treehou.ng.ohcommunicator.connector;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import se.treehou.ng.ohcommunicator.util.ConnectorUtil;
import se.treehou.ng.ohcommunicator.util.GsonHelper;

public class BasicAuthServiceGenerator {

    private static final String TAG = BasicAuthServiceGenerator.class.getSimpleName();

    // No need to instantiate this class.
    private BasicAuthServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, final String usernarname, final String password, String url) {
        return createService(serviceClass, usernarname, password, url, -1);
    }

    public static <S> S createService(Class<S> serviceClass, final String usernarname, final String password, String url, SSLContext sslContext, X509TrustManager trustManager, HostnameVerifier verifier) {
        return createService(serviceClass, usernarname, password, url, -1, sslContext, trustManager, verifier);
    }

    public static <S> S createService(Class<S> serviceClass, final String usernarname, final String password, String url, int timeout) {
        return createService(serviceClass, usernarname, password, url, -1, null, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String usernarname, final String password, String url, int timeout, SSLContext sslContext, X509TrustManager trustManager, HostnameVerifier verifier) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        if (timeout > 0) {
            client.readTimeout(timeout, TimeUnit.MILLISECONDS);
        }

        if (!TextUtils.isEmpty(usernarname) && !TextUtils.isEmpty(password)) {
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
            if (trustManager == null) {
                try {
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init((KeyStore) null);
                    trustManager = (X509TrustManager) tmf.getTrustManagers()[0];
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }

                SSLSocketFactory socketFactory = TrustModifier.createFactory();
                client.sslSocketFactory(socketFactory, trustManager);
                client.hostnameVerifier(new TrustModifier.NullHostNameVerifier());
            } else {
                //sslContext.init(null, new TrustManager[] {trustManager}, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                client.sslSocketFactory(sslSocketFactory, trustManager);
                client.hostnameVerifier(verifier);
            }
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        if (!ConnectorUtil.isValidServerUrl(url)) {
            url = "http://127.0.0.1:8080";
        }

        Retrofit retrofit;
        try {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(client.build())
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonHelper.createGsonBuilder()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            retrofit = builder.build();
        } catch (Exception e) {
            Log.e(TAG, "Failed to generate service", e);
            return null;
        }

        return retrofit.create(serviceClass);
    }
}
