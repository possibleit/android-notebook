package com.example.dell.android.model;

public class check_item {
    private String text;
    private boolean is_Selected;

    public boolean isIs_Selected() {
        return is_Selected;
    }

    public void setIs_Selected(boolean is_Selected) {
        this.is_Selected = is_Selected;

    }

    public check_item(String s,boolean is_Selected){
        this.text = s;
        this.is_Selected = is_Selected;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
