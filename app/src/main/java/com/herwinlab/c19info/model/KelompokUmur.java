package com.herwinlab.c19info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KelompokUmur {

    @SerializedName("0-5")
    @Expose
    private Integer _05;
    @SerializedName("6-18")
    @Expose
    private Integer _618;
    @SerializedName("19-30")
    @Expose
    private Integer _1930;
    @SerializedName("31-45")
    @Expose
    private Integer _3145;
    @SerializedName("46-59")
    @Expose
    private Integer _4659;
    @SerializedName("\u2265 60")
    @Expose
    private Integer _60;

    public Integer get05() {
        return _05;
    }

    public void set05(Integer _05) {
        this._05 = _05;
    }

    public Integer get618() {
        return _618;
    }

    public void set618(Integer _618) {
        this._618 = _618;
    }

    public Integer get1930() {
        return _1930;
    }

    public void set1930(Integer _1930) {
        this._1930 = _1930;
    }

    public Integer get3145() {
        return _3145;
    }

    public void set3145(Integer _3145) {
        this._3145 = _3145;
    }

    public Integer get4659() {
        return _4659;
    }

    public void set4659(Integer _4659) {
        this._4659 = _4659;
    }

    public Integer get60() {
        return _60;
    }

    public void set60(Integer _60) {
        this._60 = _60;
    }

    public KelompokUmur(Integer _05, Integer _618, Integer _1930, Integer _3145, Integer _4659, Integer _60) {
        super();
        this._05 = _05;
        this._618 = _618;
        this._1930 = _1930;
        this._3145 = _3145;
        this._4659 = _4659;
        this._60 = _60;
    }
}
