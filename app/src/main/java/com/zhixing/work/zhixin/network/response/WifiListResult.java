package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.WifiBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class WifiListResult extends BaseResult {

    private List<WifiBean> Content;

    public List<WifiBean> getContent() {
        return Content;
    }

    public void setContent(List<WifiBean> Content) {
        this.Content = Content;
    }


}
