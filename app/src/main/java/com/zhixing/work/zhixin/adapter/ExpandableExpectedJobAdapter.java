package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.JobChildBean;
import com.zhixing.work.zhixin.bean.JobParentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/11.
 * Description:
 */

public class ExpandableExpectedJobAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<JobParentBean> mGroupArray; //组列表
    private List<List<JobChildBean>> mChildArray; //子列表
    private OnViewClickedListener mInterface;

    public ExpandableExpectedJobAdapter(Context context) {
        this.mContext = context;
        mGroupArray = new ArrayList<>();
        mChildArray = new ArrayList<>();
    }

    public void setOnViewClickedListener(OnViewClickedListener listener){
        this.mInterface = listener;
    }

    public void setData(List<JobParentBean> groupArray, List<List<JobChildBean>> childArray) {
        this.mGroupArray = groupArray;
        this.mChildArray = childArray;
        notifyDataSetChanged();
    }

    public interface OnViewClickedListener {
        void selectedGroup(JobParentBean parentBean,int groupPosition,boolean isChecked);

        void selectedChild(JobChildBean childBean,int groupPosition,int childPosition,boolean isChecked);

        void expandOrMerge(int groupPosition,boolean isOpen);
    }


    @Override
    public int getGroupCount() {
        return mGroupArray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildArray.get(groupPosition).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewParentViewHolder mParentViewHolder;
        JobParentBean bean = mGroupArray.get(groupPosition);
        if (convertView == null) {
            mParentViewHolder = new ViewParentViewHolder();
            convertView = View.inflate(mContext, R.layout.item_parent_expected_job, null);
            mParentViewHolder.parentCheckBox = (ImageView) convertView.findViewById(R.id.checkParent);
            mParentViewHolder.parentName = (TextView) convertView.findViewById(R.id.parentName);
            mParentViewHolder.parentMark = (ImageView) convertView.findViewById(R.id.parentMark);
            convertView.setTag(mParentViewHolder);
        } else {
            mParentViewHolder = (ViewParentViewHolder) convertView.getTag();
        }
        mParentViewHolder.parentName.setText(bean.getName());
        if (isExpanded) {
            mParentViewHolder.parentMark.setImageResource(R.drawable.arrow_up);
        } else {
            mParentViewHolder.parentMark.setImageResource(R.drawable.arrow_down);
        }
        mParentViewHolder.parentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.expandOrMerge(groupPosition,isExpanded ? false : true);
            }
        });

        if(bean.isChecked()){
            mParentViewHolder.parentCheckBox.setSelected(true);
        }else{
           mParentViewHolder.parentCheckBox.setSelected(false);
        }

        mParentViewHolder.parentCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.selectedGroup(bean,groupPosition,mParentViewHolder.parentCheckBox.isSelected());
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewChildViewHolder jobChildViewHolder;
        JobChildBean childBean = mChildArray.get(groupPosition).get(childPosition);
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_child_expected_job, null);
            jobChildViewHolder = new ViewChildViewHolder(convertView);
            convertView.setTag(jobChildViewHolder);
        }else {
            jobChildViewHolder = (ViewChildViewHolder) convertView.getTag();
        }

        jobChildViewHolder.childName.setText(childBean.getName());
        if(childBean.isChecked()){
            jobChildViewHolder.checkChild.setSelected(true);
        }else {
            jobChildViewHolder.checkChild.setSelected(false);
        }
        jobChildViewHolder.childName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.selectedChild(childBean,groupPosition,childPosition,jobChildViewHolder.checkChild.isSelected());
            }
        });

        return convertView;
    }

    static class ViewParentViewHolder {
        ImageView parentCheckBox;
        TextView parentName;
        ImageView parentMark;
    }

    static class ViewChildViewHolder {
        @BindView(R.id.checkChild)
        ImageView checkChild;
        @BindView(R.id.childName)
        TextView childName;

        ViewChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
