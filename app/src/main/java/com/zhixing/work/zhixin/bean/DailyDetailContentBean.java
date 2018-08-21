package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by lhj on 2018/8/21.
 * Description:
 */

public class DailyDetailContentBean {
    /**
     * TotalNumber : 1
     * TotalPage : 1
     * Data : [{"StaffId":8,"Avatar":null,"RealName":"xxx","DepartmentName":"技术部","ClockInTime":null,"ClockOutTime":null,"StartTime":"09:00:00","EndTime":"19:00:00","FlexTime":5,"AbsenteeismTime":60}]
     */

    private int TotalNumber;
    private int TotalPage;
    private List<DailyDetailBean> Data;

    public int getTotalNumber() {
        return TotalNumber;
    }

    public void setTotalNumber(int TotalNumber) {
        this.TotalNumber = TotalNumber;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int TotalPage) {
        this.TotalPage = TotalPage;
    }

    public List<DailyDetailBean> getData() {
        return Data;
    }

    public void setData(List<DailyDetailBean> Data) {
        this.Data = Data;
    }


}
