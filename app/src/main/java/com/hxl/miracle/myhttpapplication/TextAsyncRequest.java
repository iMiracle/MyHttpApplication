package com.hxl.miracle.myhttpapplication;

import android.content.Context;
import android.util.Log;

/**
 * Created by MirAcle on 2015/12/25.
 */
public class TextAsyncRequest extends RequestBase {

    private static final String REQUEST_PATHNAME = HttpServerURI.IHome_GetAPPHomePage;

    public TextAsyncRequest(Context mContext) {
        super(mContext, REQUEST_PATHNAME);
    }

    public void startTextRequest(String lotType, BLRequestCallback callback) {
        mParams.put("lotType", lotType);
        Log.d("totalUrl", "startTextRequest=----" + mParams.toString());
        startBLGetRequest(callback);
    }
}
