package com.zhixing.work.zhixin.common;

import com.zhixing.work.zhixin.bean.DepartmentStaffsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lhj on 2018/7/20.
 * Description:
 */

public class DepartmentManagerHelper {


    private List<DepartmentStaffsBean> departmentStaffsBeans;
    Map<Integer, List<DepartmentStaffsBean>> departmentMap;

    public List<DepartmentStaffsBean> getDepartmentStaffsBeans() {
        return departmentStaffsBeans;

    }

    public void setDepartmentStaffsBeans(List<DepartmentStaffsBean> departmentStaffsBeans) {
        this.departmentStaffsBeans = departmentStaffsBeans;
        departmentMap();
    }

    private DepartmentManagerHelper() {
        departmentStaffsBeans = new ArrayList<>();
        departmentMap = new HashMap<>();
    }

    private static class DepartmentManagerHelperHolder {
        public static DepartmentManagerHelper instance = new DepartmentManagerHelper();
    }

    public static DepartmentManagerHelper getInstance() {
        return DepartmentManagerHelperHolder.instance;
    }




    public void departmentMap() {

        for (int i = 0; i < departmentStaffsBeans.size(); i++) {
            departmentMap.put(departmentStaffsBeans.get(i).getDepartmentId(), new ArrayList<>());

        }
        for (int i = 0; i < departmentStaffsBeans.size(); i++) {
            Integer key = departmentStaffsBeans.get(i).getParentId();
            Integer childKey = departmentStaffsBeans.get(i).getDepartmentId();
            if (departmentMap.containsKey(key)) {
                departmentMap.get(key).add(departmentStaffsBeans.get(i));
            }
            if (departmentMap.containsKey(childKey)) {
                departmentMap.get(childKey).add(departmentStaffsBeans.get(i));
            }
        }
    }

    public int getStaffTotalByDepartmentId(int departmentId) {
        Set<Integer> setList = new HashSet<>();
        List<DepartmentStaffsBean> beans = departmentMap.get(departmentId);
        for (int i = 0; i < beans.size(); i++) {
            setList.addAll(beans.get(i).getStaffIds());
            Integer parentId = beans.get(i).getDepartmentId();
            while (departmentMap.containsKey(parentId)) {
                if(departmentMap.get(parentId).size() >1){
                    for (int j = 0; j < departmentMap.get(parentId).size(); j++) {
                        setList.addAll(departmentMap.get(parentId).get(j).getStaffIds());
                        parentId = departmentMap.get(parentId).get(j).getDepartmentId();
                    }
                }else{
                    parentId = -1;
                }

            }
        }

       return setList.size();

    }


}
