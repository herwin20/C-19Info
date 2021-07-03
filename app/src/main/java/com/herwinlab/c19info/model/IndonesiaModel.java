package com.herwinlab.c19info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndonesiaModel {
    @SerializedName("updated")
    @Expose
    private Long updated;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("countryInfo")
    @Expose
    private CountryInfo countryInfo;
    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("todayCases")
    @Expose
    private Integer todayCases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("todayDeaths")
    @Expose
    private Integer todayDeaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("todayRecovered")
    @Expose
    private Integer todayRecovered;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("critical")
    @Expose
    private Integer critical;
    @SerializedName("casesPerOneMillion")
    @Expose
    private Integer casesPerOneMillion;
    @SerializedName("deathsPerOneMillion")
    @Expose
    private Integer deathsPerOneMillion;
    @SerializedName("tests")
    @Expose
    private Integer tests;
    @SerializedName("testsPerOneMillion")
    @Expose
    private Integer testsPerOneMillion;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("continent")
    @Expose
    private String continent;
    @SerializedName("oneCasePerPeople")
    @Expose
    private Integer oneCasePerPeople;
    @SerializedName("oneDeathPerPeople")
    @Expose
    private Integer oneDeathPerPeople;
    @SerializedName("oneTestPerPeople")
    @Expose
    private Integer oneTestPerPeople;
    @SerializedName("undefined")
    @Expose
    private Integer undefined;
    @SerializedName("activePerOneMillion")
    @Expose
    private Float activePerOneMillion;
    @SerializedName("recoveredPerOneMillion")
    @Expose
    private Float recoveredPerOneMillion;
    @SerializedName("criticalPerOneMillion")
    @Expose
    private Integer criticalPerOneMillion;

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Integer todayCases) {
        this.todayCases = todayCases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Integer todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(Integer todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(Integer casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public Integer getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(Integer deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public Integer getTests() {
        return tests;
    }

    public void setTests(Integer tests) {
        this.tests = tests;
    }

    public Integer getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(Integer testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(Integer oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public Integer getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(Integer oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public Integer getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(Integer oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public Integer getUndefined() {
        return undefined;
    }

    public void setUndefined(Integer undefined) {
        this.undefined = undefined;
    }

    public Float getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(Float activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public Float getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(Float recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public Integer getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(Integer criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

}
