package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 底部对话框
 *
 */
public class BottomDialog extends BaseDialog{
	Window window;
	int width;
	public BottomDialog(Context context) {
		super(context);
		window = getWindow();
		width = window.getWindowManager().getDefaultDisplay().getWidth();
		setCanceledOnTouchOutside(true);
	}

	@Override
	public void show() {
		super.show();
		WindowManager.LayoutParams attributesParams = window.getAttributes();
		attributesParams.gravity  = Gravity.BOTTOM;
		attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		attributesParams.dimAmount = 0.4f;
		attributesParams.width = width;
		attributesParams.x = 0;
		window.setAttributes(attributesParams);
	}

}
