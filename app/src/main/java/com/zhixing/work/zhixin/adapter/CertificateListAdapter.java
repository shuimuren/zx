
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
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhy.http.okhttp.utils.L;

import java.util.List;

/**
 * 证书
 */
public class CertificateListAdapter extends RecyclerView.Adapter<CertificateListAdapter.ViewHolder> {
    public List<Resume.CertificateOutputsBean> getList() {
        return list;
    }

    public void setList(List<Resume.CertificateOutputsBean>list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<Resume.CertificateOutputsBean> list;

    private Context context;
    private LayoutInflater mInflater;

    public CertificateListAdapter(List<Resume.CertificateOutputsBean> list, Context context) {
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
        private LinearLayout ll_certificate;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_certificate_list,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.time = (TextView) convertView.findViewById(R.id.time);
        viewHolder.ll_certificate = (LinearLayout) convertView.findViewById(R.id.ll_certificate);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Resume.CertificateOutputsBean certificate = list.get(position);
        holder.name.setText(certificate.getCertificateTitle());
        String times = DateFormatUtil.parseDate(certificate.getGraduationDate(), "yyyy-mm");
        holder.time.setText(times);
        if (mOnItemClickListener != null) {
            holder.ll_certificate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.ll_certificate, pos);
                }
            });
            holder.ll_certificate.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll_certificate, pos);
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
