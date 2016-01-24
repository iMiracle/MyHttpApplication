package com.hxl.miracle.myhttpapplication;

import android.content.Context;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.sql.Timestamp;
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
        final Timestamp beginTime = getTimestamp();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                statReqInfo(request.urlString(), beginTime, request.body().hashCode(), request.body().toString());
                sendFailResultCallback(request, e, blRequestCallback);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                statReqInfo(response.request().urlString(), beginTime, response.code(),response.message());
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

    private void statReqInfo(String reqUrl, Timestamp reqBegTime, int errorCode, String errorMsg){
        HttpReqInfo info = new HttpReqInfo(reqUrl, reqBegTime, getTimestamp(), errorCode, errorMsg);
        StatService.onHttpInfo(info);
    }

    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
