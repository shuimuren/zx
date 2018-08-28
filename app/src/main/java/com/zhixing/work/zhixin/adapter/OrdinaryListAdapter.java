package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.CareerAwardPunishment;
import com.zhixing.work.zhixin.bean.HonourEventBean;
import com.zhixing.work.zhixin.bean.MonthStatisticsBean;
import com.zhixing.work.zhixin.bean.StaffCareerBean;
import com.zhixing.work.zhixin.bean.StaffStatementBean;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.AwardOrPunishmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/23.
 * Description:普通recyclerView 通用Adapter
 */

public class OrdinaryListAdapter<T> extends RecyclerView.Adapter {

    private List<T> mData;
    private Callback mCallback;
    private Context mContext;


    public interface Callback<T> {
        void onItemClicked(T bean);
    }

    public OrdinaryListAdapter(Context context, List<T> data) {
        this.mData = data;
        this.mContext = context;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_CAREER_BEAN = 1;
    private static final int TYPE_CAREER_AWARD = 2;
    private static final int TYPE_MONTH_STATISTICS = 3;
    private static final int TYPE_STAFF_STATEMENT = 4;
    private static final int TYPE_HONOUR_EVENT = 5;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CAREER_BEAN:
                View viewCareer = LayoutInflater.from(mContext).inflate(R.layout.item_career, parent, false);
                return new ViewCareerViewHolder(viewCareer);
            case TYPE_CAREER_AWARD:
                View awardView = LayoutInflater.from(mContext).inflate(R.layout.item_career_award, parent, false);
                return new AwardOrPunishmentViewHolder(awardView);
            case TYPE_MONTH_STATISTICS:
                View monthStatistics = LayoutInflater.from(mContext).inflate(R.layout.item_month_statistics, parent, false);
                return new MonthStatisticsViewHolder(monthStatistics);
            case TYPE_STAFF_STATEMENT:
                View staffStatement = LayoutInflater.from(mContext).inflate(R.layout.item_staff_statement, parent, false);
                return new StaffStatementViewHolder(staffStatement);
            case TYPE_HONOUR_EVENT:
                View honourEvent = LayoutInflater.from(mContext).inflate(R.layout.item_honour_event, parent, false);
                return new HonourEventViewHolder(honourEvent);

            default:
                View viewDefault = LayoutInflater.from(mContext).inflate(R.layout.item_default_view, parent, false);
                return new DefaultViewHolder(viewDefault);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewCareerViewHolder) {
            ViewCareerViewHolder careerHolder = (ViewCareerViewHolder) holder;
            StaffCareerBean bean = (StaffCareerBean) mData.get(position);
            careerHolder.tvCompanyName.setText(bean.getCompanyName());
            careerHolder.stars.setNumStars((int) Math.ceil((double) bean.getScort()));
            careerHolder.stars.setRating(bean.getScort());
            careerHolder.itemView.setOnClickListener(v -> mCallback.onItemClicked(bean));
            return;
        }
        if (holder instanceof AwardOrPunishmentViewHolder) {
            AwardOrPunishmentViewHolder awardOrPunishmentHolder = (AwardOrPunishmentViewHolder) holder;
            CareerAwardPunishment bean = (CareerAwardPunishment) mData.get(position);
            awardOrPunishmentHolder.tvEvent.setText(bean.getName());
            awardOrPunishmentHolder.tvTime.setText(bean.getTime());
            awardOrPunishmentHolder.awardOrPunishment.setViewStatus(bean.getGrade());
            if (mCallback != null) {
                awardOrPunishmentHolder.itemView.setOnClickListener(v -> mCallback.onItemClicked(bean));
            }
            return;
        }
        if (holder instanceof MonthStatisticsViewHolder) {
            MonthStatisticsViewHolder monthStatisticsViewHolder = (MonthStatisticsViewHolder) holder;
            MonthStatisticsBean bean = (MonthStatisticsBean) mData.get(position);
            if (bean.getStatus() != 0) {
                monthStatisticsViewHolder.starsTop.setVisibility(View.GONE);
                monthStatisticsViewHolder.starsBottom.setVisibility(View.GONE);
                monthStatisticsViewHolder.tvStatusAudit.setVisibility(View.VISIBLE);

                //已审核:0,待审核:1,待完成:2,已完成:3;
                if (bean.getStatus() == 1) {
                    monthStatisticsViewHolder.tvStatusAudit.setSelected(true);
                    monthStatisticsViewHolder.tvStatusAudit.setText("审核中");
                } else if (bean.getStatus() == 2) {
                    monthStatisticsViewHolder.tvStatusAudit.setSelected(true);
                    monthStatisticsViewHolder.tvStatusAudit.setText("待完成");
                } else {
                    monthStatisticsViewHolder.tvStatusAudit.setSelected(false);
                    monthStatisticsViewHolder.tvStatusAudit.setText("已完成");
                }

            } else {
                monthStatisticsViewHolder.starsTop.setVisibility(View.VISIBLE);
                monthStatisticsViewHolder.starsBottom.setVisibility(View.VISIBLE);
                monthStatisticsViewHolder.tvStatusAudit.setVisibility(View.GONE);
            }
            if (bean.getIntegrate() > 5) {
                monthStatisticsViewHolder.starsTop.setRating(5f);
                monthStatisticsViewHolder.starsBottom.setRating((float) (bean.getIntegrate() - 5f));
            } else {
                monthStatisticsViewHolder.starsTop.setRating(bean.getIntegrate());
            }
            if (mCallback != null) {
                monthStatisticsViewHolder.itemView.setOnClickListener(v -> mCallback.onItemClicked(bean));
            }
            monthStatisticsViewHolder.tvMonth.setText(getMonth(bean.getMonth()));
            GlideUtils.getInstance().loadImage(mContext, getViewImage(bean.getMonth()), monthStatisticsViewHolder.imgMonth);
            return;
        }
        if (holder instanceof StaffStatementViewHolder) {
            StaffStatementViewHolder statementViewHolder = (StaffStatementViewHolder) holder;
            StaffStatementBean bean = (StaffStatementBean) mData.get(position);
            GlideUtils.getInstance().loadPublicCircleWithDefault(mContext, ResourceUtils.getDrawable(R.drawable.icon_avatar), bean.getAvatar(), statementViewHolder.imgStaffAvatar);
            statementViewHolder.tvStaffName.setText(bean.getName());
            statementViewHolder.stars.setRating(bean.getScore());
            return;
        }

