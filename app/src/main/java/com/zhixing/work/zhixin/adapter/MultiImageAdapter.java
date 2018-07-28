package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/27.
 * Description:
 */

public class MultiImageAdapter extends RecyclerView.Adapter {

    private List<String> mData;
    private Context mContext;
    private int maxSize;
    private OnItemClickedListener listener;


    public MultiImageAdapter(Context context, List<String> data, int maxSize) {
        this.mContext = context;
        this.mData = data;
        this.maxSize = maxSize;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public interface OnItemClickedListener {
        void addImage();

        void itemClicked(int position);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageView = LayoutInflater.from(mContext).inflate(R.layout.item_multi_image, parent, false);
        return new MultiViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MultiViewHolder viewHolder = (MultiViewHolder) holder;
        if (mData.size() < maxSize) {
            if (position == mData.size()) {
                viewHolder.imageDelete.setVisibility(View.GONE);
                viewHolder.image.setOnClickListener(v -> listener.addImage());
                Glide.with(mContext).load(ResourceUtils.getDrawable(R.drawable.add_image)).into(viewHolder.image);
            } else {
                viewHolder.imageDelete.setVisibility(View.VISIBLE);
                viewHolder.imageDelete.setOnClickListener(v -> listener.itemClicked(position));
                GlideUtils.getInstance().loadGlideRoundTransform(mContext, mData.get(position), viewHolder.image);
            }
        } else {
            viewHolder.imageDelete.setVisibility(View.VISIBLE);
            viewHolder.imageDelete.setOnClickListener(v -> listener.itemClicked(position));
            GlideUtils.getInstance().loadGlideRoundTransform(mContext, mData.get(position), viewHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() < maxSize) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    static class MultiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.image_delete)
        ImageView imageDelete;

        MultiViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
