package com.zhixing.work.zhixin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.WifiBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/7.
 * Description:
 */

public class WifiListAdapter extends RecyclerView.Adapter {

    private List<WifiBean> wifiBeans;
    private ItemSelectedInterface anInterface;
    private boolean isEdit;

    public WifiListAdapter(List<WifiBean> wifiList) {
        this.wifiBeans = wifiList;
    }

    public interface ItemSelectedInterface{
        void itemSelected(int position,WifiBean bean);
    }

    public void setItemSelectedInterface(ItemSelectedInterface itemSelectedInterface){
        this.anInterface = itemSelectedInterface;
        notifyDataSetChanged();
    }

    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    public void setData(List<WifiBean> data){
        this.wifiBeans = data;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wifiView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi, parent, false);
        return new WifiViewHolder(wifiView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WifiViewHolder viewHolder = (WifiViewHolder) holder;
        WifiBean wifiBean = wifiBeans.get(position);
        if(isEdit){
            viewHolder.imgSelector.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imgSelector.setVisibility(View.GONE);
        }
        viewHolder.tvWifiName.setText(wifiBean.getName());
        viewHolder.tvWifiBssId.setText(wifiBean.getBssId());
        viewHolder.imgSelector.setSelected(wifiBean.isSelected());

        viewHolder.imgSelector.setOnClickListener(v -> anInterface.itemSelected(position,wifiBean));

    }

    @Override
    public int getItemCount() {
        return wifiBeans.size();
    }

    static class WifiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_wifi_name)
        TextView tvWifiName;
        @BindView(R.id.tv_wifi_bss_id)
        TextView tvWifiBssId;
        @BindView(R.id.img_selector)
        ImageView imgSelector;

        WifiViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
