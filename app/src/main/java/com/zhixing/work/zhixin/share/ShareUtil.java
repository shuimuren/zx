package com.zhixing.work.zhixin.share;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.common.Logger;

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
     * @param params
     */
    public void shareToFriends(Map<String, Object> params) {
        Logger.i(">>>","分享给微信好友");
        shareToWeiXin(params, ShareConstant.SHARE_TO_FRIEND);
    }

    /**
     * 分享到朋友圈
     * @param params
     */
    public void shareToTimeLine(Map<String, Object> params) {
        shareToWeiXin(params, ShareConstant.SHARE_TO_FRIEND);
        Logger.i(">>>","分享到朋友圈");
    }

    /**
     * 分享到QQ
     *
     */
    public void shareToQQ(Map<String, Object> params){
        Logger.i(">>>","分享到QQ");
    }

    /**
     * 分享到SMS
     *
     */
    public void shareToSMS(Map<String, Object> params){
        Logger.i(">>>","分享到SMS");
    }

    /**
     * 分享到职信
     *
     */
    public void shareToZX(Map<String, Object> params){
        Logger.i(">>>","分享到ZX");
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
}
