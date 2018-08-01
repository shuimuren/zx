package com.zhixing.work.zhixin.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import com.zhixing.work.zhixin.R;

public class BaseDialog extends Dialog {

	@SuppressLint("InlinedApi")
	public BaseDialog(Context context) {
		super(context);
		
		if(getTheme() != 0){
			getContext().setTheme(getTheme());
		}else{
			getContext().setTheme(R.style.default_dialog_style);
		}



	}

	protected int getTheme() {
		return 0;
	}

}