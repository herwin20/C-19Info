package com.herwinlab.c19info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorldCasesModel {

    @SerializedName("updated")
    @Expose
    private Long updated;
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
    private Float deathsPerOneMillion;
    @SerializedName("tests")
    @Expose
    private Long tests;
    @SerializedName("testsPerOneMillion")
    @Expose
    private Float testsPerOneMillion;
    @SerializedName("population")
    @Expose
    private Long population;
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
    private Float criticalPerOneMillion;
    @SerializedName("affectedCountries")
    @Expose
    private Integer affectedCountries;

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
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

    public Float getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(Float deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public Long getTests() {
        return tests;
    }

    public void setTests(Long tests) {
        this.tests = tests;
    }

    public Float getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(Float testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
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

    public Float getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(Float criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public Integer getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(Integer affectedCountries) {
        this.affectedCountries = affectedCountries;
    }

}