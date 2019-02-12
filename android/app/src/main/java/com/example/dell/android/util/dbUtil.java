package com.example.dell.android.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dell.android.Data.database_openhelper;
import com.example.dell.android.model.item;

import java.util.ArrayList;

public class dbUtil {
    public static SQLiteDatabase getDataBase(Context context){
        database_openhelper openhelper = new database_openhelper(context,"note.db",null,2);
        return openhelper.getWritableDatabase();
    }
    public static void insert(Context context, item i){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("txt",i.getText());
        values.put("time",i.getTime());
        values.put("img_or",i.getitemType());
        if(i.getitemType()){
            values.put("path",i.getPath());
        }
        db.insert("note",null,values);
        db.close();
    }

    public static ArrayList<item> query(Context context){
        ArrayList<item> itemArrayList = new ArrayList<>();
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.query("note",null,null,null,null,
                null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String txt = cursor.getString(cursor.getColumnIndex("txt"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            boolean img_or = cursor.getString(cursor.getColumnIndex("img_or")).equals("1");
            String path = cursor.getString(cursor.getColumnIndex("path"));
            item i = new item(id,time,txt,img_or,path);
            //Log.i("result",id + txt + time + img_or + path);
            itemArrayList.add(new item(id,time,txt,img_or,path));
        }
        cursor.close();
        return itemArrayList;
    }
}
