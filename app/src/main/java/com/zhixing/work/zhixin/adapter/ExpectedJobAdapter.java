package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.JobChildBean;
import com.zhixing.work.zhixin.bean.JobChoice;
import com.zhixing.work.zhixin.bean.JobParentBean;
import com.zhixing.work.zhixin.common.JobManagerHelper;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/11.
 * Description:
 */

public class ExpectedJobAdapter extends RecyclerView.Adapter {

    @BindView(R.id.jobChoiceTitle)
    TextView jobChoiceTitle;
    @BindView(R.id.expandList)
    ExpandableListView expandList;

    private List<JobChoice> jobChoiceData;
    private OnViewChangedListener viewChangedListener;


    private Context mContext;

    public ExpectedJobAdapter(Context context, List<JobChoice> list) {
        this.jobChoiceData = list;
        this.mContext = context;
    }

    public void setViewChangedListener(OnViewChangedListener viewChangedListener) {
        this.viewChangedListener = viewChangedListener;
    }


    public interface OnViewChangedListener {
        void selectedGroup(int currentPosition,JobParentBean parentBean, int groupPosition, boolean isChecked);

        void selectedChild(int currentPosition,JobChildBean childBean, int groupPosition, int childPosition, boolean isChecked);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expected_job_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder jobHolder = (ViewHolder) holder;
        jobHolder.jobChoiceTitle.setText(jobChoiceData.get(position).getName());

        ExpandableExpectedJobAdapter adapter = new ExpandableExpectedJobAdapter(mContext);
        adapter.setOnViewClickedListener(new ExpandableExpectedJobAdapter.OnViewClickedListener() {
            @Override
            public void selectedGroup(JobParentBean parentBean, int groupPosition, boolean isChecked) {
                if(JobManagerHelper.getJobManagerHelperInstance().showParentToast(parentBean,isChecked)){
                    AlertUtils.show(ResourceUtils.getString(R.string.alter_selected_mast_three));
                }else{
                    JobManagerHelper.getJobManagerHelperInstance().setGroupSelected(position,groupPosition,isChecked);
                    adapter.notifyDataSetChanged();
                    viewChangedListener.selectedGroup(position,parentBean, groupPosition, isChecked);
               }

            }

            @Override
            public void selectedChild(JobChildBean childBean, int groupPosition, int childPosition, boolean isChecked) {
                if(JobManagerHelper.getJobManagerHelperInstance().showChildToast(childBean,isChecked)){// AlertUtils.show(ResourceUtils.getString(R.string.alter_selected_mast_three)));
                    AlertUtils.show(ResourceUtils.getString(R.string.alter_selected_mast_three));
                }else {

                    JobManagerHelper.getJobManagerHelperInstance().setChildSelected(childBean,position,groupPosition,isChecked);
                    adapter.notifyDataSetChanged();
                    viewChangedListener.selectedChild(position,childBean, groupPosition, childPosition, isChecked);
                }

            }

            @Override
            public void expandOrMerge(int groupPosition, boolean isOpen) {
                if (isOpen) {
                    jobHolder.listView.expandGroup(groupPosition);

                } else {
                    jobHolder.listView.collapseGroup(groupPosition);

                }
            }
        });
        adapter.setData(JobManagerHelper.getJobManagerHelperInstance().getParentByPosition(position)
                , JobManagerHelper.getJobManagerHelperInstance().getChildAllList(position));
        jobHolder.listView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return jobChoiceData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.jobChoiceTitle)
        TextView jobChoiceTitle;
        @BindView(R.id.expandList)
        ExpandableListView listView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
