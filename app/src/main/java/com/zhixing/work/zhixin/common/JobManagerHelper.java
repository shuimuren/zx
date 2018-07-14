package com.zhixing.work.zhixin.common;

import com.google.gson.Gson;
import com.zhixing.work.zhixin.bean.JobChildBean;
import com.zhixing.work.zhixin.bean.JobChoice;
import com.zhixing.work.zhixin.bean.JobChoiceList;
import com.zhixing.work.zhixin.bean.JobParentBean;
import com.zhixing.work.zhixin.bean.JobSelectedBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhj on 2018/7/11.
 * Description: 期望职业辅助类
 */

public class JobManagerHelper {

    private JobChoiceList listData;
    private List<JobChoice> jobChoice;
    private List<JobParentBean> jobParentData;
    private List<JobChildBean> jobChildData;
    private List<List<JobChildBean>> jobChildAllData;
    private List<JobSelectedBean> mJobSelectedBeanList;

    private JobManagerHelper() {
        jobParentData = new ArrayList<>();
        jobChildData = new ArrayList<>();
        jobChoice = new ArrayList<>();
        jobChildAllData = new ArrayList<>();
        mJobSelectedBeanList = new ArrayList<>();
    }

    public List<JobChoice> getJobChoice() {
        return jobChoice;
    }

    public void setJobChoice(List<JobChoice> jobChoice) {
        this.jobChoice = jobChoice;
    }

    public JobChoiceList getListData() {
        return listData;
    }

    public void setListData(JobChoiceList listData) {
        this.listData = listData;
    }

    public List<JobParentBean> getJobParentData() {
        return jobParentData;
    }

    public void setJobParentData(List<JobParentBean> jobParentData) {
        this.jobParentData = jobParentData;
    }

    public List<JobChildBean> getJobChildData() {
        return jobChildData;
    }

    public void setJobChildData(List<JobChildBean> jobChildData) {
        this.jobChildData = jobChildData;
    }

    public List<JobSelectedBean> getJobSelectedBeanList() {
        return mJobSelectedBeanList;
    }

    public void setJobSelectedBeanList(List<JobSelectedBean> mJobSelectedBeanList) {
        this.mJobSelectedBeanList = mJobSelectedBeanList;
    }

    private static class JobManagerHelperHolder {
        public static JobManagerHelper jobManager = new JobManagerHelper();
    }

    public static JobManagerHelper getJobManagerHelperInstance() {
        return JobManagerHelperHolder.jobManager;
    }

    /**
     * 讲json字符串解析为json对象
     *
     * @param jsonData
     */
    public void parseJobData(String jsonData) {
        Gson gson = new Gson();
        JobChoiceList jobs = gson.fromJson(jsonData, JobChoiceList.class);
        jobChoice.clear();
        jobChoice.addAll(jobs.getJobChoices());

    }

    public List<JobParentBean> getParentByPosition(int position) {

        return jobChoice.get(position).getChild();
    }

    /**
     * 获取子列表
     *
     * @param position
     * @return
     */
    public List<List<JobChildBean>> getChildAllList(int position) {
        List<List<JobChildBean>> listData = new ArrayList<>();
        List<JobParentBean> jobParentBeans = jobChoice.get(position).getChild();
        for (int i = 0; i < jobParentBeans.size(); i++) {
            listData.add(jobParentBeans.get(i).getChild());
        }
        jobChildAllData.addAll(listData);
        return listData;
    }

    public List<JobChildBean> getChildByPosition(int parentPosition, int position) {
        return jobChoice.get(parentPosition).getChild().get(position).getChild();
    }

