package com.zhixing.work.zhixin.common;

import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.bean.DepartmentStaffsBean;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lhj on 2018/7/20.
 * Description:组织架构管理类
 */

public class DepartmentManagerHelper {


    private List<DepartmentStaffsBean> departmentStaffsBeans;
    private Map<Integer, List<DepartmentStaffsBean>> departmentMap;

    private Map<String, List<ChildDepartmentBean>> cacheDepartmentChild;
    private Map<String, List<DepartmentMemberInfoBean>> cacheDepartmentMember;
    private Set<Integer> selectedDepartmentStaffs; //编辑时选择


    private List<Integer> editSelectedMembers; //传递过来的选中


    public Set<Integer> getSelectedDepartmentStaffs() {
        return selectedDepartmentStaffs;
    }

    public void setSelectedDepartmentStaffs(Set<Integer> selectedDepartmentStaffs) {
        this.selectedDepartmentStaffs = selectedDepartmentStaffs;
    }


    public List<DepartmentStaffsBean> getDepartmentStaffsBeans() {
        return departmentStaffsBeans;

    }


    public List<Integer> getEditSelectedMembers() {
        return editSelectedMembers;
    }

    public void setEditSelectedMembers(List<Integer> editSelectedMembers) {
        this.editSelectedMembers = editSelectedMembers;
        selectedDepartmentStaffs.addAll(editSelectedMembers);
    }

    public void setDepartmentStaffsBeans(List<DepartmentStaffsBean> departmentStaffsBeans) {
        this.departmentStaffsBeans = departmentStaffsBeans;
        departmentMap();
    }

    private DepartmentManagerHelper() {
        departmentStaffsBeans = new ArrayList<>();
        departmentMap = new HashMap<>();

        cacheDepartmentChild = new HashMap<>();
        cacheDepartmentMember = new HashMap<>();
        selectedDepartmentStaffs = new HashSet<>();
        editSelectedMembers = new ArrayList<>();
    }

    private static class DepartmentManagerHelperHolder {
        public static DepartmentManagerHelper instance = new DepartmentManagerHelper();
    }


    public static DepartmentManagerHelper getInstance() {
        return DepartmentManagerHelperHolder.instance;
    }

    public void ClearCache() {
        cacheDepartmentChild.clear();
        cacheDepartmentMember.clear();
        selectedDepartmentStaffs.clear();
    }

