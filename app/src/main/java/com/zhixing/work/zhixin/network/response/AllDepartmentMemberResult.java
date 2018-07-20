package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.DepartmentStaffsBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/7/18.
 * Description:
 */

public class AllDepartmentMemberResult extends BaseResult {

    private List<DepartmentStaffsBean> Content;

    public List<DepartmentStaffsBean> getContent() {
        return Content;
    }

    public void setContent(List<DepartmentStaffsBean> Content) {
        this.Content = Content;
    }

}
