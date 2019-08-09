package com.example.dell.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.dell.android.MyView.RichTextEditor;
import com.example.dell.android.model.Note;
import com.example.dell.android.model.item;
import com.example.dell.android.util.dbUtil;

import org.michaelbel.bottomsheet.BottomSheet;

import static com.example.dell.android.MainActivity.itemList;

public class DetailActivity extends BaseActivity {
    private Note note;

    private Button button_share,button_del;
    private com.sendtion.xrichtext.RichTextEditor editor;
    item i = null;
    @Override
    public int getLayoutId(){
        return R.layout.detail_layout;
    }

    @Override
    public void initData(Bundle bundle){
        Intent intent = getIntent();
        //获取里面的Persion里面的数据
        note = (Note) intent.getSerializableExtra("note");
    }

    @Override
    public void initView() {

        editor = findViewById(R.id.et_new_content);
        button_del = findViewById(R.id.btn_delete);
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(i);
                dbUtil.delete(getApplicationContext(),i.getTime());
                finish();
            }
        });
        button_share = findViewById(R.id.btn_shot);
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String[] items;
                items = new String[]{
                        "以文字形式",
                        "以图片形式",
                        "取消",
                };
                com.example.dell.android.MyView.BottomSheet bottomSheet = new com.example.dell.android.MyView.BottomSheet(items, DetailActivity.this, "分享");
                bottomSheet.setListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "连接分享");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Intent.createChooser(intent, "连接分享"));
                                break;
                            case 1:
                                Bitmap b = shot(findViewById(R.id.scr));


                                //用这种方法parse Uri会出现截出的图片背景色为黑色

                                Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), b, null, null));

                                Intent i = new Intent(DetailActivity.this, previewActivity.class);
                                i.putExtra("title", "preview");
                                i.putExtra("imguri", imageUri.toString());
                                startActivity(i);
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                bottomSheet.show();


            }
        });

    }

    public Bitmap shot(View view) {
        /**
         * 创建一个bitmap放于画布之上进行绘制 （简直如有神助）
         */
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
        }
    }


