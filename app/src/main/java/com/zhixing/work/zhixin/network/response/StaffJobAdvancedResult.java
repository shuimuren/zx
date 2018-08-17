package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.StaffJobAdvanceBean;
import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/8/15.
 * Description: 员工工作卡牌高级信息
 */

public class StaffJobAdvancedResult extends BaseResult {

    /**
     * Content : {"Nation":null,"NativePlaceProvince":null,"NativePlaceCity":null,"Married":null,"PoliticsFace":null,"IdCard":"123456789123456789","HouseholdType":null,"Province":null,"City":null,"District":null,"Address":null,"Education":50,"StudyAbroad":false,"WorkBgs":[{"CompanyName":"痛","StartDate":"2018-06-07T00:00:00","EndDate":"2018-07-10T00:00:00"},{"CompanyName":"gggg","StartDate":"2015-07-12T00:00:00","EndDate":"2016-07-12T00:00:00"}],"EducationBgs":[{"Education":60,"School":"空","StartDate":"2018-09-07T00:00:00","EndDate":"2018-06-07T00:00:00"},{"Education":40,"School":"Klondike","StartDate":"2018-07-07T00:00:00","EndDate":"2018-04-10T00:00:00"}],"CertificateBgs":[]}
     */

    private StaffJobAdvanceBean Content;

    public StaffJobAdvanceBean getContent() {
        return Content;
    }

    public void setContent(StaffJobAdvanceBean Content) {
        this.Content = Content;
    }


}
