package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.CertificateBean;
import com.zhixing.work.zhixin.bean.EducationBgsBean;
import com.zhixing.work.zhixin.bean.WorkBgsBean;
import com.zhixing.work.zhixin.network.response.AttendanceRecordMonthResult;
import com.zhixing.work.zhixin.util.ZxTextUtils;

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
        notifyDataSetChanged();
    }

    private static final int LATE = 0;
    private static final int NORMAL_ABSENTEEISM_MISS = 1;
    private static final int EARLY = 2;
    private static final int EDUCATION_TYPE = 3;
    private static final int WORK_TYPE = 4;
    private static final int CERTIFICATE_TYPE = 5;

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
            case WORK_TYPE:
                View workView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_or_education, parent, false);
                return new ViewWorkHolder(workView);
            case EDUCATION_TYPE:
                View educationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_or_education, parent, false);
                return new ViewEducationHolder(educationView);
            case CERTIFICATE_TYPE:
                View certificateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_or_education, parent, false);
                return new ViewCertificateHolder(certificateView);
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
            return;
        }else if(holder instanceof ViewWorkHolder){
            ViewWorkHolder viewWorkHolder = (ViewWorkHolder) holder;
            WorkBgsBean bgsBean = (WorkBgsBean) mData.get(position);
            viewWorkHolder.name.setText(ZxTextUtils.getTextWithDefault(bgsBean.getCompanyName()));
            viewWorkHolder.time.setText(bgsBean.getStartDate().substring(0,7)+"-"+bgsBean.getEndDate().substring(0,7));
            return;
        }else if(holder instanceof ViewEducationHolder){
            ViewEducationHolder viewWorkHolder = ( ViewEducationHolder) holder;
            EducationBgsBean bgsBean = (EducationBgsBean) mData.get(position);
            viewWorkHolder.name.setText(ZxTextUtils.getTextWithDefault(bgsBean.getSchool()));
            viewWorkHolder.time.setText(bgsBean.getStartDate().substring(0,7)+"-"+bgsBean.getEndDate().substring(0,7));
            return;
        }else if(holder instanceof ViewCertificateHolder){
            ViewCertificateHolder viewWorkHolder = (ViewCertificateHolder) holder;
            CertificateBean bgsBean = (CertificateBean) mData.get(position);
            viewWorkHolder.name.setText(ZxTextUtils.getTextWithDefault(bgsBean.getCertificateTitle()));
            viewWorkHolder.time.setText(bgsBean.getGraduationDate().substring(0,7));
            return;
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
        } else if (mData.get(position) instanceof WorkBgsBean) {
            return WORK_TYPE;
        } else if (mData.get(position) instanceof EducationBgsBean) {
            return EDUCATION_TYPE;
        }else if(mData.get(position) instanceof CertificateBean) {
            return CERTIFICATE_TYPE;
        }else {
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

    static class ViewWorkHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;

        ViewWorkHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewEducationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;

        ViewEducationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewCertificateHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;

        ViewCertificateHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
