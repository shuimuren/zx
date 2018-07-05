
package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 工作经历
 */
public class ResumeEducationAdapter extends RecyclerView.Adapter<ResumeEducationAdapter.ViewHolder> {
    public List<Resume.EducationOutputsBean> getList() {
        return list;
    }

    public void setList(List<Resume.EducationOutputsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<Resume.EducationOutputsBean> list;

    private Context context;
    private LayoutInflater mInflater;

    public ResumeEducationAdapter(List<Resume.EducationOutputsBean> list, Context context) {
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
        private CheckBox is_public;
        private LinearLayout ll_education;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_education_resume,
                parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.is_public = (CheckBox) convertView.findViewById(R.id.is_public);
        viewHolder.ll_education = (LinearLayout) convertView.findViewById(R.id.ll_education);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Resume.EducationOutputsBean bean = list.get(position);
        holder.name.setText(bean.getSchool());
        holder.is_public.setChecked(bean.isShow());

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

        holder.is_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody body = new FormBody.Builder()
                        .add("", holder.is_public.isChecked() + "")
                        .build();
                setPublic(body, bean.getId() + "", holder.is_public);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private void setPublic(RequestBody body, String id, final CheckBox cb) {
        OkUtils.getInstances().httpatch(body, context, RequestConstant.ADD_EDUCATION_EXPERIENCE + "?Id=" + id, JavaParamsUtils.getInstances().resumeAvatar(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {

                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {

                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (response.getContent() != null && response.getContent()) {
                        if (response.getContent()) {

                        }
                    } else {

                        AlertUtils.toast(context, "修改失败");
                        cb.setChecked(!cb.isChecked());
                    }

                } else {
                    AlertUtils.toast(context, response.getMessage());
                    cb.setChecked(!cb.isChecked());
                }


            }
        });
    }
}
