
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.Staff;
import com.zhixing.work.zhixin.util.GlideUtils;

import java.util.List;

/**
 *
 */
public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {


    private List<Staff> list;
    private Context context;
    private LayoutInflater mInflater;

    public List<Staff> getList() {
        return list;
    }

    public void setList(List<Staff> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public StaffAdapter(List<Staff> list, Context context) {
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
        private ImageView avater;
        private TextView number;
        private RelativeLayout ll_staff;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_staff,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.avater = (ImageView) convertView.findViewById(R.id.avatar);
        viewHolder.number = (TextView) convertView.findViewById(R.id.number);
        viewHolder.ll_staff = (RelativeLayout) convertView.findViewById(R.id.ll_staff);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Staff staff = list.get(position);
        holder.name.setText(staff.getStaffNickName());

        GlideUtils.getInstance().loadRoundUserIconInto(context, staff.getStaffAvatar(), holder.avater);


        if (mOnItemClickListener != null) {
            holder.ll_staff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.ll_staff, pos);
                }
            });
            holder.ll_staff.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll_staff, pos);
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
