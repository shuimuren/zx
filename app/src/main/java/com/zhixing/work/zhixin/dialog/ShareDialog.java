package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.share.ShareConstant;

import java.util.HashMap;
import java.util.Map;


/**
 * 分享
 */
public class ShareDialog extends BottomDialog {

    private RelativeLayout rlQQ;
    private LinearLayout llClose;
    private RelativeLayout rlWeChat;
    private RelativeLayout rlSms;
    private RelativeLayout rlZX;
    private TextView dialogTitle;
    private Map<String, Object> mParams;


    public ShareDialog(Context context, Map<String, Object> params) {
        super(context);
        mParams = new HashMap<>();
        mParams.putAll(params);
        setContentView(R.layout.dialog_share);
        bindViews();
    }

    private void bindViews() {
        rlQQ = (RelativeLayout) findViewById(R.id.rl_qq);
        rlWeChat = (RelativeLayout) findViewById(R.id.rl_wechat);
        rlZX = (RelativeLayout) findViewById(R.id.rl_firend);
        rlSms = (RelativeLayout) findViewById(R.id.rl_sms);
        llClose = (LinearLayout) findViewById(R.id.llClose);
        dialogTitle= findViewById(R.id.tv_companyName);
        if(!mParams.isEmpty() && TextUtils.isEmpty((String)mParams.get(ShareConstant.PARAM_SHARE_COMPANY_NAME))){
            dialogTitle.setText(String.format("邀请同事加入%s",mParams.get(ShareConstant.PARAM_SHARE_COMPANY_NAME)));
        }

        llClose.setOnClickListener(v -> dismiss());
        rlQQ.setOnClickListener(v -> {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHARE_TO_QQ, mParams);
            dismiss();
        });

        rlWeChat.setOnClickListener(v -> {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHARE_TO_FRIEND, mParams);
            dismiss();
        });
        rlZX.setOnClickListener(v -> {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHARE_TO_ZX, mParams);
            dismiss();
        });
        rlSms.setOnClickListener(v -> {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHARE_TO_EMS, mParams);
            dismiss();
        });
    }
}
