package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/23.
 * Description:
 */

public class SelectionDepartmentMemberAdapter extends RecyclerView.Adapter {

    private List<DepartmentMemberInfoBean> members;
    private ItemClickedInterface itemClickedInterface;
    private boolean isEdit;
    private Context mContext;

    public SelectionDepartmentMemberAdapter(Context context, List<DepartmentMemberInfoBean> data) {
        this.mContext = context;
        this.members = data;

    }

    public void setData(List<DepartmentMemberInfoBean> data) {
        this.members = data;
        notifyDataSetChanged();
    }

    public void setItemClickedListener(ItemClickedInterface listener) {
        this.itemClickedInterface = listener;
        notifyDataSetChanged();
    }

    public interface ItemClickedInterface {
        void onItemClicked(int position, DepartmentMemberInfoBean bean);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selector_department_staff, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DepartmentMemberInfoBean bean = members.get(position);
        MemberViewHolder viewHolder = (MemberViewHolder) holder;

        viewHolder.memberName.setText(bean.getStaffNickName());
        GlideUtils.getInstance().loadCircleUserIconInto(mContext, bean.getStaffAvatar(), viewHolder.memberAvatar);
        if (bean.isSelected()) {
            viewHolder.imgSelector.setSelected(true);
        } else {
            viewHolder.imgSelector.setSelected(false);
        }

        viewHolder.imgSelector.setOnClickListener(v -> itemClickedInterface.onItemClicked(position, bean));

    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_selector)
        ImageView imgSelector;
        @BindView(R.id.member_avatar)
        ImageView memberAvatar;
        @BindView(R.id.member_name)
        TextView memberName;
        @BindView(R.id.member_mark)
        ImageView memberMark;

        MemberViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
