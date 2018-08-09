package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.common.DepartmentManagerHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/18.
 * Description: 考勤选择人员 -> 组织架构列表
 */

public class AttendanceDepartmentListAdapter extends RecyclerView.Adapter {

    private List<ChildDepartmentBean> departments;
    private ItemSelectorClickedInterface selectorInterface;

    public AttendanceDepartmentListAdapter(List<ChildDepartmentBean> data) {
        this.departments = data;
    }


    public void setData(List<ChildDepartmentBean> data) {
        departments = data;
        notifyDataSetChanged();
    }

    public void setItemSelectorClickedListener(ItemSelectorClickedInterface listener) {
        this.selectorInterface = listener;
    }

    public interface ItemSelectorClickedInterface {
        void onItemNextClicked(ChildDepartmentBean bean);

        void onItemSelectorClicked(int position, ChildDepartmentBean bean);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewSelected = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_department_selected_list, parent, false);
        return new DepartmentSelectorViewHolder(viewSelected);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DepartmentSelectorViewHolder viewHolder = (DepartmentSelectorViewHolder) holder;
        ChildDepartmentBean childBean = departments.get(position);
        viewHolder.tvDepartmentName.setText(childBean.getDepartmentName());
        if (childBean.isSelected()) {
            viewHolder.imgSelector.setSelected(true);
            viewHolder.imageMark.setVisibility(View.INVISIBLE);
            viewHolder.tvDepartmentName.setClickable(false);
        } else {
            viewHolder.imgSelector.setSelected(false);
            viewHolder.imageMark.setVisibility(View.VISIBLE);
            viewHolder.tvDepartmentName.setClickable(true);
            viewHolder.tvDepartmentName.setOnClickListener(v -> selectorInterface.onItemNextClicked(childBean));
        }
        //viewHolder.tvNumber.setText(DepartmentManagerHelper.getInstance().getTotalSetList()String.valueOf(childBean.getMemberTotal()));
        viewHolder.tvNumber.setText(String.format("%s/%s", DepartmentManagerHelper.getInstance().getDepartmentSelectedTotal(childBean.getDepartmentId()),
                DepartmentManagerHelper.getInstance().getStaffTotalByDepartmentId(childBean.getDepartmentId())));
        viewHolder.imgSelector.setOnClickListener(v -> selectorInterface.onItemSelectorClicked(position, childBean));

    }

    @Override
    public int getItemCount() {
        return departments.size();
    }


    static class DepartmentSelectorViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_selector)
        ImageView imgSelector;
        @BindView(R.id.tv_department_name)
        TextView tvDepartmentName;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.ll_department)
        LinearLayout llDepartment;
        @BindView(R.id.img_mark)
        ImageView imageMark;

        DepartmentSelectorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
