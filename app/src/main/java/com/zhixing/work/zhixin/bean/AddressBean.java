package com.zhixing.work.zhixin.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class AddressBean {
    private String Province;
    private String City;
    private String District;
    private String Address;

    public AddressBean(String province, String city, String district, String address, String detailedAddress) {
        Province = province;
        City = city;
        District = district;
        Address = address;
        DetailedAddress = detailedAddress;
    }

    public String getDetailedAddress() {
        return DetailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        DetailedAddress = detailedAddress;
    }

    private String DetailedAddress;


    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}

