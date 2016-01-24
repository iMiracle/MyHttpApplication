package com.hxl.miracle.myhttpapplication;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MirAcle on 2015/12/25.
 */
public class BLOkhttpRequest {

    protected static final String URL_BASE = HttpServerURI.baseUrl;
    protected Context mContext;
    protected String pathname;
    protected String mUrl;
    protected Request request;
    protected Map<String, String> params;

    public BLOkhttpRequest(Context mContext) {
        this.mContext = mContext;
    }

    public BLOkhttpRequest(Context mContext, String pathname) {
        this.mContext = mContext;
        mUrl = URL_BASE + pathname;
        initRequest();
    }

    private void initRequest() {
        params = new HashMap<String, String>();
    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    protected void startBLGetRequest(Request request, BLOkRequestCallback callback) {
        sendBLRequest(RequestMethod.GET, request, callback);
    }

    protected void startBLPostRequest(BLOkRequestCallback callback) {
        sendBLRequest(RequestMethod.POST, request, callback);
    }

    protected void sendBLRequest(RequestMethod method, Request request, BLOkRequestCallback blRequestCallback) {
        Log.d("request", "params:" + params);
        mUrl = appendParams(mUrl, params);
        Log.d("request", "mUrl:" + mUrl);
        request = new Request.Builder().url(mUrl).addHeader("user-agent", "android ").build();


        BLOkhttpClient client = BLOkhttpClient.getInstance();

        if (method == RequestMethod.GET) {
            client.executeRequest(request, blRequestCallback);
        } else if (method == RequestMethod.POST) {
            client.executeRequest(request, blRequestCallback);
        }
    }

    enum RequestMethod {
        GET,
        POST
    }

}
