package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class Expect  implements Serializable {

    private List<ResumeExpectJobOutputsBean> ResumeExpectJobOutputs;
    private List<ResumeExpectIndustryOutputsBean> ResumeExpectIndustryOutputs;
    private List<ResumeExpectAreaOutputsBean> ResumeExpectAreaOutputs;

    public List<ResumeExpectJobOutputsBean> getResumeExpectJobOutputs() {
        return ResumeExpectJobOutputs;
    }

    public void setResumeExpectJobOutputs(List<ResumeExpectJobOutputsBean> ResumeExpectJobOutputs) {
        this.ResumeExpectJobOutputs = ResumeExpectJobOutputs;
    }

    public List<ResumeExpectIndustryOutputsBean> getResumeExpectIndustryOutputs() {
        return ResumeExpectIndustryOutputs;
    }

    public void setResumeExpectIndustryOutputs(List<ResumeExpectIndustryOutputsBean> ResumeExpectIndustryOutputs) {
        this.ResumeExpectIndustryOutputs = ResumeExpectIndustryOutputs;
    }

    public List<ResumeExpectAreaOutputsBean> getResumeExpectAreaOutputs() {
        return ResumeExpectAreaOutputs;
    }

    public void setResumeExpectAreaOutputs(List<ResumeExpectAreaOutputsBean> ResumeExpectAreaOutputs) {
        this.ResumeExpectAreaOutputs = ResumeExpectAreaOutputs;
    }

    public static class ResumeExpectIndustryOutputsBean  implements Serializable{
        /**
         * IndustryTypeId : 5
         */

        private int IndustryTypeId;

        public int getIndustryTypeId() {
            return IndustryTypeId;
        }

        public void setIndustryTypeId(int IndustryTypeId) {
            this.IndustryTypeId = IndustryTypeId;
        }
    }

    public static class ResumeExpectAreaOutputsBean  implements Serializable{
        /**
         * AreaId : 1
         */

        private int AreaId;

        public int getAreaId() {
            return AreaId;
        }

        public void setAreaId(int AreaId) {
            this.AreaId = AreaId;
        }
    }
    public static class ResumeExpectJobOutputsBean  implements Serializable{

        /**
         * JobTypeId : 3
         */

        private int JobTypeId;

        public int getJobTypeId() {
            return JobTypeId;
        }

        public void setJobTypeId(int JobTypeId) {
            this.JobTypeId = JobTypeId;
        }
    }
}
