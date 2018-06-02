
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.IndustryType;
import com.zhixing.work.zhixin.bean.JobType;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 期望职业
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    public List<JobType> getList() {
        return list;
    }

    public void setList(List<JobType> list, int surplus) {
        this.list = list;
        this.surplus = surplus;

        notifyDataSetChanged();
    }

    private List<JobType> list;
    private Context context;
    private LayoutInflater mInflater;
    private int surplus;

    public JobAdapter(List<JobType> list, Context context, int surplus) {
        this.list = list;
        this.context = context;
        this.surplus = surplus;
        mInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, JobType job, int type);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }

        private TextView name;
        private TextView title;
        private CheckBox add_job;
        private LinearLayout ll_job;
        private LinearLayout ll_titie;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_job,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
        viewHolder.add_job = (CheckBox) convertView.findViewById(R.id.add_job);
        viewHolder.ll_job = (LinearLayout) convertView.findViewById(R.id.ll_job);
        viewHolder.ll_titie = (LinearLayout) convertView.findViewById(R.id.ll_titie);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final JobType bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.title.setText(bean.getName());
        if (bean.getParentId() == 0) {
            holder.ll_titie.setVisibility(View.VISIBLE);
            holder.ll_job.setVisibility(View.GONE);
        } else {
            holder.ll_titie.setVisibility(View.GONE);
            holder.ll_job.setVisibility(View.VISIBLE);
        }
        if (bean.getIsSelect() == 0) {
            holder.add_job.setChecked(false);
        } else {
            holder.add_job.setChecked(true);
        }
        if (mOnItemClickListener != null) {
            holder.add_job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    if (holder.add_job.isChecked()) {
                        if (getData(list).size() == surplus) {
                            AlertUtils.toast(context, "最多只能选3个");
                            holder.add_job.setChecked(!holder.add_job.isChecked());
                            return;
                        }
                        bean.setIsSelect(1);
                        list.set(position, bean);
                        mOnItemClickListener.onItemClick(holder.add_job, pos, bean, 1);
                    } else {
                        bean.setIsSelect(0);
                        list.set(position, bean);
                        mOnItemClickListener.onItemClick(holder.add_job, pos, bean, 0);
                    }

                }
            });
            holder.add_job.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.add_job, pos);
                    return false;
                }
            });


            holder.ll_job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll_job, pos);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private List<JobType> getData(List<JobType> list) {
        List<JobType> dataList = new ArrayList<>();
        int number = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSelect() == 1) {
                dataList.add(list.get(i));
            }
        }
        return dataList;
    }

}
