
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.HotCity;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门城市
 */
public class WorkCityAdapter extends RecyclerView.Adapter<WorkCityAdapter.ViewHolder> {
    public List<HotCity> getList() {
        return list;
    }

    public void setList(List<HotCity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<HotCity> list;
    private Context context;
    private LayoutInflater mInflater;

    public WorkCityAdapter(List<HotCity> list, Context context) {
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
        private ImageView delete;
        private RelativeLayout ll_city;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_work_city,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.delete = (ImageView) convertView.findViewById(R.id.delete);
        viewHolder.ll_city = (RelativeLayout) convertView.findViewById(R.id.ll_city);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final HotCity city = list.get(position);
        holder.name.setText(city.getName());
        if (mOnItemClickListener != null) {


            holder.ll_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.ll_city, pos);
                }
            });
            holder.ll_city.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll_city, pos);
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

    private List<HotCity> getData(List<HotCity> list) {
        List<HotCity> dataList = new ArrayList<>();
        int number = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSelect() == 1) {
                dataList.add(list.get(i));
            }
        }
        return dataList;
    }

}
