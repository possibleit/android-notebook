package com.example.dell.android.model;

public class item {
    /** 单图布局样式 */
    public static final int TYPE_SINGLE_PICTURE   = 0;
    /** 无图布局样式 */
    public static final int TYPE_NONE_PICTURE     = 1;
    private int type;
    private int id;//执行删除操作
    private String time;
    private String text;
    public item(int id, String time, String text,int type){
        this.id = id;
        this.time = time;
        this.text = text;
        this.type = type;
    }
    public item(String time, String text,int type){
        this.time = time;
        this.text = text;
        this.type = type;
        this.id = 0;
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

    public int getitemType(){
        return type;
    }
}
