package com.zhixing.work.zhixin.share;

import android.graphics.Bitmap;

import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.Map;

/**
 * Created by lhj on 2018/7/17.
 * Description:
 */

public class BaseShareUtil {

    private static final int THUMB_SIZE = 150;
    protected String mShareUrl;
    protected String mShareTitle;
    protected String mShareDescription;
    protected Bitmap mShareThumbnail;

    /**
     * 解析分享相关参数
     * @param params
     */
    protected void explainParams(Map<String, Object> params) {
        Object url = params.get(ShareConstant.PARAM_SHARE_URL);
        if (url != null) {
            mShareUrl = url.toString();
        }else{
            AlertUtils.show("分享链接为空");
            return;
        }

        Object title = params.get(ShareConstant.PARAM_SHARE_TITLE);
        if (title != null) {
            mShareTitle = title.toString();
        }else{
            AlertUtils.show("分享标题为空");
            return;
        }

        Object description = params.get(ShareConstant.PARAM_SHARE_DESCRIPTION);
        if (description != null) {
            mShareDescription = description.toString();
        }else{
            AlertUtils.show("分享描述为空");
            return;
        }

        Object thumbnail = params.get(ShareConstant.PARAM_SHARE_THUMBNAIL);
        if (thumbnail != null) {
            Bitmap bmp = (Bitmap) thumbnail;
            mShareThumbnail = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            //mShareThumbnail = (Bitmap) thumbnail;
        }


    }
}
