package com.example.dell.android;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.dell.android.MyView.MyEditText;
import com.example.dell.android.util.PostTask;
import com.example.dell.android.util.PostTask2;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class writeActivity extends BaseActivity{
    static File file;
    MyEditText editText;
    Button button;
    ImageView img_1;
    Button button_add;
    @Override
    public int getLayoutId(){
        return R.layout.write_layout;
    }

    @Override
    public void initData(Bundle bundle){

    }

    @Override
    public void initView(){
        button = findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Date date = new Date();
               String text = editText.getText().toString();
               String time = date.toLocaleString();
               int type = 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String,File> s = new HashMap<>();
                        s.put("file",file);
                        String s1 = PostTask2.submitData("http://10.0.2.2:8001/sendimg/",null,s);
                        Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_LONG).show();
                    }
                }).start();
                Toast.makeText(getApplicationContext(),text + time,Toast.LENGTH_LONG).show();
            }
        });
        editText = findViewById(R.id.edit_1);
        img_1 = findViewById(R.id.img_1);
        button_add = findViewById(R.id.addimg);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.setType("image/*");
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if(data == null)
            return;
        switch (requestcode){
            case 1:
//                Bitmap bm = (Bitmap) data.getExtras().get("data");
//                img_1.setImageBitmap(bm);
//                break;
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    file = new File(path);
                    Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
                }
//                img_1.setImageUri(Uri.fromFile(new File("/sdcard/test.jpg")));
                img_1.setImageURI(uri);

                break;
        }
    }
}
