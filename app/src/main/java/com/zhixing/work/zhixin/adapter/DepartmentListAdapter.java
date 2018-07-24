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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/18.
 * Description: 组织架构列表
 */

public class DepartmentListAdapter extends RecyclerView.Adapter {

    private List<ChildDepartmentBean> departments;
    private ItemClickedInterface itemClickedInterface;
    private ItemSelectorClickedInterface selectorInterface;

    public DepartmentListAdapter(List<ChildDepartmentBean> data) {
        this.departments = data;
    }

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_SELECT = 1;

    public void setData(List<ChildDepartmentBean> data) {
        departments.clear();
        departments = data;
        notifyDataSetChanged();
    }

    public void setItemSelectorClickedListener(ItemSelectorClickedInterface listener) {
        this.selectorInterface = listener;
    }

    public void setItemClickedListener(ItemClickedInterface listener) {
        this.itemClickedInterface = listener;
    }

    public interface ItemClickedInterface {
        void onItemClicked(ChildDepartmentBean bean);
    }

    public interface ItemSelectorClickedInterface {
        void onItemNextClicked(ChildDepartmentBean bean);

        void onItemSelectorClicked(int position, ChildDepartmentBean bean);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_list, parent, false);
                return new DepartmentViewHolder(view);
            case TYPE_SELECT:
                View viewSelected = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_selected_list, parent, false);
                return new DepartmentSelectorViewHolder(viewSelected);
            default:
                View viewDef = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_list, parent, false);
                return new DepartmentViewHolder(viewDef);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DepartmentViewHolder) {
            DepartmentViewHolder viewHolder = (DepartmentViewHolder) holder;
            ChildDepartmentBean bean = departments.get(position);
            viewHolder.tvDepartmentName.setText(bean.getDepartmentName());
            viewHolder.tvNumber.setText(String.valueOf(bean.getMemberTotal()));
            viewHolder.llDepartment.setOnClickListener(v -> itemClickedInterface.onItemClicked(bean));
        } else if (holder instanceof DepartmentSelectorViewHolder) {
            DepartmentSelectorViewHolder viewHolder = (DepartmentSelectorViewHolder) holder;
            ChildDepartmentBean childBean = departments.get(position);
            viewHolder.tvDepartmentName.setText(childBean.getDepartmentName());
            if (childBean.isSelected()) {
                viewHolder.imgSelector.setSelected(true);
            } else {
                viewHolder.imgSelector.setSelected(false);
            }
            viewHolder.imgSelector.setOnClickListener(v -> selectorInterface.onItemSelectorClicked(position, childBean));
            viewHolder.tvNext.setOnClickListener(v -> selectorInterface.onItemNextClicked(childBean));

        }

    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (departments.get(position).getTypeSelected() == 1) {
            return TYPE_SELECT;
        } else {
            return TYPE_NORMAL;
        }
    }

    static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_department_name)
        TextView tvDepartmentName;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.ll_department)
        LinearLayout llDepartment;

        DepartmentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class DepartmentSelectorViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_selector)
        ImageView imgSelector;
        @BindView(R.id.tv_department_name)
        TextView tvDepartmentName;
        @BindView(R.id.tv_next)
        TextView tvNext;
        @BindView(R.id.ll_department)
        LinearLayout llDepartment;

        DepartmentSelectorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
