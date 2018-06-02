package com.zhixing.work.zhixin.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class Salary  implements IPickerViewData {

    /**
     * Name : 3
     * Highest : [{"Name":"4"},{"Name":"5"},{"Name":"6"}]
     */

    private String Name;
    private List<HighestBean> Highest;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<HighestBean> getHighest() {
        return Highest;
    }

    public void setHighest(List<HighestBean> Highest) {
        this.Highest = Highest;
    }

    @Override
    public String getPickerViewText() {
        return this.Name;
    }

    public static class HighestBean {
        /**
         * Name : 4
         */

        private String Name;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
