package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.AttendanceRuleBean;
import com.zhixing.work.zhixin.common.WorkDayMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lhj on 2018/8/8.
 * Description: 考勤规则列表
 */

public class AttendanceRuleListAdapter extends RecyclerView.Adapter {

    private List<AttendanceRuleBean> mData;
    private ClickCallBack callBack;


    public AttendanceRuleListAdapter(List<AttendanceRuleBean> listData) {
        this.mData = listData;
    }

    public void setData(List<AttendanceRuleBean> beans) {
        this.mData = beans;
        notifyDataSetChanged();
    }

    public void setCallBack(ClickCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ClickCallBack {
        void itemClick(AttendanceRuleBean bean);

        void changeRule(AttendanceRuleBean bean);

        void changeMember(AttendanceRuleBean bean);

        void deleteRule(AttendanceRuleBean bean);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ruleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_rule_list, parent, false);
        return new RuleViewHolder(ruleView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RuleViewHolder ruleViewHolder = (RuleViewHolder) holder;
        AttendanceRuleBean bean = mData.get(position);
        ruleViewHolder.tvRuleName.setText(bean.getName());
        ruleViewHolder.tvRuleMemberTotal.setText(String.format("%s 人", String.valueOf(bean.getStaffIds().size())));
        ruleViewHolder.tvRuleTime.setText(getWorkTime(bean));
        ruleViewHolder.tvRuleWifiName.setText(getWifiName(bean));
        ruleViewHolder.tvRuleWorkTime.setText(String.format("%s ~ %s", bean.getStartTime(), bean.getEndTime()));
        ruleViewHolder.imageRuleDelete.setOnClickListener(v -> callBack.deleteRule(bean));
        ruleViewHolder.tvChangeRule.setOnClickListener(v -> callBack.changeRule(bean));
        ruleViewHolder.tvChangeMember.setOnClickListener(v -> callBack.changeMember(bean));
        ruleViewHolder.itemView.setOnClickListener(v -> callBack.itemClick(bean));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class RuleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rule_name)
        TextView tvRuleName;
        @BindView(R.id.tv_rule_member_total)
        TextView tvRuleMemberTotal;
        @BindView(R.id.tv_rule_time)
        TextView tvRuleTime;
        @BindView(R.id.tv_rule_wifi_name)
        TextView tvRuleWifiName;
        @BindView(R.id.tv_change_member)
        TextView tvChangeMember;
        @BindView(R.id.tv_change_rule)
        TextView tvChangeRule;
        @BindView(R.id.img_rule_delete)
        ImageView imageRuleDelete;
        @BindView(R.id.tv_rule_work_time)
        TextView tvRuleWorkTime;

        RuleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private String getWifiName(AttendanceRuleBean bean) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bean.getWifis().size(); i++) {
            builder.append(bean.getWifis().get(i).getName() + ",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    private String getWorkTime(AttendanceRuleBean bean) {
        StringBuilder workDayBuilder = new StringBuilder();
        int[] work = new int[]{1, 2, 4, 8, 16, 32, 64};
        for (int i = 0; i < work.length; i++) {
            if ((work[i] & bean.getWorkDay()) == work[i]) {
                workDayBuilder.append(WorkDayMenu.getName(work[i]) + ",");
            }
        }
        return workDayBuilder.substring(0, workDayBuilder.length() - 1);
    }
}
