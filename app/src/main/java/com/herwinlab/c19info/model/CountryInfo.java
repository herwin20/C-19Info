package com.herwinlab.c19info.model;

import com.google.gson.internal.ObjectConstructor;

public class CountryInfo {
    private float _id;
    private String iso2;
    private String iso3;
    private float lat;
    private float longatt;
    private String flag;

    public float get_id() {
        return _id;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public float getLat() {
        return lat;
    }

    public float getLongatt() {
        return longatt;
    }

    public String getFlag() {
        return flag;
    }

    //Constructor
    public CountryInfo(float _id, String iso2, String iso3, float lat, float longatt, String flag) {
        this._id = _id;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.lat = lat;
        this.longatt = longatt;
        this.flag = flag;
    }
}
