package com.example.dell.android.IeHttp;

import java.io.File;
import java.util.Map;

public class LeHttp {

    public static<T,M> void sendJsonRequest(String url, Map<String,String> requestData, Map<String, File> file_map, Class<M> response, LeJsonDataTransform listener){

        LeHttpRequest leHttpRequest = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallbackListener<>(response,listener);

        HttpTask httpTask = new HttpTask(url,requestData,file_map,leHttpRequest,callbackListener);

        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
