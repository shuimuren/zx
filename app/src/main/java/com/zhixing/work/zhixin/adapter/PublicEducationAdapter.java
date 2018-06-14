package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.domain.AlbumItem;


import java.util.ArrayList;
import java.util.List;
//发布学历

/**
 * Created by wangsuli on 2017/4/28.
 */
public class PublicEducationAdapter extends RecyclerView.Adapter<PublicEducationAdapter.ViewHolder> {
    private final Context mContext;
    private List<AlbumItem> albumList;
    private ArrayList<AlbumItem> selectedImages;//标记选中的图片
    private LayoutInflater mInflater;

    public PublicEducationAdapter(Context context, List<AlbumItem> albumList) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.albumList = albumList;

        selectedImages = new ArrayList<>();
    }


    public void addSelectedImages(AlbumItem item) {
        selectedImages.add(item);
        notifyDataSetChanged();
    }

    public ArrayList<AlbumItem> getSelectedImages() {
        return selectedImages;
    }


    public int getCount() {
        return albumList.size();
    }


    public AlbumItem getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_select_image, parent, false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(convertView);


        viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
        viewHolder.check = (ImageView) convertView.findViewById(R.id.check);
        viewHolder.rl_image = (RelativeLayout) convertView.findViewById(R.id.rl_image);

        return viewHolder;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AlbumItem item = getItem(position);
        if (null == item) {
            holder.image.setImageResource(R.drawable.add_image);
            holder.check.setVisibility(View.GONE);
        } else {
            holder.check.setVisibility(View.VISIBLE);
            if (item.getThumbnail().isEmpty()) {
                Glide.with(mContext).load(item.getThumbnail()).into(holder.image);
            } else {
                Glide.with(mContext).load(item.getFilePath()).into(holder.image);
            }
            holder.check.setImageResource(R.drawable.close);
        }
        if (mOnItemClickListener != null) {
            holder.rl_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.image, pos);
                }
            });
            holder.rl_image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.image, pos);
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
        return albumList == null ? 0 : albumList.size();
    }


    public void setData(List<AlbumItem> albumList) {
        if (albumList == null || albumList.size() == 0) {
            return;
        }
        this.albumList = albumList;
        notifyDataSetChanged();
    }

    public void addData(AlbumItem item) {
        this.albumList.add(item);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        private ImageView image;
        private ImageView check;
        private RelativeLayout rl_image;
    }

}
