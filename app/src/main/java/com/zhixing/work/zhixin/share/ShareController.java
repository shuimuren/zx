package com.zhixing.work.zhixin.share;

import android.content.Context;
import android.os.Message;

import com.zhixing.work.zhixin.dialog.ShareDialog;
import com.zhixing.work.zhixin.msgctrl.AbstractController;
import com.zhixing.work.zhixin.msgctrl.MsgDef;

import java.util.Map;

/**
 * Created by lhj on 2018/7/17.
 * Description:
 */

public class ShareController extends AbstractController {

    public static final int MSG_DEF_SHARE_TO_EMS = 0x0017;
    public static final int MSG_DEF_SHARE_TO_ZX = 0x0018;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MsgDef.MSG_DEF_SHOW_SHARE_PLATFORM:
                showPlatformWindow((Map<String,Object>) msg.obj);
                break;
            case MsgDef.MSG_DEF_SHARE_TO_FRIEND:
                shareToFriends((Map<String, Object>) msg.obj);
                break;
            case MsgDef.MSG_DEF_SHARE_TO_TIMELINE:
                break;
            case MsgDef.MSG_DEF_SHARE_TO_OTHER:
                break;
            case MsgDef.MSG_DEF_SHARE_TO_QQ:
                shareToQQ((Map<String, Object>) msg.obj);
                break;
            case MsgDef.MSG_DEF_SHARE_TO_EMS:
                shareToEMS((Map<String, Object>) msg.obj);
                break;
            case MsgDef.MSG_DEF_SHARE_TO_ZX:
                shareToZX((Map<String, Object>) msg.obj);
                break;

        }
        return true;
    }

    private void shareToZX(Map<String, Object> obj) {
       // ShareUtil.getInstance().shareToSMS(obj);
        ShareUtil.getInstance().shareToZX(obj);
    }

    private void shareToEMS(Map<String, Object> obj) {

        ShareUtil.getInstance().shareToSMS(obj);
    }

    private void shareToQQ(Map<String, Object> obj) {
        ShareUtil.getInstance().shareToQQ(obj);
    }

    private void shareToFriends(Map<String, Object> obj) {
        ShareUtil.getInstance().shareToFriends(obj);
    }

    private void showPlatformWindow(Map<String, Object> params) {
        ShareDialog imageDialog = new ShareDialog((Context) params.get(ShareConstant.PARAMS_CONTEXT),params);
        imageDialog.show();
    }



}
