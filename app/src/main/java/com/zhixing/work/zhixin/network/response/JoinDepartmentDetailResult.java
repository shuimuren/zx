package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.NewMemberInfoBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/10.
 * Description: 获取申请详情
 */

public class JoinDepartmentDetailResult extends BaseResult {

    /**
     * Content : {"Id":2,"RealName":"wu","Avatar":null,"PhoneNum":"18676740875","DepartmentId":8,"DepartmentName":["XXX有限公司","技术部"],"Inviter":"主管理员","InviterRole":null,"ApplyTime":"2018-05-25 15:45:40","AuditTime":null,"AuditStatus":0,"AttendanceRules":[{"Id":1,"Name":"考勤组1"}]}
     */

    private NewMemberInfoBean Content;

    public NewMemberInfoBean getContent() {
        return Content;
    }

    public void setContent(NewMemberInfoBean Content) {
        this.Content = Content;
    }



}
