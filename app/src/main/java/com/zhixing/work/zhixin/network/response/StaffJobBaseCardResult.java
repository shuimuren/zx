package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.StaffJobBaseCardBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/15.
 * Description: 工作卡牌基础信息
 */

public class StaffJobBaseCardResult extends BaseResult {

    /**
     * Content : {"RealName":"印度阿三","PhoneNum":"13137551611","Email":"lhj@163.com","NickName":"阿三","Sex":0,"Birthday":"2018-10-14T00:00:00","Constellation":3,"Stature":10,"Weight":123,"Motto":"fool呢","FirstWorkTime":"2018-07-09T00:00:00"}
     */

    private StaffJobBaseCardBean Content;

    public StaffJobBaseCardBean getContent() {
        return Content;
    }

    public void setContent(StaffJobBaseCardBean Content) {
        this.Content = Content;
    }

   
}