        if (holder instanceof HonourEventViewHolder) {
            HonourEventViewHolder honourHolder = (HonourEventViewHolder) holder;
            HonourEventBean bean = (HonourEventBean) mData.get(position);
            honourHolder.tvHonourMessage.setText(bean.getEvent());
            honourHolder.tvHonourTime.setText(bean.getTime());
            honourHolder.itemView.setOnClickListener(v -> mCallback.onItemClicked(bean));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof StaffCareerBean) {
            return TYPE_CAREER_BEAN;
        } else if (mData.get(position) instanceof CareerAwardPunishment) {
            return TYPE_CAREER_AWARD;
        } else if (mData.get(position) instanceof MonthStatisticsBean) {
            return TYPE_MONTH_STATISTICS;
        } else if (mData.get(position) instanceof StaffStatementBean) {
            return TYPE_STAFF_STATEMENT;
        } else if (mData.get(position) instanceof HonourEventBean) {
            return TYPE_HONOUR_EVENT;
        } else {
            return TYPE_DEFAULT;
        }
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {

        public DefaultViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ViewCareerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_company_logo)
        ImageView imgCompanyLogo;
        @BindView(R.id.tv_company_name)
        TextView tvCompanyName;
        @BindView(R.id.stars)
        ScaleRatingBar stars;

        ViewCareerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class AwardOrPunishmentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_event)
        TextView tvEvent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.award_or_punishment)
        AwardOrPunishmentView awardOrPunishment;

        AwardOrPunishmentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class MonthStatisticsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_month)
        ImageView imgMonth;
        @BindView(R.id.tv_month)
        TextView tvMonth;
        @BindView(R.id.tv_status_audit)
        TextView tvStatusAudit;
        @BindView(R.id.stars_top)
        ScaleRatingBar starsTop;
        @BindView(R.id.stars_bottom)
        ScaleRatingBar starsBottom;

        MonthStatisticsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class StaffStatementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_staff_avatar)
        ImageView imgStaffAvatar;
        @BindView(R.id.tv_staff_name)
        TextView tvStaffName;
        @BindView(R.id.stars)
        ScaleRatingBar stars;

        StaffStatementViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HonourEventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_honour_message)
        TextView tvHonourMessage;
        @BindView(R.id.tv_honour_time)
        TextView tvHonourTime;

        HonourEventViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private Drawable getViewImage(int index) {
        Drawable drawable = null;
        switch (index) {
            case 1:
                drawable = ResourceUtils.getDrawable(R.drawable.img_1);
                break;
            case 2:
                drawable = ResourceUtils.getDrawable(R.drawable.img_2);
                break;
            case 3:
                drawable = ResourceUtils.getDrawable(R.drawable.img_3);
                break;
            case 4:
                drawable = ResourceUtils.getDrawable(R.drawable.img_4);
                break;
            case 5:
                drawable = ResourceUtils.getDrawable(R.drawable.img_5);
                break;
            case 6:
                drawable = ResourceUtils.getDrawable(R.drawable.img_6);
                break;
            case 7:
                drawable = ResourceUtils.getDrawable(R.drawable.img_7);
                break;
            case 8:
                drawable = ResourceUtils.getDrawable(R.drawable.img_8);
                break;
            case 9:
                drawable = ResourceUtils.getDrawable(R.drawable.img_9);
                break;
            case 10:
                drawable = ResourceUtils.getDrawable(R.drawable.img_10);
                break;
            case 11:
                drawable = ResourceUtils.getDrawable(R.drawable.img_11);
                break;
            case 12:
                drawable = ResourceUtils.getDrawable(R.drawable.img_12);
                break;
            default:
                drawable = ResourceUtils.getDrawable(R.drawable.img_1);

        }
        return drawable;
    }

    private String getMonth(int index) {
        String month = null;
        switch (index) {
            case 1:
                month = "一月";
                break;
            case 2:
                month = "二月";
                break;
            case 3:
                month = "三月";
                break;
            case 4:
                month = "四月";
                break;
            case 5:
                month = "五月";
                break;
            case 6:
                month = "六月";
                break;
            case 7:
                month = "七月";
                break;
            case 8:
                month = "八月";
                break;
            case 9:
                month = "九月";
                break;
            case 10:
                month = "十月";
                break;
            case 11:
                month = "十一月";
                break;
            case 12:
                month = "十二月";
                break;


        }
        return month;
    }


}
