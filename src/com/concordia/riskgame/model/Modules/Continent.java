package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;


public class Continent {

    private String continentName;
    private List<Country> countriesPresent;
    private int controlValue;


    public Continent() {
        controlValue = 0;
        countriesPresent = new ArrayList<Country>();
    }


    public String getContinentName() {
        return continentName;
    }


    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }


    public List<Country> getCountriesPresent() {
        return countriesPresent;
    }


    public void setCountriesPresent(List<Country> countriesPresent) {
        this.countriesPresent = countriesPresent;
    }

    public int getControlValue() {
        return controlValue;
    }

    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }

    public void addCountry(Country name) {
        countriesPresent.add(name);
    }

    public void removeCountry(Country name) {
        countriesPresent.remove(name);
    }
}
