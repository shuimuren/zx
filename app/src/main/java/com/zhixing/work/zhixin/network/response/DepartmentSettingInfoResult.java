package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.DepartmentSettingInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/19.
 * Description:
 */

public class DepartmentSettingInfoResult extends BaseResult {

    /**
     * Content : {"Id":7,"CompanyId":1,"ParentId":0,"ParentName":null,"Name":"XXX有限公司","Hide":false,"ManagerId":null,"ManagerName":null,"DepartmentIds":[],"StaffIds":[]}
     */

    private DepartmentSettingInfoBean Content;

    public DepartmentSettingInfoBean getContent() {
        return Content;
    }

    public void setContent(DepartmentSettingInfoBean Content) {
        this.Content = Content;
    }

}
