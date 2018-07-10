package com.zhixing.work.zhixin.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;

import imagetool.lhj.com.permission.PermissionTool;


/**
 * SelectImageDialog
 * 0表示拍照 1表示相册
 */
public class SelectImageDialog extends BottomDialog {
    private TextView camera;
    private TextView photo;
    private LinearLayout llClose;
    private OnItemClickListener clickListener;
    public static final int TYPE_CAMERA = 0;
    public static final int TYPE_PHOTO = 1;
    private Context mContext;

    public interface OnItemClickListener {
        /**
         * @param index 0表示拍照 1表示相册
         */
        void onClick(SelectImageDialog dialog, int index);
    }

    public SelectImageDialog(Context context, OnItemClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        mContext = context;
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
                if(PermissionTool.hasPermissions(mContext,new String[]{"android.permission.CAMERA"})){
                    clickListener.onClick(SelectImageDialog.this, TYPE_CAMERA);
                }else{
                    PermissionTool.requestPermission((Activity) mContext,new String[]{"android.permission.CAMERA"},
                            new String[]{"拍照"},100);
                }

            }

        });
        photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(PermissionTool.hasPermissions(mContext,new String[]{"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"})){
                    clickListener.onClick(SelectImageDialog.this, TYPE_PHOTO);
                }else{
                    PermissionTool.requestPermission((Activity) mContext,new String[]{"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"},
                            new String[]{"读写内存权限"},1000);
                }
            }


        });
    }
}
