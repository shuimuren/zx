package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhixing.work.zhixin.R;


//import com.fscx.jbc.R;
public class LoadingDialog extends BaseDialog {

	private Animation loadingAnimation;

	private boolean canNotCancel;

	/**
	 * 当小老虎不允许被用户取消的时候，用户按返回按钮时弹出的提示语
	 */
	private String backTipMsg;

	
	private ImageView ivLoading;
	private TextView tvLoadingLabel;
	
	private String loadingMsg;
	
	public LoadingDialog(Context ctx) {
		this(ctx, null);
	}
	
	public LoadingDialog(Context ctx, String loadingMsg) {
		this(ctx, loadingMsg, false, null);
	}

	public LoadingDialog(final Context ctx, String loadingMessage, boolean canNotCancel, String backTipMsg) {
		super(ctx);

		this.canNotCancel = canNotCancel;
		this.backTipMsg = backTipMsg;

//		this.getContext().setTheme(android.R.style.Theme_InputMethod);

		setContentView(R.layout.loading_dialog);

		ivLoading = (ImageView) findViewById(R.id.iv_loading);
		tvLoadingLabel = (TextView) findViewById(R.id.tv_loading_label);
		
		/*if (!TextUtils.isEmpty(loadingMessage)) {
			tvLoadingLabel.setVisibility(View.VISIBLE);
			setLoadingMessage(loadingMessage);
		}else{
			tvLoadingLabel.setVisibility(View.GONE);
		}*/

		Window window = getWindow();
		WindowManager.LayoutParams attributesParams = window.getAttributes();
		attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		attributesParams.dimAmount = 0.2f;

		window.setLayout(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		
//		loadingAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading);
//		ivLoading.setAnimation(loadingAnimation); 
	}
	
	public void setLoadingMessage(String msg) {
		this.loadingMsg = msg;
	}

	@Override
	public void show() {
		
		if(tvLoadingLabel != null){
			if(loadingMsg == null ||loadingMsg.length() == 0) {
				tvLoadingLabel.setVisibility(View.GONE);
			} else {
				tvLoadingLabel.setVisibility(View.VISIBLE);
				tvLoadingLabel.setText(loadingMsg);
			}
		}
		super.show();

	}
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
	        AnimationDrawable spinner = (AnimationDrawable) ivLoading.getBackground();
	        spinner.start();
//				loadingAnimation.start();
		}
	}
	
	@Override
	public void dismiss() {
		if (loadingAnimation != null) {
			loadingAnimation.cancel();
		}
		super.dismiss();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (canNotCancel) {
				Toast.makeText(getContext(), backTipMsg, Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
