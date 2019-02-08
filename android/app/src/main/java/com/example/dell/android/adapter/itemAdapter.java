package com.example.dell.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.dell.android.R;
import com.example.dell.android.model.item;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.List;

public class itemAdapter extends XRecyclerViewAdapter<item> {

    public itemAdapter(@NonNull RecyclerView mRecyclerView, List<item> dataLists){
        super(mRecyclerView,dataLists);
    }

    @Override
    public int getItemLayoutResId(item data,int position){
        int layoutResId = -1;
        switch (data.getitemType()){
            case item.TYPE_NONE_PICTURE:
                layoutResId = R.layout.item;
                break;
            case item.TYPE_SINGLE_PICTURE:
                layoutResId = R.layout.image_item;
                break;
        }
        return layoutResId;
    }

    @Override
    public void bindData(XViewHolder holder, item data, int position) {
        switch (data.getitemType()){
            case item.TYPE_NONE_PICTURE:
                holder.setText(R.id.item_text, data.getText())
                .setText(R.id.item_time,data.getTime());
                break;
            case item.TYPE_SINGLE_PICTURE:

                break;
        }
    }
}
