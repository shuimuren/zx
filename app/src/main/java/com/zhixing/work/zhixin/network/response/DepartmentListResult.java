package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.DepartmentListBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/7/18.
 * Description: 账号下所有组织架构列表
 */

public class DepartmentListResult extends BaseResult {

    private List<DepartmentListBean> Content;

    public List<DepartmentListBean> getContent() {
        return Content;
    }

    public void setContent(List<DepartmentListBean> Content) {
        this.Content = Content;
    }

}
