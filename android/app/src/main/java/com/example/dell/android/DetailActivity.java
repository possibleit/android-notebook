package com.example.dell.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dell.android.model.item;

public class DetailActivity extends BaseActivity {
    private EditText detail_text;
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
        if(i.isType()) {
            detail_img = findViewById(R.id.detail_img);
            detail_img.setImageURI(Uri.parse(i.getPath()));
        }

    }
}
