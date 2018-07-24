package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/23.
 * Description:
 */

public class SelectionDepartmentAdapter extends RecyclerView.Adapter {

    private List<ChildDepartmentBean> departments;
    private ItemSelectorClickedInterface selectorInterface;

    public SelectionDepartmentAdapter(List<ChildDepartmentBean> data) {
        this.departments = data;
    }

    public void setData(List<ChildDepartmentBean> data) {
        this.departments = data;
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selector_department_selected_list, parent, false);
        return new DepartmentSelectorViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DepartmentSelectorViewHolder viewHolder = (DepartmentSelectorViewHolder) holder;
        ChildDepartmentBean childBean = departments.get(position);
        viewHolder.tvDepartmentName.setText(childBean.getDepartmentName());
        if (childBean.isSelected()) {
            viewHolder.imgSelector.setSelected(true);
        } else {
            viewHolder.imgSelector.setSelected(false);
        }
        viewHolder.tvDepartmentTotal.setText(String.valueOf(childBean.getMemberTotal()));
        viewHolder.imgSelector.setOnClickListener(v -> selectorInterface.onItemSelectorClicked(position, childBean));
        viewHolder.tvNext.setOnClickListener(v -> selectorInterface.onItemNextClicked(childBean));
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
        @BindView(R.id.tv_department_total)
        TextView tvDepartmentTotal;
        @BindView(R.id.tv_next)
        TextView tvNext;

        DepartmentSelectorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
