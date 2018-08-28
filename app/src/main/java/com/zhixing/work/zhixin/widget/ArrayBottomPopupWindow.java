package com.zhixing.work.zhixin.widget;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.common.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lhj on 2018/8/27.
 * Description:
 */

public class ArrayBottomPopupWindow extends BasePopupWindow {

    private FiltrateAdapter mFiltrateAdapter;
    private List<String> mListDate;
    public String selectedItem;
    private ListView mView;
    private AdapterView.OnItemClickListener mItemClickListener;

    public ArrayBottomPopupWindow(Activity activity, @NonNull View parentView, Map<String, String> params, int width) {
        super(activity, parentView, params);
        mListDate = new ArrayList<>();
        View popupWindow = LayoutInflater.from(mActivity).inflate(R.layout.list_popup_window, null);
        initPopupWindow(popupWindow, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.anim_top_to_bottom_style);
        mFiltrateAdapter = new FiltrateAdapter();
        mView = popupWindow.findViewById(R.id.popup_window_list);
        mView.setAdapter(mFiltrateAdapter);
        mFiltrateAdapter.notifyDataSetChanged();
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDismiss();
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    public void setDateSet(List<String> info) {
        mListDate = info;
    }

    public void setDataSet(List<String> info, String currentString) {
        selectedItem = currentString;
        mListDate = info;
    }


    public void setItemClickListener(AdapterView.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onDismiss() {
        super.onDismiss();
        Logger.i(">>>", "dismiss??");
    }

    private class FiltrateAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public Object getItem(int position) {
            return mListDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(mActivity).inflate(R.layout.array_popup_window_item, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_item);
            textView.setText(mListDate.get(position));
            if (!TextUtils.isEmpty(selectedItem) && selectedItem.equals(mListDate.get(position))) {
                textView.setSelected(true);
            } else {
                textView.setSelected(false);
            }
            return view;
        }
    }
}
