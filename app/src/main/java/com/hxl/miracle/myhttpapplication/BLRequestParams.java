package com.hxl.miracle.myhttpapplication;

import com.loopj.android.http.RequestParams;

import java.util.Map;

public class BLRequestParams extends RequestParams {
    public Map<String, String> getUrlParams() {
        return this.urlParams;
    }
}