    /**
     * 选中大项
     *
     * @param position
     * @param groupPosition
     * @param isChecked
     */
    public void setGroupSelected(int position, int groupPosition, boolean isChecked) {
        JobParentBean parentBean = jobChoice.get(position).getChild().get(groupPosition);
        parentBean.setChecked(!isChecked);
        JobSelectedBean bean = new JobSelectedBean(parentBean.getId(), parentBean.getParentId(), parentBean.getName());
        List<JobSelectedBean> list1 = new ArrayList<>();
        list1.addAll(mJobSelectedBeanList);
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < mJobSelectedBeanList.size(); j++) {
                if (bean.getId() == list1.get(i).getParentId()) {
                    mJobSelectedBeanList.remove(j);
                }
            }
        }
        if (isChecked) {
            List<JobSelectedBean> list2 = new ArrayList<>();
            list2.addAll(mJobSelectedBeanList);

            for (int i = 0; i < list2.size(); i++) {
                if (list2.get(i).getId() == bean.getId()) {
                    mJobSelectedBeanList.remove(i);
                }

            }

        } else {
            mJobSelectedBeanList.add(bean);
        }
        setAllChildSelectedFalse(position, groupPosition);

    }

    /**
     * 选中子项
     *
     * @param position
     * @param
     * @param isChecked
     */
    public void setChildSelected(JobChildBean bean, int position, int groupPosition, boolean isChecked) {
        JobChildBean childBean = bean;
        childBean.setChecked(!isChecked);
        JobSelectedBean currentBean = new JobSelectedBean(bean.getId(), bean.getParentId(), bean.getName());
        //移除所有父类
        List<JobSelectedBean> list3 = new ArrayList<>();
        list3.addAll(mJobSelectedBeanList);
        for (int i = 0; i < list3.size(); i++) {
            if (childBean.getParentId() == list3.get(i).getId()) {
                mJobSelectedBeanList.remove(i);
            }
        }

        if (isChecked) {
            List<JobSelectedBean> list4 = new ArrayList<>();
            list4.addAll(mJobSelectedBeanList);
            for (int i = 0; i < list4.size(); i++) {
                if (childBean.getId() == list4.get(i).getId()) {
                    mJobSelectedBeanList.remove(i);
                }
            }

        } else {
            mJobSelectedBeanList.add(currentBean);
        }

        setParentGroupSelectedFalse(position, groupPosition);

    }

    /**
     * 选择大项时取消选择子项
     *
     * @param position
     * @param groupPosition
     */
    public void setAllChildSelectedFalse(int position, int groupPosition) {
        List<JobChildBean> childBeans = jobChoice.get(position).getChild().get(groupPosition).getChild();
        int size = childBeans.size();
        for (int i = 0; i < size; i++) {
            childBeans.get(i).setChecked(false);
        }
    }

    /**
     * 选中子项时,取消选中大项
     */
    public void setParentGroupSelectedFalse(int position, int groupPosition) {
        jobChoice.get(position).getChild().get(groupPosition).setChecked(false);
    }

    /**
     * 清空所有选中项
     */
    public void clearSelectedData() {
        if (mJobSelectedBeanList != null) {
            mJobSelectedBeanList.clear();
        }
    }

    /**
     * 点击
     *
     * @param bean
     * @param isChecked
     * @return
     */
    public boolean showParentToast(JobParentBean bean, boolean isChecked) {
        int totalSize = getJobSelectedBeanList().size();
        if ((totalSize > 3 || totalSize == 3) && !isChecked && !childToParent(bean)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean showChildToast(JobChildBean bean, boolean isChecked) {
        int totalSize = getJobSelectedBeanList().size();
        if ((totalSize > 3 || totalSize == 3) && !isChecked && !parentToChild(bean)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 一开始选择大项,后选择子项
     *
     * @param bean
     * @return
     */
    public boolean parentToChild(JobChildBean bean) {
        boolean isParentToChild = false;
        for (int i = 0; i < mJobSelectedBeanList.size(); i++) {
            if (bean.getParentId() == mJobSelectedBeanList.get(i).getId()) {
                isParentToChild = true;
            }
        }
        return isParentToChild;
    }

    /**
     * 一开始选择大项,后选择子项
     *
     * @param bean
     * @return
     */
    public boolean childToParent(JobParentBean bean) {
        boolean isChildToParent = false;
        for (int i = 0; i < mJobSelectedBeanList.size(); i++) {
            if (bean.getId() == mJobSelectedBeanList.get(i).getParentId()) {
                isChildToParent = true;
            }
        }
        return isChildToParent;
    }

    public void removeSelectedJobBean(int size, int id, int position, int groupPosition, int childPosition) {
        Logger.i(">>>", "size1>" + mJobSelectedBeanList.size() +">>>id>>>"+id);
        for (int i = 0; i < mJobSelectedBeanList.size(); i++) {
            if (id == mJobSelectedBeanList.get(i).getId()) {
                mJobSelectedBeanList.remove(i);
                break;
            }
        }
        Logger.i(">>>", "size2>>" + mJobSelectedBeanList.size());

        if (childPosition != -1) {
            jobChoice.get(position).getChild().get(groupPosition).getChild().get(childPosition).setChecked(false);
        } else {
            jobChoice.get(position).getChild().get(groupPosition).setChecked(false);
        }

    }


}
