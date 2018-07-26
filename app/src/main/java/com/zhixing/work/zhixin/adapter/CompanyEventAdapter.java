package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.History;
import com.zhixing.work.zhixin.util.DateFormatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/26.
 * Description:
 */

public class CompanyEventAdapter extends RecyclerView.Adapter {

    private List<History> mData;
    private OnItemClickedListener itemInterface;

    public CompanyEventAdapter(List<History> data, OnItemClickedListener anInterface) {
        this.mData = data;
        this.itemInterface = anInterface;
    }

    public interface OnItemClickedListener {
        void onItemClicked(History history);
    }

    public void setData(List<History> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    private static final int RIGHT_TYPE = 0;
    private static final int LEFT_TYPE = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case RIGHT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_big_event_right, parent, false);
                break;
            case LEFT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_big_event_left, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_big_event_left, parent, false);

        }
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History history = mData.get(position);
        EventViewHolder viewHolder = (EventViewHolder) holder;
        if (mData.size() == 1 || position + 1 == mData.size()) {
            viewHolder.imgLine.setVisibility(View.GONE);
        } else {
            viewHolder.imgLine.setVisibility(View.VISIBLE);
        }
        viewHolder.tvEventTime.setText(DateFormatUtil.parseDate(history.getDate(), "yyyy-mm"));
        viewHolder.tvEventInfo.setText(history.getName());
        viewHolder.rlInfo.setOnClickListener(v -> itemInterface.onItemClicked(history));
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return RIGHT_TYPE;
        } else {
            return LEFT_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.tv_event_time)
        TextView tvEventTime;
        @BindView(R.id.tv_event_info)
        TextView tvEventInfo;
        @BindView(R.id.img_line)
        ImageView imgLine;
        @BindView(R.id.rl_info)
        RelativeLayout rlInfo;


        EventViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
