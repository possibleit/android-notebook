
package com.example.dell.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.dell.android.MyView.MyEditText;
import com.example.dell.android.model.item;
import com.example.dell.android.util.Fulltask;
import com.example.dell.android.util.dbUtil;
//import com.lzy.imagepicker.ImagePicker;
//import com.lzy.imagepicker.bean.ImageItem;
//import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class writeActivity extends BaseActivity{
    private static final int IMAGE_PICKER = 1001;
    private boolean type = false;
    private String path = null;
    static File file;
    MyEditText editText;
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
        editText = findViewById(R.id.edit_1);
        img_1 = findViewById(R.id.img_1);
        button_add = findViewById(R.id.addimg);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(writeActivity.this, PhotoSelectorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("limit", 1 );//number是选择图片的数量
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.write,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_write_done:
               save();
               break;
        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode) {
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");//path是选择拍照或者图片的地址数组
                    //处理代码
                    path = paths.get(0);
                    img_1.setImageURI(Uri.parse(path));
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void save(){
        Date date = new Date();
        final String text = editText.getText().toString();
        if(text.equals("")){
            Toast.makeText(writeActivity.this,"内容为空",Toast.LENGTH_SHORT).show();
            return;
        }
//        final String time = date.toLocaleString();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final String time = df.format(date);
        if(path != null){
            type = true;
        }
        item i = new item(time,text,type,path);
        Toast.makeText(getApplicationContext(),time + text,Toast.LENGTH_LONG).show();
        dbUtil.insert(getApplicationContext(),i);
        new Thread(new Runnable() {
           @Override
           public void run() {
                if(path != null) {
                    file = new File(path);
                    OkHttpUtils.post()//
                            .addFile("file", "messenger_01.jpg", file)//
                            .url("http://10.0.2.2:8001/sendimg/")
    //                                .params(params)//
    //                                .headers(headers)//
                            .build()//
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.i("result", e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.i("result", response);
                                }
                            });
                }
                HashMap<String,String > map = new HashMap<>();
                map.put("name","yang");
                map.put("time",time);
                map.put("text",text);
                String s2 = Fulltask.getResult("http://10.0.2.2:8001/",map,"send/");
                Log.i("result",s2);
            }
        }).start();
    }
}