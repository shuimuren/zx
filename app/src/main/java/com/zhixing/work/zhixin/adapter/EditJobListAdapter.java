package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.CertificateBean;
import com.zhixing.work.zhixin.bean.EducationBgsBean;
import com.zhixing.work.zhixin.bean.WorkBgsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/16.
 * Description: 工作编辑
 */

public class EditJobListAdapter<T> extends RecyclerView.Adapter {

    private List<T> mData;
    private boolean mIsEdit;
    private CallBack mCallBack;

    public EditJobListAdapter(List<T> data) {
        this.mData = data;
    }

    public void setData(boolean isEdit, List<T> data) {
        this.mData = data;
        this.mIsEdit = isEdit;
        notifyDataSetChanged();
    }

    public void setItemCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public interface CallBack<T> {
        void itemClicked(T t);

        void deleteClicked(T t);
    }

    private static final int EMPTY_TYPE = 0;
    private static final int CERTIFICATE_TYPE = 1;
    private static final int EDUCATION_TYPE = 2;
    private static final int WORK_TYPE = 3;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_TYPE:
                View viewEmpty = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_list_empty, parent, false);
                return new EmptyViewHolder(viewEmpty);
            case WORK_TYPE:
                View viewWork = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_work_edit, parent, false);
                return new WorkViewHolder(viewWork);
            case EDUCATION_TYPE:
                View viewEducation = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_work_edit,parent,false);
                return new EducationViewHolder(viewEducation);
            case CERTIFICATE_TYPE:
                View viewCertificate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_work_edit,parent,false);
                return new CertificateViewHolder(viewCertificate);
            default:
                View viewDefault = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_list_empty, parent, false);
                return new EmptyViewHolder(viewDefault);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WorkViewHolder) {
            WorkViewHolder viewHolder = (WorkViewHolder) holder;
            WorkBgsBean bgsBean = (WorkBgsBean) mData.get(position);
            if (mIsEdit) {
                viewHolder.btnDelete.setVisibility(View.VISIBLE);
                viewHolder.imgMark.setVisibility(View.GONE);
            } else {
                viewHolder.btnDelete.setVisibility(View.GONE);
                viewHolder.imgMark.setVisibility(View.VISIBLE);
            }
            viewHolder.tvName.setText(bgsBean.getCompanyName());
            viewHolder.tvTime.setText(bgsBean.getStartDate().substring(0,7)+"-"+bgsBean.getEndDate().substring(0,7));
            viewHolder.itemView.setOnClickListener(v -> mCallBack.itemClicked(bgsBean));
            viewHolder.btnDelete.setOnClickListener(v -> mCallBack.deleteClicked(bgsBean));
            return;
        }else  if(holder instanceof EducationViewHolder){
            EducationViewHolder viewHolder = (EducationViewHolder) holder;
            EducationBgsBean bgsBean = (EducationBgsBean) mData.get(position);
            if (mIsEdit) {
                viewHolder.btnDelete.setVisibility(View.VISIBLE);
                viewHolder.imgMark.setVisibility(View.GONE);
            } else {
                viewHolder.btnDelete.setVisibility(View.GONE);
                viewHolder.imgMark.setVisibility(View.VISIBLE);
            }
            viewHolder.tvName.setText(bgsBean.getSchool());
            viewHolder.tvTime.setText(bgsBean.getStartDate().substring(0,7)+"-"+bgsBean.getEndDate().substring(0,7));
            viewHolder.itemView.setOnClickListener(v -> mCallBack.itemClicked(bgsBean));
            viewHolder.btnDelete.setOnClickListener(v -> mCallBack.deleteClicked(bgsBean));
            return;
        }else if(holder instanceof CertificateViewHolder){
            CertificateViewHolder viewHolder = (CertificateViewHolder) holder;
            CertificateBean bgsBean = (CertificateBean) mData.get(position);
            if (mIsEdit) {
                viewHolder.btnDelete.setVisibility(View.VISIBLE);
                viewHolder.imgMark.setVisibility(View.GONE);
            } else {
                viewHolder.btnDelete.setVisibility(View.GONE);
                viewHolder.imgMark.setVisibility(View.VISIBLE);
            }
            viewHolder.tvName.setText(bgsBean.getCertificateTitle());
            viewHolder.tvTime.setText(bgsBean.getGraduationDate().substring(0,7));
            viewHolder.itemView.setOnClickListener(v -> mCallBack.itemClicked(bgsBean));
            viewHolder.btnDelete.setOnClickListener(v -> mCallBack.deleteClicked(bgsBean));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof WorkBgsBean) {
            return WORK_TYPE;
        } else if (mData.get(position) instanceof EducationBgsBean) {
            return EDUCATION_TYPE;
        } else if (mData.get(position) instanceof CertificateBean) {
            return CERTIFICATE_TYPE;
        } else {
            return EMPTY_TYPE;
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class WorkViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.img_mark)
        ImageView imgMark;

        WorkViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    //
    static class EducationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.img_mark)
        ImageView imgMark;

        EducationViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //CertificateViewHolder
    static class CertificateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.img_mark)
        ImageView imgMark;

        CertificateViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
