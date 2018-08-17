package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.LeaveStaffBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class LeaveStaffListResult extends BaseResult {

    /**
     * Content : {"DmsStaffs":[{"StatffId":8,"Avatar":null,"RealName":"xxx","DepartmentName":"职信 - 运营部","DimissionTime":"2018-06-01 11:00:00"},{"StatffId":7,"Avatar":null,"RealName":"wu","DepartmentName":"职信","DimissionTime":"2018-06-01 10:28:05"}],"RowsCount":2}
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
         * DmsStaffs : [{"StatffId":8,"Avatar":null,"RealName":"xxx","DepartmentName":"职信 - 运营部","DimissionTime":"2018-06-01 11:00:00"},{"StatffId":7,"Avatar":null,"RealName":"wu","DepartmentName":"职信","DimissionTime":"2018-06-01 10:28:05"}]
         * RowsCount : 2
         */

        private int RowsCount;
        private List<LeaveStaffBean> DmsStaffs;

        public int getRowsCount() {
            return RowsCount;
        }

        public void setRowsCount(int RowsCount) {
            this.RowsCount = RowsCount;
        }

        public List<LeaveStaffBean> getDmsStaffs() {
            return DmsStaffs;
        }

        public void setDmsStaffs(List<LeaveStaffBean> DmsStaffs) {
            this.DmsStaffs = DmsStaffs;
        }

    }
}
