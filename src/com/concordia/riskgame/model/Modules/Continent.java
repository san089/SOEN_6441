/*
 * 
 */
package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
public class Continent {

    private String continentName;
    private List<Country> countriesPresent;
    private int controlValue;


    /**
     * Instantiates a new continent.
     */
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

    /**
     * Adds the country.
     *
     * @param name the name
     */
    public void addCountry(Country name) {
        countriesPresent.add(name);
    }

    /**
     * Removes the country.
     *
     * @param name the name
     */
    public void removeCountry(Country name) {
        countriesPresent.remove(name);
    }
    
    
    /**
     * Search country.
     *
     * @param countryName the country name
     * @return the country
     */
    public Country searchCountry(String countryName) {
    	for(Country country:getCountriesPresent())
    		if(country.getCountryName().contentEquals(countryName))
    			return country;
    	return null;
    }
}
