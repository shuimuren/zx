package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/7/18.
 * Description: 子部门
 */

public class ChildDepartmentResult extends BaseResult {
    /**
     * Content : {"CurrentDepartmentName":"XXX科技有限公司","SubDepartments":[{"DepartmentId":8,"DepartmentName":"技术部"},{"DepartmentId":12,"DepartmentName":"营销部"}]}
     */

    private ContentBean Content;
    private String departmentId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * CurrentDepartmentName : XXX科技有限公司
         * SubDepartments : [{"DepartmentId":8,"DepartmentName":"技术部"},{"DepartmentId":12,"DepartmentName":"营销部"}]
         */

        private String CurrentDepartmentName;
        private List<ChildDepartmentBean> SubDepartments;

        public String getCurrentDepartmentName() {
            return CurrentDepartmentName;
        }

        public void setCurrentDepartmentName(String CurrentDepartmentName) {
            this.CurrentDepartmentName = CurrentDepartmentName;
        }

        public List<ChildDepartmentBean> getSubDepartments() {
            return SubDepartments;
        }

        public void setSubDepartments(List<ChildDepartmentBean> SubDepartments) {
            this.SubDepartments = SubDepartments;
        }


    }

}
