package com.hxl.miracle.myhttpapplication;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class BLHttpClient {
    private static final int TIME_OUT = 15000;
    private static BLHttpClient instance = null;
    private AsyncHttpClient mClient = null;

    public static BLHttpClient getInstance() {
        if (instance == null) {
            instance = new BLHttpClient();
            instance.init();
        }
        return instance;
    }

    public void init() {
        this.init(TIME_OUT);
    }

    public void init(int timeout) {
        mClient = new AsyncHttpClient();
        mClient.setTimeout(timeout);
        //设置useragent，用于广告接入
        String userAgent = System.getProperty("http.agent");
        mClient.setUserAgent(userAgent);
    }

    public void syncCookie(Context context, String url) {
//		if (context != null && url.startsWith(HttpServerURI.baseUrl)) {
//			PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
//			mClient.setCookieStore(myCookieStore);
//		}
    }

    public void simplePostRequest(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        mClient.post(url, params, responseHandler);
    }

    public void simpleGetRequest(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        mClient.get(url, params, responseHandler);
    }

    public void setTimeOut(int timeOut) {
        mClient.setTimeout(timeOut);
    }
}
