package com.hxl.miracle.myhttpapplication;

import android.content.Context;

/**
 * Created by MirAcle on 2015/12/25.
 */
public class TextRequest extends BLOkhttpRequest {
    private static final String REQUEST_PATHNAME = HttpServerURI.IHome_GetAPPHomePage;

    public TextRequest(Context mContext) {
        super(mContext, REQUEST_PATHNAME);
    }

    public void startTextRequest(String lotType, BLOkRequestCallback blRequestCallback) {
        params.put("lotType", lotType);
        startBLGetRequest(request, blRequestCallback);
    }


}
