package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.DepartmentMemberBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/19.
 * Description:
 */

public class DepartmentMemberTitleAdapter extends RecyclerView.Adapter {

    private List<DepartmentMemberBean> mData;
    private ItemClickedInterface mItemClickedInterface;

    public DepartmentMemberTitleAdapter(List<DepartmentMemberBean> data) {
        this.mData = data;
    }

    public void setData(List<DepartmentMemberBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setItemClickedListener(ItemClickedInterface listener) {
        this.mItemClickedInterface = listener;
    }

    public interface ItemClickedInterface {
        void onItemClicked(int position, DepartmentMemberBean bean);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_member_title, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TitleViewHolder viewHolder = (TitleViewHolder) holder;
        DepartmentMemberBean bean = mData.get(position);
        viewHolder.textTitle.setText(mData.get(position).getDepartmentName());
        if (position == mData.size() - 1) {
            viewHolder.imgNext.setVisibility(View.GONE);
            viewHolder.textTitle.setEnabled(false);
        } else {
            viewHolder.imgNext.setVisibility(View.VISIBLE);
            viewHolder.textTitle.setEnabled(true);
        }

        viewHolder.textTitle.setOnClickListener(v -> mItemClickedInterface.onItemClicked(position, bean));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textTitle)
        TextView textTitle;
        @BindView(R.id.img_next)
        ImageView imgNext;

        TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
