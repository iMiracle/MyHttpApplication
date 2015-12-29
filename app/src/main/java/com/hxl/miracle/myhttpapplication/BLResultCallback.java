package com.hxl.miracle.myhttpapplication;

/**
 * Created by MirAcle on 2015/12/25.
 */
public interface BLResultCallback {

    void onSuccess(Basebean basebean, String data);

    void onFailure(Basebean basebean, String errorCode, String errorMsg);
}
