package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.NewMemberBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/10.
 * Description: 加入组织申请列表
 */

public class AuditMemberResult extends BaseResult {

    private List<NewMemberBean> Content;

    public List<NewMemberBean> getContent() {
        return Content;
    }

    public void setContent(List<NewMemberBean> Content) {
        this.Content = Content;
    }


}
