package com.hxl.miracle.myhttpapplication;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * RequestBase 所有接口请求类的基类
 * 一般来说，请求返回的errorCode为"0"时，执行BLRequestCallback执行onSuccess方法
 * onSuccess时，返回的data，即bean.getData()有可能为null
 * 但并不影响 `JsonUtil.parseObject(data, XXX.class);` 的执行
 * 关键需要对解析过后的XXX对象进行判断，因为解析失败的情况下，XXX为null
 * 需要对回调函数定制化的请求，参考 CheckUpdateRequest
 */

public class RequestBase {
    public static final String ERROR_CODE_NONE = "0";
    public static final String ERROR_CODE_NETWORK = "200";
    public static final String ERROR_CODE_PARSE = "-1000";
    public static final String ERROR_MSG_NETWORK = "网络请求错误";
    public static final String ERROR_MSG_PARSE = "解析错误";
    protected static final String URL_BASE = HttpServerURI.baseUrl;
    private static final String TAG = RequestBase.class.getSimpleName();
    protected Context mContext;
    protected String mURL;
    protected BLRequestParams mParams;

    protected RequestBase(Context context, String pathname) {
        mContext = context;
        mURL = URL_BASE + pathname;
        mParams = new BLRequestParams();
    }

    protected RequestBase(Context context) {
        mContext = context;
        mParams = new BLRequestParams();
    }

    // 对本来服务器发送请求，需要额外添加一些参数，以及同步cookie
    public void sendBLRequest(RequestMethod method, final BLRequestCallback callback) {

//		BLHttpClient client = BLHttpClient.getInstance();
//		client.syncCookie(mContext, mURL);
        //获取完整的url链接
        String totalUrl = AsyncHttpClient.getUrlWithQueryString(false, mURL, mParams);
        Log.d("totalUrl", "totalUrl" + totalUrl);
        final Timestamp beginTime = getTimestamp();
        JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {

            public void onFailureResponse() {

                if (callback != null) {
                    statReqInfo(mURL,beginTime,404,ERROR_MSG_NETWORK);
                    callback.onFailure(ERROR_CODE_NETWORK, ERROR_MSG_NETWORK, null);

                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                if (callback != null) {
                    if (response == null || response.toString().trim().length() == 0) {
                        statReqInfo(mURL,beginTime,statusCode,response.toString());
                        callback.onFailure(ERROR_CODE_PARSE, ERROR_MSG_PARSE, null);
                        return;
                    }
                    Log.d("onSuccess", "response:" + response.toString());
                    Basebean bean = JSON.parseObject(response.toString(), Basebean.class);

                    if (bean == null) {
                        callback.onFailure(ERROR_CODE_PARSE, ERROR_MSG_PARSE, null);
                        statReqInfo(mURL, beginTime, statusCode, response.toString());
                        return;
                    }

                    String errorCode = bean.getError();
                    if (ERROR_CODE_NONE.equals(errorCode)) {
                        statReqInfo(mURL,beginTime,Integer.valueOf(ERROR_CODE_NONE),bean.getData());
                        String data = null;
                        if (bean.getData() != null && !bean.getData().equals("[]")) {
                            data = bean.getData();
                        }
                        callback.onSuccess(bean, data);
                        return;
                    }

                    String errorMsg = bean.getMessage();
                    statReqInfo(mURL, beginTime, statusCode, errorMsg);
                    callback.onFailure(errorCode, errorMsg, bean);
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                statReqInfo(mURL, beginTime, statusCode, errorResponse.toString());
                onFailureResponse();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                statReqInfo(mURL, beginTime, statusCode, responseString);
                onFailureResponse();
            }
        };

        sendSimpleRequest(method, responseHandler);
    }

    protected void startBLGetRequest(BLRequestCallback callback) {
        sendBLRequest(RequestMethod.GET, callback);
    }

    protected void startBLPostRequest(BLRequestCallback callback) {
        sendBLRequest(RequestMethod.POST, callback);
    }

    protected void sendSimpleRequest(RequestMethod method, JsonHttpResponseHandler responseHandler) {
        BLHttpClient client = BLHttpClient.getInstance();

        if (method == RequestMethod.GET) {
            client.simpleGetRequest(mURL, mParams, responseHandler);
        } else if (method == RequestMethod.POST) {
            client.simplePostRequest(mURL, mParams, responseHandler);
        }
    }

    enum RequestMethod {
        GET,
        POST
    }

    private void statReqInfo(String reqUrl, Timestamp reqBegTime, int errorCode, String errorMsg){
        HttpReqInfo info = new HttpReqInfo(reqUrl, reqBegTime, getTimestamp(), errorCode, errorMsg);
        StatService.onHttpInfo(info);
    }

    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
