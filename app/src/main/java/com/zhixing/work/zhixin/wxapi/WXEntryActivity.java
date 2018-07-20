package com.zhixing.work.zhixin.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.share.ShareConstant;

/**
 * Created by lhj on 2018/7/17.
 * Description:
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler{

    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_entry);
        api = WXAPIFactory.createWXAPI(this, ShareConstant.WX_APP_ID);

        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }
    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            //发送成功
            case BaseResp.ErrCode.ERR_OK:

                break;
            //发送取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                break;
            //发送被拒绝
            case BaseResp.ErrCode.ERR_AUTH_DENIED:

                break;
             //不支持错误
            case BaseResp.ErrCode.ERR_UNSUPPORT:

                break;
             //发送返回
            default:

                break;

        }
        this.finish();
    }
}
