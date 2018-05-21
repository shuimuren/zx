package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;


/**
 * SelectImageDialog
 * 0表示拍照 1表示相册
 *
 */
public class SelectImageDialog extends ButtomDialog {
	private TextView camera;
	private TextView photo;
	private LinearLayout llClose;
	private OnItemClickListener clickListener;
	public static final int TYPE_CAMERA=0;
	public static final int TYPE_PHOTO=1;
	public interface OnItemClickListener {
		/**
		 * @param index 0表示拍照 1表示相册
		 */
		void onClick(SelectImageDialog dialog, int index);
	}
	public SelectImageDialog(Context context, OnItemClickListener clickListener) {
		super(context);
		this.clickListener = clickListener;
		setContentView(R.layout.dialog_switch_image);
		bindViews();
	}
	private void bindViews() {
		camera = (TextView) findViewById(R.id.camera);
		photo = (TextView) findViewById(R.id.photo);
		llClose = (LinearLayout) findViewById(R.id.llClose);
		llClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}

		});
		camera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clickListener.onClick(SelectImageDialog.this,TYPE_CAMERA);
			}

		});
		photo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clickListener.onClick(SelectImageDialog.this,TYPE_PHOTO);
			}

		});
	}
}
