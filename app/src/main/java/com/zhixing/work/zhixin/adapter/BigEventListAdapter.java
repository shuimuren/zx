
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.History;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.util.DateFormatUtil;

import java.util.List;

/**
 * 公司大事件
 */
public class BigEventListAdapter extends RecyclerView.Adapter<BigEventListAdapter.ViewHolder> {
    public List<History> getList() {
        return list;
    }

    public void setList(List<History> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<History> list;
    private Context context;
    private LayoutInflater mInflater;

    public BigEventListAdapter(List<History> list, Context context) {
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
        private LinearLayout ll_event;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_big_event,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.time = (TextView) convertView.findViewById(R.id.time);
        viewHolder.ll_event = (LinearLayout) convertView.findViewById(R.id.ll_event);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        History history = list.get(position);
        holder.name.setText(history.getName());

        holder.time.setText( DateFormatUtil.parseDate(history.getDate(), "yyyy-mm"));

        if (mOnItemClickListener != null) {
            holder.ll_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.ll_event, pos);
                }
            });
            holder.ll_event.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll_event, pos);
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
