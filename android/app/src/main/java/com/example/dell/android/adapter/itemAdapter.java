package com.example.dell.android.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.android.ImageLoader.GlideImageLoader;
import com.example.dell.android.R;
import com.example.dell.android.model.item;
//import com.squareup.picasso.Picasso;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;
import com.youth.xframe.utils.imageload.XImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class itemAdapter extends XRecyclerViewAdapter<item> {

    public itemAdapter(@NonNull RecyclerView mRecyclerView, List<item> dataLists){
        super(mRecyclerView,dataLists);
    }

    @Override
    public int getItemLayoutResId(item data,int position){
        int layoutResId = -1;
        if (data.getitemType()){
            layoutResId = R.layout.image_item;
        }else {
            layoutResId = R.layout.item;
        }
        return layoutResId;
    }

    @Override
    public void bindData(XViewHolder holder, item data, int position) {

        if(data.getitemType()){

//            holder.setText(R.id.img_item_text,data.getText())
//                    .setText(R.id.img_item_time,data.getTime())
//                    .setImageUrl(R.id.img_item_img,data.getPath());
            TextView textView = holder.getView(R.id.img_item_text);
            textView.setText(data.getText());
            TextView textView2 = holder.getView(R.id.img_item_time);
            textView2.setText(data.getTime());
            ImageView imageView = holder.getView(R.id.img_item_img);
            imageView.setImageURI(Uri.parse(data.getPath()));

//            XImage.getInstance().load(imageView,
//                            "http://p9.qhimg.com/t01c2084745dc313fd1.jpg",
//                            GlideImageLoader.circleTransform);
        }else {
            holder.setText(R.id.item_text,data.getText())
                    .setText(R.id.item_time,data.getTime());
        }
    }
}

