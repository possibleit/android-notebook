package com.example.dell.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class previewActivity extends BaseActivity{
    private ImageView imageView;
    private Button pre_share;
    private Button pre_save;
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
        pre_save = findViewById(R.id.pre_save);
        pre_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回数据
            }
        });
        pre_share = findViewById(R.id.pre_share);
        pre_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });
    }
}
