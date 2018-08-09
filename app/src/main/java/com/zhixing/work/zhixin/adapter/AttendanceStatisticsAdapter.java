package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.network.response.AttendanceRecordMonthResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/2.
 * Description:
 */

public class AttendanceStatisticsAdapter<T> extends RecyclerView.Adapter {


    private List<T> mData;

    public void setData(List<T> data) {
        this.mData = data;
    }

    private static final int LATE = 0;
    private static final int NORMAL_ABSENTEEISM_MISS = 1;
    private static final int EARLY = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case LATE:
            case EARLY:
                View viewEarlyOrLate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_early_or_late, parent, false);
                return new ViewEarlyOrLateHolder(viewEarlyOrLate);
            case NORMAL_ABSENTEEISM_MISS:
                View viewNormal = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal_or_miss, parent, false);
                return new ViewNormalOrMissHolder(viewNormal);
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal_or_miss, parent, false);
                return new ViewNormalOrMissHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewEarlyOrLateHolder) {
            ViewEarlyOrLateHolder viewHolder = (ViewEarlyOrLateHolder) holder;
            if (mData.get(position) instanceof AttendanceRecordMonthResult.ContentBean.LateDaysBean) {
                viewHolder.tvDate.setText(((AttendanceRecordMonthResult.ContentBean.LateDaysBean) mData.get(position)).getDate());
                viewHolder.tvMinutes.setText(String.format("上班迟到%s分钟", ((AttendanceRecordMonthResult.ContentBean.LateDaysBean) mData.get(position)).getMinutes()));
            } else {
                viewHolder.tvDate.setText(((AttendanceRecordMonthResult.ContentBean.EarlyDaysBean) mData.get(position)).getDate());
                viewHolder.tvMinutes.setText(String.format("上班早退%s分钟", ((AttendanceRecordMonthResult.ContentBean.EarlyDaysBean) mData.get(position)).getMinutes()));
            }

        } else {
            ViewNormalOrMissHolder viewHolder = (ViewNormalOrMissHolder) holder;
            viewHolder.tvDate.setText((String) mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof String) {
            return NORMAL_ABSENTEEISM_MISS;
        } else if (mData.get(position) instanceof AttendanceRecordMonthResult.ContentBean.LateDaysBean) {
            return LATE;
        } else {
            return EARLY;
        }
    }

    static class ViewEarlyOrLateHolder extends BaseViewHolder {
        @BindView(R.id.tv_Date)
        TextView tvDate;
        @BindView(R.id.tv_minutes)
        TextView tvMinutes;

        ViewEarlyOrLateHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewNormalOrMissHolder extends BaseViewHolder {
        @BindView(R.id.tv_Date)
        TextView tvDate;
        @BindView(R.id.tv_minutes)
        TextView tvMinutes;

        ViewNormalOrMissHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
