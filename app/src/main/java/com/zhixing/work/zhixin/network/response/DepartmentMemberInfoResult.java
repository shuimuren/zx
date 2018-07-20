package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/7/18.
 * Description: 部门下员工列表
 */

public class DepartmentMemberInfoResult extends BaseResult {


    private String departmentId;

    private List<DepartmentMemberInfoBean> Content;

    public List<DepartmentMemberInfoBean> getContent() {
        return Content;
    }

    public void setContent(List<DepartmentMemberInfoBean> Content) {
        this.Content = Content;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

}
