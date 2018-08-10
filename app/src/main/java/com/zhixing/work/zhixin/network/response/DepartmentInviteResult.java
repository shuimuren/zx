package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.DepartmentInviteBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/18.
 * Description:
 */

public class DepartmentInviteResult extends BaseResult {

    /**
     * Content : {"Inviter":"吴","CompanyName":"成功后","Url":"http://9681.vicp.net:11285/#/invite?departId=15&uid=11&s=WycxMScsJ+WQtCcsJ+aIkOWKn+WQjidd"}
     */

    private DepartmentInviteBean Content;

    public DepartmentInviteBean getContent() {
        return Content;
    }

    public void setContent(DepartmentInviteBean Content) {
        this.Content = Content;
    }



}
