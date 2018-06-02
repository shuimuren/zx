
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.CardBack;
import com.zhixing.work.zhixin.bean.Certificate;

import java.util.List;

/**
 * 添加学历
 */
public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.ViewHolder> {
    public List<CardBack.CertificateBackgroundOutputsBean> getList() {
        return list;
    }

    public void setList(List<CardBack.CertificateBackgroundOutputsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<CardBack.CertificateBackgroundOutputsBean> list;

    private Context context;
    private LayoutInflater mInflater;

    public CertificateAdapter(List<CardBack.CertificateBackgroundOutputsBean> list, Context context) {
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
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_education,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.time = (TextView) convertView.findViewById(R.id.time);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        CardBack.CertificateBackgroundOutputsBean certificate = list.get(position);
        holder.name.setText(certificate.getCertificateTitle());



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
