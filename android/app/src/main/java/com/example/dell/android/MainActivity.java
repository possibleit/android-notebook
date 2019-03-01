package com.example.dell.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dell.android.adapter.headerAdapter;
import com.example.dell.android.adapter.itemAdapter;
import com.example.dell.android.model.item;
import com.example.dell.android.util.dbUtil;
import com.yalantis.phoenix.PullToRefreshView;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.decoration.DividerDecoration;
import com.youth.xframe.adapter.decoration.StickyHeaderDecoration;
import com.youth.xframe.utils.log.XLog;

import org.michaelbel.bottomsheet.BottomSheet;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    public static ArrayList<item> itemList = new ArrayList<>();
    public static boolean top;
    public static int num = 0;
    public itemAdapter adapter;
    com.yalantis.phoenix.PullToRefreshView mPullToRefreshView;
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle bundle) {
        //初始化数据
        itemList = dbUtil.query(getApplicationContext());
//        itemList.clear();
//        itemList.add(new item(0,"2019-02-27","1",false,null,true));
//        itemList.add(new item(0,"2019-02-27","1",false,null,false));
//        itemList.add(new item(0,"2018-02-27","2",false,null,false));
//        itemList.add(new item(0,"2017-02-27","1",false,null,true));
        XLog.list(itemList);
        predo(itemList);
        XLog.list(itemList);
    }

    @Override
    public void initView() {
        //初始化视图
        mPullToRefreshView = findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(refresh_listener);
        recyclerView = findViewById(R.id.listview);
        adapter = new itemAdapter(recyclerView, itemList);
        adapter.setOnItemClickListener(click_listener);
        adapter.setOnItemLongClickListener(longclick_listener);
        recyclerView.addItemDecoration(new DividerDecoration(Color.parseColor("#C4C4C4"),1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //设置header
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(new headerAdapter(this));
        decoration.setIncludeHeader(false);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onResume(){

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent intent = new Intent(getApplicationContext(), writeActivity.class);
                intent.putExtra("title", "write");
                startActivity(intent);
               break;
            case R.id.menu_change:
                //TODO
                //全选界面
                Intent intent1 = new Intent(getApplicationContext(),activity_list.class);
                intent1.putExtra("title","list");
                startActivity(intent1);
                break;
        }
       return super.onOptionsItemSelected(item);
    }

    XRecyclerViewAdapter.OnItemClickListener click_listener = new XRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            //点击事件,进入详情界面
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("title", "便签详情");
            item i = adapter.getItem(position);
            intent.putExtra("item",i);
            startActivity(intent);
        }
    };

    XRecyclerViewAdapter.OnItemLongClickListener longclick_listener = new XRecyclerViewAdapter.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(View v, final int position) {
            //TODO 长按事件
            //删除便签，置顶，取消
            final String[] items;
            if(!adapter.getItem(position).isTop()) {
                items = new String[] {
                    "置顶",
                    "删除便签",
                    "取消",
                };
            }else {
                items = new String[] {
                    "取消置顶",
                    "删除便签",
                    "取消",
                };
            }

            BottomSheet.Builder builder = new BottomSheet.Builder(MainActivity.this);
            builder.setTitle("操作");

            builder.setItems(items,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // your code here.
                    switch (which){
                        case 0:
                            //置顶
                            //TODO 暂时有问题
                            item i = adapter.getItem(position);
                            adapter.remove(position);
                            adapter.add(0, i);
                            dbUtil.update_top(getApplicationContext(),i.getTime(),!i.isTop());
                            break;
                        case 1:
                            //删除
                            dbUtil.delete(getApplicationContext(),adapter.getItem(position).getTime());
                            adapter.remove(position);
                            break;
                        case 2:
                            break;
                    }
                }
            });
            builder.setContentType(BottomSheet.LIST);
            builder.setTitleTextColor(0xFFFF5252);
            builder.setItemTextColor(0xFFFF5252);
            builder.setBackgroundColor(Color.WHITE);
            builder.setDividers(true);
            builder.show();
            return false;
        }
    };

    PullToRefreshView.OnRefreshListener refresh_listener = new PullToRefreshView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPullToRefreshView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO
                    //同步
                    mPullToRefreshView.setRefreshing(false);
                }
            }, 1000);
        }
    };

    public void predo(ArrayList<item> list){
       num = 0;
        for(int i=0;i<itemList.size();i++){//将置顶的item放在list前面，方便后续操作
            if(itemList.get(i).isTop()){
               itemList.add(0,itemList.remove(i));
               num++;
            }
        }
    }
}
