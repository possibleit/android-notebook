package com.example.dell.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class previewActivity extends BaseActivity{
    private ImageView imageView;
    private Uri imgUri;
    @Override
    public int getLayoutId() {
        return R.layout.preview_activity;
    }

    @Override
    public void initData(Bundle bundle) {
        Intent intent = getIntent();
        imgUri = Uri.parse(intent.getExtras().getString("imguri"));
    }

    @Override
    public void initView() {
        imageView = findViewById(R.id.img_pre);
        imageView.setImageURI(imgUri);
    }
}
