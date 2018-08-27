package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.AwardOrPunishmentBean;
import com.zhixing.work.zhixin.bean.CareerAwardPunishment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/24.
 * Description:奖惩列表
 */

public class AwardOrPunishmentAdapter extends RecyclerView.Adapter {

    private List<AwardOrPunishmentBean> mData;
    private Callback mCallback;
    private Context mContext;

    public interface Callback {
        void onItemClicked(CareerAwardPunishment bean);
    }

    public AwardOrPunishmentAdapter(Context context, List<AwardOrPunishmentBean> data) {
        this.mData = data;
        this.mContext = context;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setData(List<AwardOrPunishmentBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_award_or_punishment_list, parent, false);
        return new AwardPunishmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AwardPunishmentViewHolder viewHolder = (AwardPunishmentViewHolder) holder;
        AwardOrPunishmentBean bean = mData.get(position);
        viewHolder.tvTime.setText(bean.getTime());
        if (bean.getList() != null) {
            OrdinaryListAdapter adapter = new OrdinaryListAdapter(mContext, bean.getList());
            viewHolder.recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
            viewHolder.recyclerList.setHasFixedSize(true);
            viewHolder.recyclerList.setAdapter(adapter);
            adapter.setCallback(new OrdinaryListAdapter.Callback() {
                @Override
                public void onItemClicked(Object bean) {
                    mCallback.onItemClicked((CareerAwardPunishment) bean);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class AwardPunishmentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.recycler_list)
        RecyclerView recyclerList;

        AwardPunishmentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
