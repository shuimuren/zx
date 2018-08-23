package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.HrBean;
import com.zhixing.work.zhixin.bean.LeaveStaffBean;
import com.zhixing.work.zhixin.bean.NewMemberBean;
import com.zhixing.work.zhixin.bean.StatisticsMonthDataBean;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.ZxTextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/3.
 * Description:
 */

public class ListRecycleViewAdapter<T> extends RecyclerView.Adapter {

    private boolean mIsNoMore = false;
    private boolean mIsEmpty = false;
    private List<T> mData;
    private Callback mCallback;
    private Context mContext;

    private static final int TYPE_FOOTER = 99;
    private static final int TYPE_ATTENDANCE_MONTH = 0;
    private static final int TYPE_NEW_MEMBER_BEAN = 1;
    private static final int TYPE_LEAVE_STAFF_BEAN = 2;
    private static final int TYPE_HR_OR_MANAGER_BEAN = 3;

    public ListRecycleViewAdapter(Context context, List<T> data, Callback back) {
        this.mContext = context;
        this.mCallback = back;
        this.mData = data;
    }

    public interface Callback<T> {
        void onItemClicked(T bean);

        void onDeleteClicked(T bean);

        boolean isPaged();

        void onLoadMoreButtonClicked();
    }

    public void setIsNoMore(boolean isNoMore) {
        mIsNoMore = isNoMore;
    }

    public void setData(List<T> data) {
        mData = data;
        mIsEmpty = data.isEmpty();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ATTENDANCE_MONTH:
                View viewMonth = LayoutInflater.from(mContext).inflate(R.layout.item_attendance_month, parent, false);
                return new ViewMonthHolder(viewMonth);
            case TYPE_NEW_MEMBER_BEAN:
                View viewNewMember = LayoutInflater.from(mContext).inflate(R.layout.item_new_member, parent, false);
                return new ViewNewMemberHolder(viewNewMember);
            case TYPE_LEAVE_STAFF_BEAN:
                View viewLeave = LayoutInflater.from(mContext).inflate(R.layout.item_leave_staff, parent, false);
                return new ViewLeaveStaffHolder(viewLeave);
            case TYPE_HR_OR_MANAGER_BEAN:
                View viewManager = LayoutInflater.from(mContext).inflate(R.layout.item_hr_or_manager, parent, false);
                return new ViewManagerHolder(viewManager);
            default:
                View viewFooter = LayoutInflater.from(mContext).inflate(R.layout.item_list_footer, parent, false);
                return new ViewFooterHolder(viewFooter);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewMonthHolder) {
            StatisticsMonthDataBean bean = (StatisticsMonthDataBean) mData.get(position);
            ViewMonthHolder viewHolder = (ViewMonthHolder) holder;
            GlideUtils.getInstance().loadPublicRoundTransformWithDefault(mContext, ResourceUtils.getDrawable(R.drawable.icon_avatar), bean.getAvatar(), viewHolder.imgAvatar);
            viewHolder.tvName.setText(bean.getRealName());
            viewHolder.tvSection.setText(ZxTextUtils.getTextWithDefault(bean.getDepartmentName()));
            viewHolder.tvTimes.setText(String.format("%s次", String.valueOf(bean.getCount())));
            if (bean.getLateMinutes() > 0) {
                viewHolder.tvMinute.setText(String.format("%s分钟", String.valueOf(bean.getLateMinutes())));
                viewHolder.tvMinute.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvMinute.setVisibility(View.GONE);
            }

            viewHolder.itemView.setOnClickListener(v -> mCallback.onItemClicked(bean));
            return;
        }
        if (holder instanceof ViewNewMemberHolder) {
            ViewNewMemberHolder viewNewMemberHolder = (ViewNewMemberHolder) holder;
            NewMemberBean bean = (NewMemberBean) mData.get(position);
            GlideUtils.getInstance().loadGlideRoundTransform(mContext, ResourceUtils.getDrawable(R.drawable.icon_avatar),
                    bean.getAvatar(), viewNewMemberHolder.imgAvatar);
            viewNewMemberHolder.tvName.setText(ZxTextUtils.getTextWithDefault(bean.getRealName()));
            viewNewMemberHolder.tvPhone.setText(ZxTextUtils.getTextWithDefault(bean.getPhoneNum()));
            if (bean.getAuditStatus() == ResultConstant.AUDIT_STATUS_PASS) {
                viewNewMemberHolder.tvStatus.setText(ResourceUtils.getString(R.string.audit_member_passed));
            } else if (bean.getAuditStatus() == ResultConstant.AUDIT_STATUS_REJECT) {
                viewNewMemberHolder.tvStatus.setText(ResourceUtils.getString(R.string.audit_member_reject));
            } else {
                viewNewMemberHolder.tvStatus.setText(ResourceUtils.getString(R.string.audit_member_wait));
            }
            viewNewMemberHolder.itemView.setOnClickListener(v -> mCallback.onItemClicked(bean));
            return;
        }

