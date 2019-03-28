package com.example.dell.android.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private onSwipeListener mOnSwipeListener;
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
    public void bindData(final XViewHolder holder, item data, final int position) {

        if(data.getitemType()){
            LinearLayout content = holder.getView(R.id.imgitem_content);
            TextView textView = holder.getView(R.id.img_item_text);
            textView.setText(data.getText());
            TextView textView2 = holder.getView(R.id.img_item_time);
            textView2.setText(data.getTime());
            ImageView imageView = holder.getView(R.id.img_item_img);
            imageView.setImageURI(Uri.parse(data.getPath()));
            Button imgitem_btnTop = holder.getView(R.id.imgitem_btnTop);
            imgitem_btnTop.setText(data.isTop() ? "取消置顶":"置顶");
//            Button imgitem_btnUnRead = holder.getView(R.id.imgitem_btnUnRead);
            Button imgitem_btnDelete = holder.getView(R.id.imgitem_btnDelete);
            /**---------------------------------------------------
             * @author : Qian
             * @Date : 0:08 2019/3/29
             * @Description : 给swipelayout下的第一个view或viewgroup设置监听相当于是对整个view设置监听，可以
             * 代替adapter的itemonclick方法，防止冲突
             --------------------------------------------------*/
            content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Toast.makeText(getContext(), "longclig", Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
//                    return false;
                    if (null != mOnSwipeListener) {
                        //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                        //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                        //((CstSwipeDelMenu) holder.itemView).quickClose();
                        /**
                         * 使用holder.getAdapterPosition()以更新改动后的item的索引值
                         */
                        mOnSwipeListener.longclick(holder.getAdapterPosition());
                    }
                    return false;
                }
            });
            //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnSwipeListener) {
                        mOnSwipeListener.click(holder.getAdapterPosition());
                    }
                }
            });

//            imgitem_btnUnRead.setVisibility(position % 3 == 0 ? View.GONE : View.VISIBLE);

            imgitem_btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnSwipeListener) {
                        //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                        //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                        //((CstSwipeDelMenu) holder.itemView).quickClose();
                        mOnSwipeListener.onDel(holder.getAdapterPosition());
                    }
                }
            });

            //置顶：
            imgitem_btnTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=mOnSwipeListener){
                        mOnSwipeListener.onTop(holder.getAdapterPosition());
                    }
                }
            });
        }else {
            LinearLayout content = holder.getView(R.id.item_content);
            holder.setText(R.id.item_text,data.getText())
                    .setText(R.id.item_time,data.getTime());
            Button item_btnTop = holder.getView(R.id.item_btnTop);
            item_btnTop.setText(data.isTop() ? "取消置顶":"置顶");
//            Button item_btnUnRead = holder.getView(R.id.item_btnUnRead);
            Button item_btnDelete = holder.getView(R.id.item_btnDelete);

            content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Toast.makeText(getContext(), "longclig", Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
//                    return false;
                    if (null != mOnSwipeListener) {
                        //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                        //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                        //((CstSwipeDelMenu) holder.itemView).quickClose();
                        mOnSwipeListener.longclick(holder.getAdapterPosition());
                    }
                    return false;
                }
            });

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnSwipeListener) {
                        mOnSwipeListener.click(holder.getAdapterPosition());
                    }
                }
            });

//            imgitem_btnUnRead.setVisibility(position % 3 == 0 ? View.GONE : View.VISIBLE);

            item_btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnSwipeListener) {
                        //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                        //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                        //((CstSwipeDelMenu) holder.itemView).quickClose();
                        mOnSwipeListener.onDel(holder.getAdapterPosition());
                    }
                }
            });

            //置顶：
            item_btnTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=mOnSwipeListener){
                        mOnSwipeListener.onTop(holder.getAdapterPosition());
                    }

                }
            });
        }
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void click(int pos);

        void longclick(int pos);

        void onDel(int pos);

        void onTop(int pos);
    }



    public onSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }
}

