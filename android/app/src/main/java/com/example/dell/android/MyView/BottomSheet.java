package com.example.dell.android.MyView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.dell.android.R;
import com.example.dell.android.previewActivity;
import com.example.dell.android.writeActivity;

public class BottomSheet {
    private String[] items;
    private Context context;//释放
    private String title;
    private org.michaelbel.bottomsheet.BottomSheet.Builder builder = null;
    private DialogInterface.OnClickListener listener = null;

    public void setListener(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }

    public BottomSheet(String[] items, Context context,String title){
        this.items = items;
        this.title = title;
        this.context = context;
    }

    public void show(){
        builder = new org.michaelbel.bottomsheet.BottomSheet.Builder(context);
        builder.setTitle("分享");

        builder.setItems(items,listener);
        builder.setContentType(org.michaelbel.bottomsheet.BottomSheet.LIST);
        builder.setTitleTextColor(0x000000);
        builder.setItemTextColor(0x000000);
        builder.setBackgroundColor(Color.WHITE);
        builder.setDividers(true);
        builder.show();
    }

}
