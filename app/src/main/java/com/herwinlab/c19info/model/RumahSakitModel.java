package com.herwinlab.c19info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RumahSakitModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("province")
    @Expose
    private String province;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public RumahSakitModel(String name, String address, String region, String phone, String province) {
        super();
        this.name = name;
        this.address = address;
        this.region = region;
        this.phone = phone;
        this.province = province;
    }
}