    public void setCacheDepartmentMember(String departmentId, List<DepartmentMemberInfoBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if(editSelectedMembers.contains(list.get(i).getStaffId())){
                list.get(i).setSelected(true);
                setDepartmentMemberSelected(String.valueOf(list.get(i).getStaffId()),true);
            }
        }
        cacheDepartmentMember.put(departmentId, list);
    }


    public void setCacheDepartmentChild(String departmentId, List<ChildDepartmentBean> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setMemberTotal(getStaffTotalByDepartmentId(list.get(i).getDepartmentId()));
        }
        cacheDepartmentChild.put(departmentId, list);
    }

    /**
     * 获取未分组列表
     *
     * @param departmentId
     * @return
     */
    public List<DepartmentMemberInfoBean> getDepartmentMemberList(String departmentId) {
        if (hasDepartmentMemberCache(departmentId)) {
            return cacheDepartmentMember.get(departmentId);
        } else {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER, departmentId);
            return null;
        }

    }

    /**
     * 获取组织列表
     *
     * @param departmentId
     * @return
     */
    public List<ChildDepartmentBean> getDepartmentChildList(String departmentId) {
        if (hasDepartmentChildCache(departmentId)) {
            return cacheDepartmentChild.get(departmentId);
        } else {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, departmentId);
            return null;
        }
    }

    /**
     * 选中所有或不选中
     *
     * @param departmentId
     * @param isSelectedAll
     */
    public void setAllDepartmentSelected(String departmentId, boolean isSelectedAll) {
        List<ChildDepartmentBean> listChild = getDepartmentChildList(departmentId);
        List<DepartmentMemberInfoBean> listMember = getDepartmentMemberList(departmentId);
        if (listChild != null) {
            int childSize = listChild.size();
            for (int i = 0; i < childSize; i++) {
                listChild.get(i).setSelected(isSelectedAll);
            }
        }

        if (listMember != null) {
            int memberSize = listMember.size();
            for (int i = 0; i < memberSize; i++) {
                listMember.get(i).setSelected(isSelectedAll);
            }
        }

        if (listChild != null) {
            for (int i = 0; i < listChild.size(); i++) {
                setDepartmentChildSelected(String.valueOf(listChild.get(i).getDepartmentId()), isSelectedAll);
            }
        }

        if (listMember != null) {
            for (int i = 0; i < listChild.size(); i++) {
                setDepartmentChildSelected(departmentId, isSelectedAll);
            }
        }

    }

    public int getDepartmentSelectedTotal(int departmentId) {

        Set<Integer> sameSet = new HashSet<>();
        /*
         * 利用ForEach循环和HashSet中的contains方法判断两个Set中元素是否相交
         * 相交则存入SameSet中
         */
        for (Integer s : getTotalSetList(departmentId)) {
            if (selectedDepartmentStaffs.contains(s))
                sameSet.add(s);
        }
        return sameSet.size();

    }

    /**
     * 选中或不选中组织列表
     *
     * @param departmentId
     * @param isSelected
     */
    public void setDepartmentChildSelected(String departmentId, boolean isSelected) {
        if (isSelected) {
            setAllSelected(Integer.parseInt(departmentId));
        } else {
            removeSelected(Integer.parseInt(departmentId));
        }
    }

    /**
     * 选中或不选中个人
     * <p>
     * //@param parentDepartmentId
     *
     * @param departmentId
     * @param isSelected
     */
    public void setDepartmentMemberSelected(String departmentId, boolean isSelected) {
        if (isSelected) {
            selectedDepartmentStaffs.add(Integer.parseInt(departmentId));
        } else {
            selectedDepartmentStaffs.remove(Integer.parseInt(departmentId));
        }
    }


    /**
     * 判断未分组列表是否存在缓存在,存在则从缓存加载,不存在则从网络获取
     *
     * @param departmentId
     * @return
     */
    public boolean hasDepartmentMemberCache(String departmentId) {
        if (cacheDepartmentMember.containsKey(departmentId)) {
            return true;
        }
        return false;
    }

    /**
     * 判断组织列表是否存在缓存在,存在则从缓存加载,不存在则从网络获取
     *
     * @param departmentId
     * @return
     */
    public boolean hasDepartmentChildCache(String departmentId) {
        if (cacheDepartmentChild.containsKey(departmentId)) {
            return true;
        }
        return false;

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
        if (departmentMap == null || departmentMap.get(departmentId) == null) {
            return setList.size();
        }
        List<DepartmentStaffsBean> beans = departmentMap.get(departmentId);
        for (int i = 0; i < beans.size(); i++) {
            setList.addAll(beans.get(i).getStaffIds());
            Integer parentId = beans.get(i).getDepartmentId();
            while (departmentMap.containsKey(parentId)) {
                if (departmentMap.get(parentId).size() > 1) {
                    for (int j = 0; j < departmentMap.get(parentId).size(); j++) {
                        setList.addAll(departmentMap.get(parentId).get(j).getStaffIds());
                        parentId = departmentMap.get(parentId).get(j).getDepartmentId();
                    }
                } else {
                    parentId = -1;
                }

            }
        }
        return setList.size();

    }

    public Set<Integer> getTotalSetList(int departmentId) {
        Set<Integer> setList = new HashSet<>();
        if (departmentMap == null || departmentMap.get(departmentId) == null) {
            return setList;
        }
        List<DepartmentStaffsBean> beans = departmentMap.get(departmentId);
        for (int i = 0; i < beans.size(); i++) {
            setList.addAll(beans.get(i).getStaffIds());
            Integer parentId = beans.get(i).getDepartmentId();
            while (departmentMap.containsKey(parentId)) {
                if (departmentMap.get(parentId).size() > 1) {
                    for (int j = 0; j < departmentMap.get(parentId).size(); j++) {
                        setList.addAll(departmentMap.get(parentId).get(j).getStaffIds());
                        parentId = departmentMap.get(parentId).get(j).getDepartmentId();
                    }
                } else {
                    parentId = -1;
                }

            }
        }
        return setList;
    }

    public void setAllSelected(int departmentId) {
        List<DepartmentStaffsBean> beans = departmentMap.get(departmentId);
        for (int i = 0; i < beans.size(); i++) {
            selectedDepartmentStaffs.addAll(beans.get(i).getStaffIds());
            Integer parentId = beans.get(i).getDepartmentId();
            while (departmentMap.containsKey(parentId)) {
                if (departmentMap.get(parentId).size() > 1) {
                    for (int j = 0; j < departmentMap.get(parentId).size(); j++) {
                        selectedDepartmentStaffs.addAll(departmentMap.get(parentId).get(j).getStaffIds());
                        parentId = departmentMap.get(parentId).get(j).getDepartmentId();
                    }
                } else {
                    parentId = -1;
                }

            }
        }
    }

    public void removeSelected(int departmentId) {
        List<DepartmentStaffsBean> beans = departmentMap.get(departmentId);
        for (int i = 0; i < beans.size(); i++) {
            selectedDepartmentStaffs.removeAll(beans.get(i).getStaffIds());
            Integer parentId = beans.get(i).getDepartmentId();
            while (departmentMap.containsKey(parentId)) {
                if (departmentMap.get(parentId).size() > 1) {
                    for (int j = 0; j < departmentMap.get(parentId).size(); j++) {
                        selectedDepartmentStaffs.removeAll(departmentMap.get(parentId).get(j).getStaffIds());
                        parentId = departmentMap.get(parentId).get(j).getDepartmentId();
                    }
                } else {
                    parentId = -1;
                }

            }
        }
    }




}
