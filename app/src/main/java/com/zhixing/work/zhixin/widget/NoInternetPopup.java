package com.zhixing.work.zhixin.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.zhixing.work.zhixin.R;


public class NoInternetPopup extends PopupWindow {
	private Context mContext;
	private View view;
	public NoInternetPopup(Context context){
		this.mContext = context;
		setHeight(LayoutParams.WRAP_CONTENT);
		setWidth(LayoutParams.MATCH_PARENT);
		view = LayoutInflater.from(mContext).inflate(R.layout.popup_no_internet, null);
		setContentView(view);
	}
/*	public NoInternetPopup(Context context,int height,int width){
		this.mContext = context;
		setHeight(height);
		setWidth(width);
		View view = LayoutInflater.from(mContext).inflate(R.layout.popup_no_internet, null);
		setContentView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}*/
	public void setOnClickListener(OnClickListener listener){
		
		view.setOnClickListener(listener);
	}
}
