
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.CardBack;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.util.DateFormatUtil;

import java.util.List;

/**
 * 添加学历
 */
public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {
    public List<CardBack.EducationBackgroundOutputsBean> getContactList() {
        return list;
    }

    public void setList(List<CardBack.EducationBackgroundOutputsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<CardBack.EducationBackgroundOutputsBean> list;

    private Context context;
    private LayoutInflater mInflater;

    public EducationAdapter(List<CardBack.EducationBackgroundOutputsBean> list, Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

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
        private TextView time;
        private LinearLayout ll_education;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_education,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.time = (TextView) convertView.findViewById(R.id.time);
        viewHolder.ll_education = (LinearLayout) convertView.findViewById(R.id.ll_education);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CardBack.EducationBackgroundOutputsBean education= list.get(position);
        holder.name.setText(education.getSchool());
        String times = DateFormatUtil.parseDate(education.getStartDate(), "yyyy") + "一"+DateFormatUtil.parseDate(education.getEndDate(), "yyyy");
        holder.time.setText(times);
        if (mOnItemClickListener != null) {
            holder.ll_education.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.ll_education, pos);
                }
            });
            holder.ll_education.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll_education, pos);
                    return false;
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

}
