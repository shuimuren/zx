package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;


/**
 * 分享
 */
public class ShareDialog extends ButtomDialog {
    private TextView camera;
    private TextView photo;
    private RelativeLayout rl_qq;
    private LinearLayout llClose;
    private RelativeLayout rl_wechat;
    private RelativeLayout rl_sms;
    private RelativeLayout rl_firend;
    private OnItemClickListener clickListener;


    public static final int TYPE_QQ = 0;
    public static final int TYPE_WECHAT = 1;
    public static final int TYPE_Friend = 2;
    public static final int TYPE_SMS = 3;

    public interface OnItemClickListener {

        void onClick(ShareDialog dialog, int index);
    }

    public ShareDialog(Context context, OnItemClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        setContentView(R.layout.dialog_share);
        bindViews();
    }

    private void bindViews() {
        rl_qq = (RelativeLayout) findViewById(R.id.rl_qq);
        rl_wechat = (RelativeLayout) findViewById(R.id.rl_wechat);
        rl_firend = (RelativeLayout) findViewById(R.id.rl_firend);
        rl_sms = (RelativeLayout) findViewById(R.id.rl_sms);
        llClose = (LinearLayout) findViewById(R.id.llClose);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rl_qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(ShareDialog.this, TYPE_QQ);
            }

        });

        rl_wechat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(ShareDialog.this, TYPE_WECHAT);
            }

        });
        rl_firend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(ShareDialog.this, TYPE_Friend);
            }

        });
        rl_sms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(ShareDialog.this, TYPE_SMS);
            }

        });
    }
}
