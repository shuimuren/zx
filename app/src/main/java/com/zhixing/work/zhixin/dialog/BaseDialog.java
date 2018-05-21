package com.zhixing.work.zhixin.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

public class BaseDialog extends Dialog {

	@SuppressLint("InlinedApi")
	public BaseDialog(Context context) {
		super(context);
		
		if(getTheme() != 0){
			getContext().setTheme(getTheme());
		}else{
			getContext().setTheme(android.R.style.Theme_Holo_InputMethod);
		}

	}

	protected int getTheme() {
		return 0;
	}

}