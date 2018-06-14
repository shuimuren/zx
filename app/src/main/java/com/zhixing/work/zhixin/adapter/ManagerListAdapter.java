
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.bean.Manager;
import com.zhixing.work.zhixin.bean.Product;
import com.zhixing.work.zhixin.util.GlideUtils;

import java.util.List;

/**
 * 公司高管列表
 */
public class ManagerListAdapter extends RecyclerView.Adapter<ManagerListAdapter.ViewHolder> {
    public List<Manager> getList() {
        return list;
    }

    public void setList(List<Manager> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<Manager> list;
    private Context context;
    private LayoutInflater mInflater;

    public ManagerListAdapter(List<Manager> list, Context context) {
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
        private TextView describe;
        private ImageView photo;
        private LinearLayout ll_manager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_manager_list,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.describe = (TextView) convertView.findViewById(R.id.describe);
        viewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
        viewHolder.ll_manager = (LinearLayout) convertView.findViewById(R.id.ll_manager);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Manager manager = list.get(position);
        holder.name.setText(manager.getName());
        holder.describe.setText(manager.getIntro());
        if (!TextUtils.isEmpty(manager.getAvatar())) {

            GlideUtils.getInstance().loadCircleUserIconInto(context, ALiYunFileURLBuilder.getUserIconUrl(manager.getAvatar()), holder.photo);
        }


        if (mOnItemClickListener != null) {
            holder.ll_manager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.ll_manager, pos);
                }
            });
            holder.ll_manager.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll_manager, pos);
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
