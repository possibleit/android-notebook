package com.example.dell.android.IeHttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    //创建1队列，存放所有的网络请求
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance(){
        return threadPoolManager;
    }

    public void addDelayTask(HttpTask ht){
        if (ht != null){
            ht.setDelayTime(3000);
            mDelayQueue.offer(ht);
        }
    }

    //将网络请求任务添加到队列
    public void addTask(Runnable runnable){
        if (runnable != null){
            try {
                mQueue.put(runnable);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    private ThreadPoolExecutor mThreadPoolExecutor;


    private ThreadPoolManager(){
        mThreadPoolExecutor = new ThreadPoolExecutor(3,5,15,TimeUnit.SECONDS ,
                new ArrayBlockingQueue<Runnable>(4),new RejectedExecutionHandler(){

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });

        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    //创建核心线程
    public Runnable coreThread = new Runnable() {
        Runnable ruun = null;
        @Override
        public void run() {
            while (true){
                try {
                    ruun = mQueue.take();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(ruun);

            }
        }
    };

    public Runnable delayThread = new Runnable() {

        HttpTask httpTask = null;

        @Override
        public void run() {
            while (true){
                try {
                    httpTask = mDelayQueue.take();
                    if (httpTask.getRetryCount() < 3){
                        mThreadPoolExecutor.execute(httpTask);
                        httpTask.setRetryCount(httpTask.getRetryCount() + 1);
                        Log.e("test","重试机制" + httpTask.getRetryCount() + "次数");
                    }else {
                        Log.e("test","失败次数过多");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };



}
