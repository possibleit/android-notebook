package com.example.dell.android.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database_openhelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table note (" +
            "id integer primary key autoincrement," +
            "txt text not null," +
            "time varchar(20) not null," +
            "img_or boolean not null," +
            "path varchar(50)," +
            "top boolean not null);";

    public database_openhelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
        if(newversion > oldversion){
            db.execSQL("drop table if exists note");
        }
    }
}
