
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
import com.zhixing.work.zhixin.bean.HotCity;
import com.zhixing.work.zhixin.bean.IndustryType;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门城市
 */
public class IndustryAdapter extends RecyclerView.Adapter<IndustryAdapter.ViewHolder> {
    public List<IndustryType> getList() {
        return list;
    }

    public void setList(List<IndustryType> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<IndustryType> list;
    private Context context;
    private LayoutInflater mInflater;

    public IndustryAdapter(List<IndustryType> list, Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, IndustryType industryType, int type);

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
        private CheckBox add_industry;
        private LinearLayout ll_industry;
        private LinearLayout ll_titie;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_industry,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
        viewHolder.add_industry = (CheckBox) convertView.findViewById(R.id.add_industry);
        viewHolder.ll_industry = (LinearLayout) convertView.findViewById(R.id.ll_industry);
        viewHolder.ll_titie = (LinearLayout) convertView.findViewById(R.id.ll_titie);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final IndustryType bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.title.setText(bean.getName());
        if (bean.getParentId() == 0) {
            holder.ll_titie.setVisibility(View.VISIBLE);
            holder.ll_industry.setVisibility(View.GONE);
        } else {
            holder.ll_titie.setVisibility(View.GONE);
            holder.ll_industry.setVisibility(View.VISIBLE);
        }
        if (bean.getIsSelect() == 0) {
            holder.add_industry.setChecked(false);
        } else {
            holder.add_industry.setChecked(true);
        }
        if (mOnItemClickListener != null) {

            holder.add_industry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();

                    if (holder.add_industry.isChecked()) {
                        if (getData(list).size() == 3) {
                            AlertUtils.toast(context, "最多只能选3个");
                            holder.add_industry.setChecked(!holder.add_industry.isChecked());
                            return;
                        }
                        bean.setIsSelect(1);
                        list.set(position, bean);
                        mOnItemClickListener.onItemClick(holder.add_industry, pos, bean, 1);
                    } else {
                        bean.setIsSelect(0);
                        list.set(position, bean);
                        mOnItemClickListener.onItemClick(holder.add_industry, pos, bean, 0);
                    }

                }
            });
            holder.add_industry.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.add_industry, pos);
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

    private List<IndustryType> getData(List<IndustryType> list) {
        List<IndustryType> dataList = new ArrayList<>();
        int number = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSelect() == 1) {
                dataList.add(list.get(i));
            }
        }
        return dataList;
    }

}
