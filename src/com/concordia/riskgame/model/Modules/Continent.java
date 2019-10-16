package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  This class holds continent related data
 */
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


    /**
     * Getter for continent name
     * @return continentName
     */
    public String getContinentName() {
        return continentName;
    }


    /**
     * Setter method for continent name
     * @param continentName
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }


    /**
     * Getter method for the list of countries in the contient
     * @return countriesPresent (list of type Country)
     */
    public List<Country> getCountriesPresent() {
        return countriesPresent;
    }

    /**
     * Setter method for the list of countries in the contient
     * @return countriesPresent (list of type Country)
     */
    public void setCountriesPresent(List<Country> countriesPresent) {
        this.countriesPresent = countriesPresent;
    }

    /**
     * Getter method for the control value of continent
     * @return controlValue
     */
    public int getControlValue() {
        return controlValue;
    }

    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }

    /**
     * Adds the country to the list of countries in the continent.
     *
     * @param name the name of country to add
     */
    public void addCountry(Country name) {
        countriesPresent.add(name);
    }

    /**
     * Removes the country from list of countries in the continent.
     *
     * @param name the name of country to remove
     */
    public void removeCountry(Country name) {
        countriesPresent.remove(name);
    }
    
    
    /**
     * Search country in the list of conuntries.
     *
     * @param countryName the country name to be searched
     * @return the country object, null if country not found.
     */
    public Country searchCountry(String countryName) {
    	for(Country country:getCountriesPresent())
    		if(country.getCountryName().contentEquals(countryName))
    			return country;
    	return null;
    }
}
