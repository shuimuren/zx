package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/18.
 * Description: 组织架构列表
 */

public class DepartmentManagerSelectorAdapter extends RecyclerView.Adapter {

    private List<DepartmentMemberInfoBean> members;
    private ItemClickedInterface itemClickedInterface;
    private Context mContext;

    public DepartmentManagerSelectorAdapter(Context context, List<DepartmentMemberInfoBean> data) {
        this.mContext = context;
        this.members = data;
    }

    public void setData(List<DepartmentMemberInfoBean> data) {
        members = data;
        notifyDataSetChanged();
    }

    public void setItemClickedListener(ItemClickedInterface listener) {
        this.itemClickedInterface = listener;
        notifyDataSetChanged();
    }

    public interface ItemClickedInterface {
        void onItemClicked(DepartmentMemberInfoBean bean);

        void selectClicked(int position, DepartmentMemberInfoBean bean);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_selector_manager, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DepartmentMemberInfoBean bean = members.get(position);
        MemberViewHolder viewHolder = (MemberViewHolder) holder;
        if (bean.getStaffRole() != ResultConstant.USER_STAFF_ROLE_EMPLOYEE) {
            viewHolder.imgSelector.setEnabled(false);
            viewHolder.memberRole.setVisibility(View.VISIBLE);
            if (bean.getStaffRole() == ResultConstant.USER_STAFF_ROLE_HR) {
                viewHolder.memberRole.setText("HR");
            } else {
                viewHolder.memberRole.setText("管理员");
            }
        } else {
            viewHolder.imgSelector.setEnabled(true);
            viewHolder.memberRole.setVisibility(View.GONE);
        }
        viewHolder.imgSelector.setSelected(bean.isSelected());
        viewHolder.memberName.setText(bean.getStaffNickName());
        GlideUtils.getInstance().loadCircleUserIconInto(mContext, bean.getStaffAvatar(), viewHolder.memberAvatar);
        viewHolder.imgSelector.setOnClickListener(v -> itemClickedInterface.selectClicked(position, bean));
        viewHolder.itemView.setOnClickListener(v -> itemClickedInterface.onItemClicked(bean));
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
        @BindView(R.id.member_role)
        TextView memberRole;
        @BindView(R.id.ll_member)
        LinearLayout llMember;

        MemberViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
