package com.example.dell.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.android.R;
import com.example.dell.android.model.check_item;

import java.util.List;

import static com.example.dell.android.activity_list.action_type;

public class MAdapter extends RecyclerView.Adapter<MAdapter.ViewHolder> implements View.OnClickListener{
        private List<check_item> datas = null;
        private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

        public MAdapter(List<check_item > datas){
            this.datas = datas;
        }

        //创建新的view 被LauyoutManage所调用
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
            //获取布局填充器
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            //把布局转换为View对象，参数：布局，父容器，是否主动挂载在父容器
            View view = layoutInflater.inflate(R.layout.list, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            //将创建的view注册点击事件
            view.setOnClickListener(this);
            return vh;
        }

        //将数据与界面绑定
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder,int position){


                //普通模式
                viewHolder.textView.setText(datas.get(position).getText());
                //如果维护的列表中有，则显示选中

                //将position保存在itemview的Tag中，以便获取
                viewHolder.itemView.setTag(position);
        }

        //获取数据数量
        @Override
        public int getItemCount(){
            return datas.size();
        }

        @Override
        public void onClick(View view){
            if(onRecyclerViewItemClickListener != null){
                int position = (int) view.getTag();
                onRecyclerViewItemClickListener.onItemClick(view,datas.get(position),position);
            }
        }

        //定义完接口，添加设置接口的方法
        public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
            this.onRecyclerViewItemClickListener = listener;
        }

        public static interface OnRecyclerViewItemClickListener{
            //view点击的条目，data数据，position位置
            void onItemClick(View view,check_item datas,int position);
        }

        public void addItem(check_item content,int position){
            //存在问题
            datas.add(position,content);
            notifyItemInserted(position);
            //通知数据与界面重新绑定
            notifyItemRangeChanged(position,datas.size()-position);
        }

        public void removeItem(check_item content,int position){
            datas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,datas.size()-position);
        }


        public static class ViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public ImageView imageView;
            private ViewHolder(View view){
                super(view);
                textView = view.findViewById(R.id.re_list);

            }
        }
    }
