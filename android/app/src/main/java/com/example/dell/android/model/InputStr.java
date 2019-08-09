package com.example.dell.android.model;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class InputStr extends LitePalSupport implements Serializable {

    private int id;

    @Column(nullable = false)
    private String text;

    private String mode;
    private int order;

    @Column(nullable = false)
    private Note note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

//    public enum Mode{
//        /*
//        unit
//         */
//        TEXT("TEXT"),
//        IMGPATH("IMGPATH");
//
//        private String mode;
//
//        Mode(String unit) {
//            this.mode = unit;
//        }
//    }

    @Override
    public String toString() {
        return "InputStr{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", mode=" + mode +
                ", order=" + order +
                ", note=" + note +
                '}';
    }
}
