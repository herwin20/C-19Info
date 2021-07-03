package com.herwinlab.c19info.model;

import java.io.Serializable;

public class RecycleData {

    private int cases;
    private int recovered;
    private int deaths;
    private String country;
    private int population;
    private long updated;
    private float activePerOneMillion;
    private int todayCases;
    private int todayDeaths;
    private int todayRecovered;
    private int tests;

    //Class Objcet inside JsonObject
    private CountryInfo countryInfo;

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getCountry() {
        return country;
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public float getactivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setactivePerOneMillion(float activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public int getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(int todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int test) {
        this.tests = tests;
    }

    public RecycleData(int cases, int recovered, int deaths, String country, int population, long updated, float activePerOneMillion,
                       int todayCases, int todayRecovered, int todayDeaths, int tests, CountryInfo countryInfo) {
        this.cases = cases;
        this.recovered = recovered;
        this.deaths = deaths;
        this.country = country;
        this.population = population;
        this.updated = updated;
        this.activePerOneMillion = activePerOneMillion;
        this.todayCases = todayCases;
        this.todayRecovered = todayRecovered;
        this.todayDeaths = todayDeaths;
        this.tests = tests;
        this.countryInfo = countryInfo;
    }
}
