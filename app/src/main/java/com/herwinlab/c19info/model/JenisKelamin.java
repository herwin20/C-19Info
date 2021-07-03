package com.herwinlab.c19info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JenisKelamin {

    @SerializedName("laki-laki")
    @Expose
    private Integer lakiLaki;
    @SerializedName("perempuan")
    @Expose
    private Integer perempuan;

    public Integer getLakiLaki() {
        return lakiLaki;
    }

    public void setLakiLaki(Integer lakiLaki) {
        this.lakiLaki = lakiLaki;
    }

    public Integer getPerempuan() {
        return perempuan;
    }

    public void setPerempuan(Integer perempuan) {
        this.perempuan = perempuan;
    }

    public JenisKelamin(Integer lakiLaki, Integer perempuan) {
        super();
        this.lakiLaki = lakiLaki;
        this.perempuan = perempuan;
    }
}
