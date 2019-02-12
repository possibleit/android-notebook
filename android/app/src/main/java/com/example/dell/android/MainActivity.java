package com.example.dell.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.dell.android.adapter.itemAdapter;
import com.example.dell.android.model.item;
import com.example.dell.android.util.dbUtil;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    public ArrayList<item> itemList = new ArrayList<>();
    Button button;
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle bundle) {
        //初始化数据
        itemList = dbUtil.query(getApplicationContext());
    }

    @Override
    public void initView() {
        //初始化视图
        recyclerView = findViewById(R.id.listview);
        itemAdapter adapter = new itemAdapter(recyclerView, itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        button = findViewById(R.id.bt_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), writeActivity.class);
                intent.putExtra("title", "write");
                startActivity(intent);
            }
        });
    }
}
