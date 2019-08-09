package com.example.dell.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dell.android.adapter.itemAdapter;
import com.example.dell.android.model.Note;
import com.example.dell.android.model.item;
import com.example.dell.android.util.dbUtil;
import com.yalantis.phoenix.PullToRefreshView;
import com.youth.xframe.adapter.decoration.DividerDecoration;
import com.youth.xframe.utils.log.XLog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<Note> notes;
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
        notes = LitePal.findAll(Note.class,true);
    }

    @Override
    public void initView() {
        //初始化视图
        mPullToRefreshView = findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(refresh_listener);
        recyclerView = findViewById(R.id.listview);
        adapter = new itemAdapter(recyclerView, notes);
//        adapter.setOnItemClickListener(click_listener);
//        adapter.setOnItemLongClickListener(longclick_listener);
        /**---------------------------------------------------
         * @author : Qian
         * @Date : 23:58 2019/3/28
         * @Description : 添加了删除和置顶按钮的监听事件，以及点击与长按监听事件
         *                  由于点击事件的冲突，故在adapter中设置点击与长按事件
         --------------------------------------------------*/
        adapter.setOnDelListener(new itemAdapter.onSwipeListener() {
            @Override
            public void click(int pos){
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("title", "便签详情");
                intent.putExtra("note",notes.get(pos));
                startActivity(intent);
            }

            @Override
            public void longclick(final int pos){
                //TODO 长按事件
                //删除便签，置顶，取消
                /*final String[] items;
                if(!adapter.getItem(pos).isTop()) {
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
                                /**
                                 * 置顶 由于adapter刷新问题，显示有bug，故在更新后有读取一次数据库，重置所有item
                                 * 有些僵硬
                                 *//*
                                //TODO 暂时有问题
                                item i = adapter.getItem(pos);
                                adapter.remove(pos);
                                adapter.add(0, i);
                                dbUtil.update_top(getApplicationContext(),i.getTime(),!i.isTop());
                                itemList = dbUtil.query(getApplicationContext());
                                predo(itemList);
                                adapter.setDataLists(itemList);
                                break;
                            case 1:
                                //删除
                                dbUtil.delete(getApplicationContext(),adapter.getItem(pos).getTime());
                                adapter.remove(pos);
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
                builder.show();*/
            }


            @Override
            public void onDel(int pos) {
                dbUtil.delete(getApplicationContext(),adapter.getItem(pos).getTime());
                adapter.remove(pos);
                Toast.makeText(getApplicationContext(),"del clicked and delete position " + pos,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTop(int pos) {
//                item i = adapter.getItem(pos);
//                adapter.remove(pos);
//                adapter.add(0, i);
//                dbUtil.update_top(getApplicationContext(), i.getTime(), !i.isTop());
//                itemList = dbUtil.query(getApplicationContext());
//                predo(itemList);
                adapter.setDataLists(notes);
                Toast.makeText(getApplicationContext(),"top clicked",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addItemDecoration(new DividerDecoration(Color.parseColor("#C4C4C4"),1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //设置header
//        StickyHeaderDecoration decoration = new StickyHeaderDecoration(new headerAdapter(this));
//        decoration.setIncludeHeader(false);
//        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onResume(){
        /**
         * 期望在这里存储数据库，之后会有修改
         */
        notes = LitePal.findAll(Note.class,true);
        XLog.list(notes);
        adapter.notifyDataSetChanged();
//        Log.i("debug",notes.get(0).getTime());
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
                /**
                * 暂时有问题
                */

                break;
        }
       return super.onOptionsItemSelected(item);
    }


    PullToRefreshView.OnRefreshListener refresh_listener = new PullToRefreshView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPullToRefreshView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 读取数据库，更新所有item
                     */
                    notes = LitePal.findAll(Note.class);
                    XLog.list(notes);
                    adapter.notifyDataSetChanged();
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
