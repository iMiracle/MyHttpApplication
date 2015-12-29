package com.hxl.miracle.myhttpapplication;

public interface BLRequestCallback {
    void onSuccess(Basebean bean, String data);

    void onFailure(String errorCode, String errorMsg, Basebean bean);
}
