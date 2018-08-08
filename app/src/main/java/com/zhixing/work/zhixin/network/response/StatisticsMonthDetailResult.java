package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.StatisticsMonthDataBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description:
 */

public class StatisticsMonthDetailResult extends BaseResult{


    /**
     * Content : {"TotalNumber":2,"TotalPage":1,"Data":[{"StaffId":8,"Avatar":null,"RealName":"xxx","DepartmentName":"技术部","Count":3,"LateMinutes":0},{"StaffId":7,"Avatar":null,"RealName":"吴","DepartmentName":"技术部","Count":1,"LateMinutes":0}]}
     */

    private ContentBean Content;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * TotalNumber : 2
         * TotalPage : 1
         * Data : [{"StaffId":8,"Avatar":null,"RealName":"xxx","DepartmentName":"技术部","Count":3,"LateMinutes":0},{"StaffId":7,"Avatar":null,"RealName":"吴","DepartmentName":"技术部","Count":1,"LateMinutes":0}]
         */

        private int TotalNumber;
        private int TotalPage;
        private List<StatisticsMonthDataBean> Data;

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

        public List<StatisticsMonthDataBean> getData() {
            return Data;
        }

        public void setData(List<StatisticsMonthDataBean> Data) {
            this.Data = Data;
        }

    }
}
