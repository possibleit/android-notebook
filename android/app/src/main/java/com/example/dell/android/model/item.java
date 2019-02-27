package com.example.dell.android.model;

import java.io.Serializable;

public class item implements Serializable {
    //继承Serializable接口是为了intent传输数据
    private boolean Top;//是否置顶
    private boolean type;//判断记录是否带有图片
    private int id;//执行删除操作
    private String time;
    private String text;
    private String path;
    public item(int id, String time, String text,boolean type,String path,boolean top){//从网上下载数据添加到数据库使用
        this.id = id;
        this.time = time;
        this.text = text;
        this.type = type;
        this.path = path;
        this.Top = top;
    }
    public item(String time, String text,boolean type,String path){//本地添加使用
        this.time = time;
        this.text = text;
        this.type = type;
        this.path = path;
        this.id = 0;
        this.Top = false;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getitemType(){
        return type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isTop() {
        return Top;
    }

    public void setTop(boolean top) {
        Top = top;
    }
}

