
package com.example.dell.android;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.dell.android.MyView.RichTextEditor;
import com.example.dell.android.model.item;
import com.example.dell.android.util.Fulltask;
import com.example.dell.android.util.dbUtil;
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
    static File file;
    private RichTextEditor richTextEditor;
    private Button button_add;
    private Button button_share;
    @Override
    public int getLayoutId(){
        return R.layout.write_layout;
    }

    @Override
    public void initData(Bundle bundle){

    }

    @Override
    public void initView(){
//        editText = findViewById(R.id.edit_1);
//        img_1 = findViewById(R.id.img_1);
        richTextEditor = findViewById(R.id.richEditor);
        button_share = findViewById(R.id.share);
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
        MenuInflater inflater = getMenuInflater();
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
                    String path = paths.get(0);
                    insertBitmap(path);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void save(){
        Date date = new Date();
        List<RichTextEditor.EditData> editList = richTextEditor.buildEditData();
        final HashMap<String,String> map =  dealEditData(editList);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final String time = df.format(date);
        if(!map.get("imagepath").equals("")){
            type = true;
        }
        item i = new item(time,map.get("text"),type,map.get("imagepath") );
        Toast.makeText(getApplicationContext(),time + map.get("text"),Toast.LENGTH_LONG).show();
        dbUtil.insert(getApplicationContext(),i);
        new Thread(new Runnable() {
           @Override
           public void run() {
                if(!map.get("imagepath").equals("")){
                    file = new File(map.get("imagepath"));
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
//                map.put("text",text);
                String s2 = Fulltask.getResult("http://10.0.2.2:8001/",map,"send/");
                Log.i("result",s2);
            }
        }).start();
    }


    protected HashMap<String, String> dealEditData(List<RichTextEditor.EditData> editList) {
        String sum = "";
        String path = "";
        HashMap<String,String > map = new HashMap<>();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                sum = sum + itemData.inputStr;
                Log.d("RichEditor", "commit inputStr=" + itemData.inputStr);
            } else if (itemData.imagePath != null) {
                Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);
                path = itemData.imagePath;
            }
        }
        map.put("text",sum);
        map.put("imagepath",path);
        return map;
    }

    private void insertBitmap(String imagePath) {
        richTextEditor.insertImage(imagePath);
    }
}