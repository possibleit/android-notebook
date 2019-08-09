package com.example.dell.android.model;

import android.util.Log;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Note extends LitePalSupport implements Serializable {

    private int id;

    @Column(unique = true, nullable = false)
    private String time;

    @Column(defaultValue = "false")
    private boolean isTop;

    private List<InputStr> TextList = new ArrayList<InputStr>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public List<InputStr> getTextList() {
        return TextList;
    }

    public void setTextList(List<InputStr> textList) {
        TextList = textList;
    }

    public String getTopText(){
        for(InputStr inputStr : TextList){
            Log.e("debug",inputStr.getText());
            if (inputStr.getMode().equals("TEXT")){
                return inputStr.getText();
            }
        }
        return "null";
    }

    public String getTopImagePath(){
        for(InputStr inputStr : TextList){
            Log.e("debug",inputStr.getText());
            if (inputStr.getMode().equals("IMG")){
                return inputStr.getText();
            }
        }
        return "";
    }

    public Boolean hasImage(){
        for(InputStr inputStr : TextList){
            if (inputStr.getMode().equals("IMG")){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", isTop=" + isTop +
                ", TextList=" + TextList.toString() +
                '}';
    }
}
