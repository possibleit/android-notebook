package com.example.dell.android.IeHttp;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask implements Runnable,Delayed {

    private LeHttpRequest leHttpRequest;

    public HttpTask(String url, Map<String,String> requestData, Map<String, File> file_map, LeHttpRequest leHttpRequest, CallbackListener callbackListener){
        this.leHttpRequest = leHttpRequest;
        leHttpRequest.setUrl(url);
        leHttpRequest.setListener(callbackListener);
        leHttpRequest.setFile(file_map);
        leHttpRequest.setData(requestData);
//        String content = JSON.toJSONString(requestData);
//        try {
//            leHttpRequest.setData(content.getBytes("utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }

    private long delayTime;
    private int retryCount;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public void run() {
        try {
            this.leHttpRequest.execute();
        }catch (Exception e){
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(),TimeUnit.MILLISECONDS );
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
