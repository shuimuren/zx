package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.EvaluateBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/10.
 * Description:
 */

public class EvaluateResult extends BaseResult {
    EvaluateBean Content;
    public EvaluateBean getContent() {
        return Content;
    }

    public void setContent(EvaluateBean content) {
        Content = content;
    }


}
