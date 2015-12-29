package com.hxl.miracle.myhttpapplication;

import android.content.Context;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by MirAcle on 2015/12/24.
 */
public class BLOkhttpClient {

    private static final int TIME_OUT = 15;
    private static BLOkhttpClient mInstance = null;
    private OkHttpClient mClient = null;

    public static BLOkhttpClient getInstance() {
        if (mInstance == null) {
            mInstance = new BLOkhttpClient();
            mInstance.init();
        }
        return mInstance;
    }

    public void init() {
        this.init(TIME_OUT);
    }

    public void init(int timeout) {
        mClient = new OkHttpClient();
        mClient.setConnectTimeout(TIME_OUT, TimeUnit.SECONDS);
//        //设置useragent，用于广告接入
//        String userAgent = System.getProperty("http.agent");
//        mClient.setUserAgent(userAgent);
    }

    public void syncCookie(Context context, String url) {
        if (context != null && url.startsWith(HttpServerURI.baseUrl)) {

//            mClient.setCookieHandler(new CookieManager(
//                    new PersistentCookieStore(getApplicationContext()),
//                    CookiePolicy.ACCEPT_ALL));
        }
    }

    public void executeRequest(final Request request, final BLOkRequestCallback blRequestCallback) {
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailResultCallback(request, e, blRequestCallback);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                sendSuccessResultCallback(request, response, blRequestCallback);
            }
        });
    }

    public void sendFailResultCallback(final Request request, final Exception e, final BLOkRequestCallback blRequestCallback) {
        if (blRequestCallback == null) return;

        blRequestCallback.onRequestFailure(request, e);
    }

    public void sendSuccessResultCallback(final Request request, final Response response, final BLOkRequestCallback blRequestCallback) {
        if (blRequestCallback == null) return;
        blRequestCallback.onRequestSuccess(request, response);
    }


    public void cancelTag(Object tag) {
        mClient.cancel(tag);
    }

    public void setTimeOut(int timeOut) {
        mClient.setConnectTimeout(timeOut, TimeUnit.SECONDS);
    }
}
