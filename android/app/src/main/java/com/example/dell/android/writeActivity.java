
package com.example.dell.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dell.android.MyView.RichTextEditor;
import com.example.dell.android.model.InputStr;
import com.example.dell.android.model.Note;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class writeActivity extends BaseActivity{

    private RichTextEditor et_new_content;
    private int is_click = 0;
    private static final int IMAGE_PICKER = 1001;
    private boolean type = false;
    static File file;
    private RichTextEditor richTextEditor;
    private Button button_add;
    private Button button_share;
    private HashMap<String,String> map = null;
    @Override
    public int getLayoutId(){
        return R.layout.write_layout;
    }

    @Override
    public void initData(Bundle bundle){

    }

    @Override
    public void initView(){
        richTextEditor = findViewById(R.id.richEditor);
        button_share = findViewById(R.id.share);
        button_share.setClickable(false);
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items;
                items = new String[]{
                        "以文字形式",
                        "以图片形式",
                        "取消",
                };
                com.example.dell.android.MyView.BottomSheet bottomSheet = new com.example.dell.android.MyView.BottomSheet(items,writeActivity.this,"分享");
                bottomSheet.setListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "连接分享");
                                        intent.putExtra(Intent.EXTRA_TEXT, map.get("text"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(Intent.createChooser(intent, "连接分享"));
                                        break;
                                    case 1:
                                        Bitmap b = shot2(findViewById(R.id.richEditor));

                                        //用这种方法parse Uri会出现截出的图片背景色为黑色

                                        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), b, null,null));

                                        Intent i = new Intent(writeActivity.this,previewActivity.class);
                                        i.putExtra("title", "preview");
                                        i.putExtra("imguri",imageUri.toString());
                                        startActivity(i);
                                        break;
                                    case 2:
                                        break;
                                    }
                                };
                        });
                bottomSheet.show();
            }
        });


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
                if (is_click == 0) {
                    save();
                    is_click = 1;
                }
               button_share.setClickable(true);
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

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final String time = df.format(date);

        Note note = new Note();
        note.setTime(time);
        note.save();

        int s = editList.size();
        for(int i = 0;i < s;i++){
            RichTextEditor.EditData editData = editList.get(i);
            if (editData.inputStr != null) {
                InputStr inputStr = new InputStr();
                Log.e("debug",editData.inputStr);
                inputStr.setText(editData.inputStr);
                inputStr.setNote(note);
                inputStr.setOrder(i);
                inputStr.setMode("TEXT");
                inputStr.save();
//                note.getTextList().add(inputStr);
            }else if(editData.imagePath != null){
                InputStr inputStr = new InputStr();
                Log.e("debug",editData.imagePath);
                inputStr.setText(editData.imagePath);
                inputStr.setNote(note);
                inputStr.setOrder(i);
                inputStr.setMode("IMG");
                inputStr.save();
//                note.getTextList().add(inputStr);
            }
        }

    }

    private void insertBitmap(String imagePath) {
        richTextEditor.insertImage(imagePath);
    }
    public Bitmap shot2(View view) {
        /**
         * 创建一个bitmap放于画布之上进行绘制
         */
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    private String getEditData() {
        StringBuilder content = new StringBuilder();
        try {
            List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
            for (RichTextEditor.EditData itemData : editList) {
                if (itemData.inputStr != null) {
                    content.append(itemData.inputStr);
                } else if (itemData.imagePath != null) {
                    content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}