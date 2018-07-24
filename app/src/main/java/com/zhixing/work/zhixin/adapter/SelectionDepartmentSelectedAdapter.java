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
import com.zhixing.work.zhixin.bean.SelectedDepartmentBean;
import com.zhixing.work.zhixin.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/23.
 * Description:
 */

public class SelectionDepartmentSelectedAdapter extends RecyclerView.Adapter {

    private List<SelectedDepartmentBean> mData;
    private ItemClickedListener mClickedListener;
    private Context mContext;

    public SelectionDepartmentSelectedAdapter(Context context, List<SelectedDepartmentBean> data) {
        this.mData = data;
        this.mContext = context;
    }

    public void setData(List<SelectedDepartmentBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public interface ItemClickedListener {
        void onItemClicked(SelectedDepartmentBean bean);

        void onDeleteClicked(SelectedDepartmentBean bean);
    }

    public void setItemClickedListener(ItemClickedListener listener) {
        this.mClickedListener = listener;
    }

    private static final int TYPE_USER = 0;
    private static final int TYPE_DEPARTMENT = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_USER:
                View viewUser = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selection_department_user, parent, false);
                return new ViewUserHolder(viewUser);
            case TYPE_DEPARTMENT:
                View viewDepartment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selection_department, parent, false);
                return new ViewDepartmentHolder(viewDepartment);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewUserHolder) {
            ViewUserHolder viewHolder = (ViewUserHolder) holder;
            SelectedDepartmentBean bean = mData.get(position);
            GlideUtils.getInstance().loadCircleUserIconInto(mContext, bean.getUserAvatar(), viewHolder.memberAvatar);
            viewHolder.imgDelete.setOnClickListener(v -> mClickedListener.onDeleteClicked(bean));
        } else if (holder instanceof ViewDepartmentHolder) {
            ViewDepartmentHolder departmentHolder = (ViewDepartmentHolder) holder;
            SelectedDepartmentBean bean = mData.get(position);
            departmentHolder.departmentName.setText(bean.getDepartmentName());
            departmentHolder.departmentName.setOnClickListener(v -> mClickedListener.onItemClicked(bean));
            departmentHolder.imgDelete.setOnClickListener(v -> mClickedListener.onDeleteClicked(bean));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).isUser()) {
            return TYPE_USER;
        } else {
            return TYPE_DEPARTMENT;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewUserHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.member_avatar)
        ImageView memberAvatar;
        @BindView(R.id.img_delete)
        ImageView imgDelete;

        ViewUserHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewDepartmentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.department_name)
        TextView departmentName;
        @BindView(R.id.img_delete)
        ImageView imgDelete;

        ViewDepartmentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
