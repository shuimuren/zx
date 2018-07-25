package com.zhixing.work.zhixin.share;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.app.AppManager;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.util.ResourceUtils;

import java.util.Map;

/**
 * Created by lhj on 2018/7/17.
 * Description:
 */

public class ShareUtil extends BaseShareUtil {

    private IWXAPI mWxApi;

    private ShareUtil() {
        mWxApi = WXAPIFactory.createWXAPI(ZxApplication.getInstance().getApplicationContext(), ShareConstant.WX_APP_ID);
        mWxApi.registerApp(ShareConstant.WX_APP_ID);
    }

    private static class InstanceHolder {
        private static ShareUtil sInstance = new ShareUtil();
    }

    public static ShareUtil getInstance() {
        return InstanceHolder.sInstance;
    }

    /**
     * 分享到朋友
     *
     * @param params
     */
    public void shareToFriends(Map<String, Object> params) {
        Logger.i(">>>", "分享给微信好友");
        shareToWeiXin(params, ShareConstant.SHARE_TO_FRIEND);
    }

    /**
     * 分享到朋友圈
     *
     * @param params
     */
    public void shareToTimeLine(Map<String, Object> params) {
        shareToWeiXin(params, ShareConstant.SHARE_TO_FRIEND);
        Logger.i(">>>", "分享到朋友圈");
    }

    /**
     * 分享到QQ
     */
    public void shareToQQ(Map<String, Object> params) {
        Logger.i(">>>", "分享到QQ");
        shareToQq(params);
    }

    /**
     * 分享到SMS
     */
    public void shareToSMS(Map<String, Object> params) {
        Logger.i(">>>", "分享到SMS");
    }

    /**
     * 分享到职信
     */
    public void shareToZX(Map<String, Object> params) {
        Logger.i(">>>", "分享到ZX");
    }

    private void shareToWeiXin(Map<String, Object> params, int flag) {

        explainParams(params);

        WXWebpageObject webpageObject = new WXWebpageObject();

        webpageObject.webpageUrl = mShareUrl;

        WXMediaMessage wxMediaMessage = new WXMediaMessage(webpageObject);
        if (mShareThumbnail != null) {
            wxMediaMessage.setThumbImage(mShareThumbnail);
            if (wxMediaMessage.thumbData.length > 1024 * 32) {
                wxMediaMessage.thumbData = null;
            }
        }
        wxMediaMessage.title = mShareTitle;
        wxMediaMessage.description = mShareDescription;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = flag == ShareConstant.SHARE_TO_FRIEND ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        mWxApi.sendReq(req);

    }

    private void shareToQq(Map<String, Object> object) {
        final Bundle params = new Bundle();
        //标题
        params.putString(QQShare.SHARE_TO_QQ_TITLE, (String) object.get(ShareConstant.PARAM_SHARE_TITLE));
        //链接
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, (String) object.get(ShareConstant.PARAM_SHARE_URL));
        //描述语言
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, (String) object.get(ShareConstant.PARAM_SHARE_DESCRIPTION));
        //应用名
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, ResourceUtils.getString(R.string.app_name));
        //分享类型
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

                /*QQ分享增加ARK*/
//        String arkStr = arkInfo.getText().toString();
//        params.putString(QQShare.SHARE_TO_QQ_ARK_INFO, arkStr);
               /*QQ分享增加ARK end*/

        AppManager.mTencent.shareToQQ((Activity) params.get(ShareConstant.PARAMS_CONTEXT), params, qqShareListener);
    }


    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Logger.i(">>>", "QQ分享取消");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Logger.i(">>>", "QQ分享完成");

        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Logger.i(">>>", "QQ分享错误" + e.errorDetail);
        }
    };
}
