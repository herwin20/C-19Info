package com.herwinlab.c19info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProvinsiModel {

      @SerializedName("provinsi")
      @Expose
      private String provinsi;
      @SerializedName("kasus")
      @Expose
      private Integer kasus;
      @SerializedName("dirawat")
      @Expose
      private Integer dirawat;
      @SerializedName("sembuh")
      @Expose
      private Integer sembuh;
      @SerializedName("meninggal")
      @Expose
      private Integer meninggal;
      @SerializedName("last_date")
      @Expose
      private String lastDate;
      @SerializedName("jenis_kelamin")
      @Expose
      private JenisKelamin jenisKelamin;
      @SerializedName("kelompok_umur")
      @Expose
      private KelompokUmur kelompokUmur;
      @SerializedName("penambahan")
      @Expose
      private Penambahan penambahan;

      public ProvinsiModel() {

      }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public Integer getKasus() {
        return kasus;
    }

    public void setKasus(Integer kasus) {
        this.kasus = kasus;
    }

    public Integer getDirawat() {
        return dirawat;
    }

    public void setDirawat(Integer dirawat) {
        this.dirawat = dirawat;
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

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public KelompokUmur getKelompokUmur() {
        return kelompokUmur;
    }

    public void setKelompokUmur(KelompokUmur kelompokUmur) {
        this.kelompokUmur = kelompokUmur;
    }

    public Penambahan getPenambahan() {
        return penambahan;
    }

    public void setPenambahan(Penambahan penambahan) {
        this.penambahan = penambahan;
    }

    public ProvinsiModel(String provinsi, Integer kasus, Integer dirawat, Integer sembuh, Integer meninggal, String lastDate, JenisKelamin jenisKelamin, KelompokUmur kelompokUmur, Penambahan penambahan) {
        super();
        this.provinsi = provinsi;
        this.kasus = kasus;
        this.dirawat = dirawat;
        this.sembuh = sembuh;
        this.meninggal = meninggal;
        this.lastDate = lastDate;
        this.jenisKelamin = jenisKelamin;
        this.kelompokUmur = kelompokUmur;
        this.penambahan = penambahan;
    }
  }
