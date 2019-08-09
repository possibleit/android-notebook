package com.example.dell.android.IeHttp;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonCallbackListener<T> implements CallbackListener {

    private Class<T> responseClass;
    private LeJsonDataTransform leJsonDataTransform;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonCallbackListener(Class<T> responseClass,LeJsonDataTransform leJsonDataTransform){
        this.responseClass = responseClass;
        this.leJsonDataTransform = leJsonDataTransform;
    }

    public void setLeJsonDataTransform(LeJsonDataTransform leJsonDataTransform) {
        this.leJsonDataTransform = leJsonDataTransform;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String response = getContent(inputStream);
        final T clazz = JSON.parseObject(response, responseClass);
        mHandler.post(new Runnable(){

            @Override
            public void run() {
                leJsonDataTransform.onSuccess(clazz);
            }
        });
    }

    private String getContent(InputStream inputStream){
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer sb = new StringBuffer();
            String line = null;
            try {
                while ((line = reader.readLine()) != null){
                    sb.append(line + "\n");

                }
            }catch (IOException e){
                System.out.println("ERROR=" + e.toString());
            }finally {
                try {
                    inputStream.close();
                }catch (IOException e){
                    System.out.println("ERROR=" + e.toString());
                }
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void onFailure() {

    }
}
