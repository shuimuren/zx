package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.MessageTestBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/13.
 * Description:
 */

public class ChatMessageAdapter extends RecyclerView.Adapter {

    private List<MessageTestBean> mData;
    private Context mContext;
    private  ItemClickedInterface clickedInterface;

    public static final int item = 0;
    public static final int bottom = 1;
    public interface ItemClickedInterface{
        void itemClicked(String name);
    }

    public void setItemClickedListener(ItemClickedInterface listener){
        clickedInterface = listener;
    }

    public ChatMessageAdapter(Context context, List<MessageTestBean> list) {
        this.mData = list;
        this.mContext = context;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if(position == mData.size()){
            return bottom;
        }else {
            return item;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case item:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
                return new ChatViewHolder(view);
            case bottom:
                View bottomView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message_bottom, parent, false);
                return new ChatBottomViewHolder(bottomView);
                default:
                    View defaultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message_bottom, parent, false);
                    return new ChatBottomViewHolder(defaultView);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ChatBottomViewHolder){

        }else {
            MessageTestBean bean = mData.get(position);
            ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
            if (bean.getUnRead() > 0) {
                chatViewHolder.unReadNumber.setVisibility(View.VISIBLE);
                chatViewHolder.unReadNumber.setText(String.valueOf(bean.getUnRead()));
            } else {
                chatViewHolder.unReadNumber.setVisibility(View.GONE);
            }

            chatViewHolder.userName.setText(bean.getName());
            chatViewHolder.messageDetail.setText(bean.getMessage());
            if (bean.getHead() == 0) {
                Glide.with(mContext).load(R.drawable.boss).into(chatViewHolder.avatar);
            } else if (bean.getHead() == 1) {
                Glide.with(mContext).load(R.drawable.img_man).into(chatViewHolder.avatar);
            } else if (bean.getHead() == 2) {
                Glide.with(mContext).load(R.drawable.on_job).into(chatViewHolder.avatar);
            }else if(bean.getHead() == 3){
                Glide.with(mContext).load(R.drawable.student).into(chatViewHolder.avatar);
            }else if(bean.getHead() == 4){
                Glide.with(mContext).load(R.drawable.student).into(chatViewHolder.avatar);
            }else{
                Glide.with(mContext).load(R.drawable.img_women).into(chatViewHolder.avatar);
            }

            chatViewHolder.llChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedInterface.itemClicked(bean.getName());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.unReadNumber)
        TextView unReadNumber;
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.messageDetail)
        TextView messageDetail;
        @BindView(R.id.llChat)
        LinearLayout llChat;

        ChatViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ChatBottomViewHolder extends RecyclerView.ViewHolder{

        public ChatBottomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
