package com.gof.scut.androidcourse.net;

import android.content.Context;

import com.gof.scut.androidcourse.storage.XManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

/**
 * Created by gjz on 11/2/15.
 */
public class HttpClient {
    private static final String BASE_URL = "http://121.42.160.104/BusinessCard/web/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, RequestParams params, JsonResponseHandler handler) {
        params.put(RequestParamName.TOKEN, XManager.getToken(context));
        client.get(context, BASE_URL + url, params, handler);
    }

    public static void post(Context context, String url, RequestParams params, JsonResponseHandler handler) {
        params.put(RequestParamName.TOKEN, XManager.getToken(context));
        client.post(context, BASE_URL + url, params, handler);
    }

}
