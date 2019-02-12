package com.example.dell.android.model;
public class item {
    /** 单图布局样式 */
    public static final int TYPE_SINGLE_PICTURE   = 0;
    /** 无图布局样式 */
    public static final int TYPE_NONE_PICTURE     = 1;
    private boolean type;//判断记录是否带有图片
    private int id;//执行删除操作
    private String time;
    private String text;
    private String path;
    public item(int id, String time, String text,boolean type,String path){
        this.id = id;
        this.time = time;
        this.text = text;
        this.type = type;
        this.path = path;
    }
    public item(String time, String text,boolean type,String path){
        this.time = time;
        this.text = text;
        this.type = type;
        this.path = path;
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
}

