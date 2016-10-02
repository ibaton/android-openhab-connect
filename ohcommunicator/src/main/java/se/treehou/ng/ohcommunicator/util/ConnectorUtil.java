package se.treehou.ng.ohcommunicator.util;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import se.treehou.ng.ohcommunicator.connector.models.OHWidget;
import se.treehou.ng.ohcommunicator.util.OpenhabConstants;
import se.treehou.ng.ohcommunicator.util.OpenhabUtil;

public class ConnectorUtil {

    private static final String TAG = "ConnectorUtil";

    /**
     * Get the base of url with parameters and
     *
     * @param urlText the url to get base for.
     * @return base for url where protocol, host and port is kept.
     */
    public static String getBaseUrl(String urlText){
        try {
            URL url = new URL(urlText);
            return new URL(url.getProtocol(), url.getHost(), url.getPort(), "").toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Replace host part of url with provided new host.
     *
     * @param baseUrl the url to replace.
     * @param newHost the new host value
     * @return uri where base utl is replaced with new host
     */
    public static Uri changeHostUrl(Uri baseUrl, Uri newHost){
        Uri.Builder builder = baseUrl.buildUpon()
                .scheme(newHost.getScheme())
                .encodedAuthority(newHost.getHost() + (newHost.getPort() != -1 ? ":" + newHost.getPort() : ""));
        return builder.build();
    }

    /**
     * Check if url for server is valid
     *
     * @param url the url to check.
     * @return true if the url could lead to a server.
     */
    public static boolean isValidServerUrl(String url){
        return Patterns.WEB_URL.matcher(url).matches();
    }

    /**
     * Create a auth valuue used for basic authentication.
     * @param username the username to send.
     * @param password the password for user.
     * @return basic auth credentials as string
     */
    public static String createAuthValue(String username, String password) {
        final String credentials = username + ":" + password;
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        return auth;
    }

    /**
     * Creates a url that is used to request chart data.
     *
     * @param baseUrl the base url of server.
     * @param widget the widget to request data for.
     * @return url used to fetch chart image.
     */
    public static String buildChartRequestString(String baseUrl, OHWidget widget){

        if(widget.getItem() == null){
            return "";
        }

        Random random = new Random();
        String type = OpenhabUtil.isGroup(widget.getItem().getType()) ? "groups" : "items";
        Uri.Builder uriBuilder = Uri.parse(baseUrl+ OpenhabConstants.CHART_URL).buildUpon()
                .appendQueryParameter(type, widget.getItem().getName())
                .appendQueryParameter("period", widget.getPeriod())
                .appendQueryParameter("random",String.valueOf(Math.abs(random.nextInt())));

        if(!TextUtils.isEmpty(widget.getService())){
            uriBuilder.appendQueryParameter("service", widget.getService());
        }
        Uri builtUri = uriBuilder.build();
        builtUri = changeHostUrl(builtUri, Uri.parse(widget.getItem().getLink()));

        Log.d(TAG, "Creating chart url " + builtUri.toString());

        return builtUri.toString();
    }
}
