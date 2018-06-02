package com.zhixing.work.zhixin.event;

import com.zhixing.work.zhixin.bean.JobType;

import java.util.List;

/*
 *
 */
public class JobListEvent {

    public JobListEvent(List<JobType> data) {
        this.data = data;
    }

    public List<JobType> getData() {
        return data;
    }

    public void setData(List<JobType> data) {
        this.data = data;
    }

    private List<JobType> data;

}