        if (holder instanceof ViewLeaveStaffHolder) {
            ViewLeaveStaffHolder leaveStaffHolder = (ViewLeaveStaffHolder) holder;
            LeaveStaffBean bean = (LeaveStaffBean) mData.get(position);
            GlideUtils.getInstance().loadGlideRoundTransform(mContext, ResourceUtils.getDrawable(R.drawable.icon_avatar),
                    bean.getAvatar(), leaveStaffHolder.imgAvatar);
            // leaveStaffHolder.tvLeaveTime.setText();
            leaveStaffHolder.tvName.setText(ZxTextUtils.getTextWithDefault(bean.getRealName()));
            leaveStaffHolder.tvDepartment.setText(ZxTextUtils.getTextWithDefault(bean.getDepartmentName()));
            leaveStaffHolder.tvLeaveTime.setText(String.format("离职日期：%s", bean.getDimissionTime().substring(0, 10)));
            leaveStaffHolder.itemView.setOnClickListener(v -> mCallback.onItemClicked(bean));
            return;
        }

        if (holder instanceof ViewManagerHolder) {
            ViewManagerHolder managerHolder = (ViewManagerHolder) holder;
            HrBean bean = (HrBean) mData.get(position);
            GlideUtils.getInstance().loadGlideRoundTransform(mContext, ResourceUtils.getDrawable(R.drawable.icon_avatar),
                    bean.getAvatar(), managerHolder.imageAvatar);
            managerHolder.tvUserName.setText(ZxTextUtils.getTextWithDefault(bean.getRealName()));
            managerHolder.btnDelete.setOnClickListener(v -> mCallback.onDeleteClicked(bean));


        }

        if (holder instanceof ViewFooterHolder) {
            ViewFooterHolder footerHolder = (ViewFooterHolder) holder;
            String desc = "";
            if (mIsEmpty) {
                desc = ResourceUtils.getString(R.string.list_item_empty);
            } else if (mIsNoMore) {
                desc = ResourceUtils.getString(R.string.list_item_no_more);
            } else {
                desc = ResourceUtils.getString(R.string.list_item_loading);
                footerHolder.itemFooter.setOnClickListener(v -> mCallback.onLoadMoreButtonClicked());
            }
            footerHolder.itemFooter.setText(desc);
        }
    }

    @Override
    public int getItemCount() {
        if (mCallback.isPaged()) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mCallback.isPaged() && position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            if (mData.get(position) instanceof StatisticsMonthDataBean) {
                return TYPE_ATTENDANCE_MONTH;
            }
            if (mData.get(position) instanceof NewMemberBean) {
                return TYPE_NEW_MEMBER_BEAN;
            }
            if (mData.get(position) instanceof LeaveStaffBean) {
                return TYPE_LEAVE_STAFF_BEAN;
            }
            if (mData.get(position) instanceof HrBean) {
                return TYPE_HR_OR_MANAGER_BEAN;
            }
        }
        return TYPE_FOOTER;
    }

    static class ViewMonthHolder extends BaseViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_section)
        TextView tvSection;
        @BindView(R.id.img_next)
        ImageView imgNext;
        @BindView(R.id.tv_times)
        TextView tvTimes;
        @BindView(R.id.tv_minute)
        TextView tvMinute;

        ViewMonthHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewFooterHolder extends BaseViewHolder {
        @BindView(R.id.item_footer)
        TextView itemFooter;

        ViewFooterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewNewMemberHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        ViewNewMemberHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewLeaveStaffHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time_total)
        TextView tvTimeTotal;
        @BindView(R.id.tv_department)
        TextView tvDepartment;
        @BindView(R.id.tv_leave_time)
        TextView tvLeaveTime;

        ViewLeaveStaffHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewManagerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_avatar)
        ImageView imageAvatar;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.btn_delete)
        Button btnDelete;

        ViewManagerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
