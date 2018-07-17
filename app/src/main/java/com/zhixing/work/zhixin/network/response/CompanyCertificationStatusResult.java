package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.network.BaseResult;

/**
 * Created by lhj on 2018/7/17.
 * Description:
 */

public class CompanyCertificationStatusResult extends BaseResult {

    /**
     * Content : {"BusinessLicenseStatus":0,"ManagerIdCardStatus":0}
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
         * BusinessLicenseStatus : 0
         * ManagerIdCardStatus : 0
         */

        private int BusinessLicenseStatus;
        private int ManagerIdCardStatus;

        public int getBusinessLicenseStatus() {
            return BusinessLicenseStatus;
        }

        public void setBusinessLicenseStatus(int BusinessLicenseStatus) {
            this.BusinessLicenseStatus = BusinessLicenseStatus;
        }

        public int getManagerIdCardStatus() {
            return ManagerIdCardStatus;
        }

        public void setManagerIdCardStatus(int ManagerIdCardStatus) {
            this.ManagerIdCardStatus = ManagerIdCardStatus;
        }
    }
}
