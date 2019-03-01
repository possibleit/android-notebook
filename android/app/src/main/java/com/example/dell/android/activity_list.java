package com.example.dell.android;

import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dell.android.adapter.MAdapter;
import com.example.dell.android.model.check_item;

import java.util.ArrayList;
import java.util.List;

public class activity_list extends BaseActivity{
    //0单选，1多选
    public static int action_type = 0;
    private RecyclerView recyclerView;
    private List<check_item> datas;
    @Override
    public int getLayoutId(){
        return R.layout.activity_list;
    }

    @Override
    public void initData(Bundle bundle){
        datas = new ArrayList<>();
        datas.add(new check_item("阿尔巴尼亚",false));
        datas.add(new check_item("任何",false));
        datas.add(new check_item("英国",false));
    }

    @Override
    public void initView() {
       recyclerView = findViewById(R.id.list);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
       //设置adapter
        final MAdapter adapter = new MAdapter(datas);
        recyclerView.setAdapter(adapter);

        adapter.setOnRecyclerViewItemClickListener(new MAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, check_item data, int position) {
                //data是该位置的数据
                switch (action_type) {
                    case 0:
                        adapter.removeItem(null, position);
                        break;
                    case 1:
                        //多选
                        break;
                        default:
                            break;
                }
            }
        });
    }


}
