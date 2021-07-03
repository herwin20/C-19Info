package com.herwinlab.c19info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Penambahan {

    @SerializedName("positif")
    @Expose
    private Integer positif;
    @SerializedName("sembuh")
    @Expose
    private Integer sembuh;
    @SerializedName("meninggal")
    @Expose
    private Integer meninggal;

    public Integer getPositif() {
        return positif;
    }

    public void setPositif(Integer positif) {
        this.positif = positif;
    }

    public Integer getSembuh() {
        return sembuh;
    }

    public void setSembuh(Integer sembuh) {
        this.sembuh = sembuh;
    }

    public Integer getMeninggal() {
        return meninggal;
    }

    public void setMeninggal(Integer meninggal) {
        this.meninggal = meninggal;
    }

    public Penambahan(Integer positif, Integer sembuh, Integer meninggal) {
        super();
        this.positif = positif;
        this.sembuh = sembuh;
        this.meninggal = meninggal;
    }

}
