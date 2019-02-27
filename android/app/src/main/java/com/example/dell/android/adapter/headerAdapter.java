package com.example.dell.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.android.R;
import com.youth.xframe.adapter.decoration.StickyHeaderDecoration;

import static com.example.dell.android.MainActivity.itemList;
import static com.example.dell.android.MainActivity.num;


public class headerAdapter implements StickyHeaderDecoration.IStickyHeaderAdapter<headerAdapter.HeaderHolder> {

    private LayoutInflater mInflater;
    private String year = "";
    private long i = 0;

    public headerAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public long getHeaderId(int position){
        year = "";
        String y = itemList.get(position).getTime().substring(0,4);
        if (position < num) {
            i = 0;
        } else {
            i = Integer.parseInt(y);
            year = y;
        }
        Log.i("debug", position + "#####" + i);
        return i;
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_item,parent,false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position){
       Log.i("debug","$$$$$" + position + "$$$$");
        if (position == 0 && year.equals("")){
            viewholder.header.setText("置顶");
        }else {
            viewholder.header.setText(year);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }
}
