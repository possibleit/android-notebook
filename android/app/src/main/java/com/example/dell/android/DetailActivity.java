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

import com.example.dell.android.model.item;
import com.example.dell.android.util.dbUtil;

import org.michaelbel.bottomsheet.BottomSheet;

public class DetailActivity extends BaseActivity {
    private EditText detail_text;
    private Button button;
    private ImageView detail_img;
    item i = null;
    @Override
    public int getLayoutId(){
        return R.layout.detail_layout;
    }

    @Override
    public void initData(Bundle bundle){
        Intent intent = getIntent();
        i = (item) intent.getSerializableExtra("item");
    }

    @Override
    public void initView(){
        detail_text = findViewById(R.id.detail_text);
        detail_text.setText(i.getText());
        button = findViewById(R.id.btn_shot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String[] items;
                    items = new String[]{
                            "以文字形式",
                            "以图片形式",
                            "取消",
                    };

                BottomSheet.Builder builder = new BottomSheet.Builder(DetailActivity.this);
                builder.setTitle("分享");

                builder.setItems(items,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // your code here.
                        switch (which){
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "连接分享");
                                intent.putExtra(Intent.EXTRA_TEXT, detail_text.getText());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Intent.createChooser(intent, "连接分享"));
                                break;
                            case 1:
                                Bitmap b = shot(findViewById(R.id.scr));

                                detail_img = findViewById(R.id.detail_img);
                                detail_img.setImageBitmap(b);
                                //用这种方法parse Uri会出现截出的图片背景色为黑色

                                Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), b, null,null));

                                Intent i = new Intent(DetailActivity.this,previewActivity.class);
                                i.putExtra("title", "preview");
                                i.putExtra("imguri",imageUri.toString());
                                startActivity(i);
//                                Intent shareIntent = new Intent();
//                                shareIntent.setAction(Intent.ACTION_SEND);
//                                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                                shareIntent.setType("image/*");
//                                startActivity(Intent.createChooser(shareIntent, "分享到"));
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                builder.setContentType(BottomSheet.LIST);
                builder.setTitleTextColor(0x000000);
                builder.setItemTextColor(0x000000);
                builder.setBackgroundColor(Color.WHITE);
                builder.setDividers(true);
                builder.show();
            }
        });
        if(i.isType()) {
            detail_img = findViewById(R.id.detail_img);
            detail_img.setImageURI(Uri.parse(i.getPath()));
        }

    }
    public Bitmap shot(View view) {
        /**
         * 创建一个bitmap放于画布之上进行绘制 （简直如有神助）
         */
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}
