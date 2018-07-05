package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/5.
 * Description: 获取StsToken
 */

public class StsTokenResult extends BaseResult{


    public StsToken getContent() {
        return Content;
    }

    public void setContent(StsToken content) {
        Content = content;
    }

    private StsToken Content;


}
