package com.hxl.miracle.myhttpapplication;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public interface BLOkRequestCallback {
    void onRequestFailure(Request request, Exception e);

    void onRequestSuccess(Request request, Response response);
}
