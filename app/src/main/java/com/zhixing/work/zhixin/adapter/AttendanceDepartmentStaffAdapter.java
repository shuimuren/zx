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
import com.zhixing.work.zhixin.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/18.
 * Description: 考勤选择人员 ->成员列表
 */

public class AttendanceDepartmentStaffAdapter extends RecyclerView.Adapter {

    private List<DepartmentMemberInfoBean> members;
    private ItemClickedInterface itemClickedInterface;
    private boolean isEdit;
    private Context mContext;

    public AttendanceDepartmentStaffAdapter(Context context, List<DepartmentMemberInfoBean> data, boolean isEdit) {
        this.mContext = context;
        this.members = data;
        this.isEdit = isEdit;
    }

    public void setData(List<DepartmentMemberInfoBean> data) {
        members = data;
        notifyDataSetChanged();
    }

    public void setItemClickedListener(ItemClickedInterface listener) {
        this.itemClickedInterface = listener;
    }

    public interface ItemClickedInterface {
        void onItemClicked(int position,DepartmentMemberInfoBean bean);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_department_staff, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DepartmentMemberInfoBean bean = members.get(position);
        MemberViewHolder viewHolder = (MemberViewHolder) holder;
        if (bean.isSelected()) {
            viewHolder.imgSelector.setSelected(true);
        } else {
            viewHolder.imgSelector.setSelected(false);
        }
        viewHolder.memberName.setText(bean.getStaffNickName());
        GlideUtils.getInstance().loadCircleUserIconInto(mContext, bean.getStaffAvatar(), viewHolder.memberAvatar);
        viewHolder.imgSelector.setOnClickListener(v -> itemClickedInterface.onItemClicked(position,bean));
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
        @BindView(R.id.img_edit)
        ImageView imgEdit;
        @BindView(R.id.ll_member)
        LinearLayout llMember;

        MemberViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
