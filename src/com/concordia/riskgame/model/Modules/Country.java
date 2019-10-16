package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;


/**
 * Instantiates a new country.
 *
 * @param countryName the country name
 * @param continentName the continent name
 * @param coordinateX the coordinate X
 * @param coordinateY the coordinate Y
 * @param ownerIndex the owner index
 * @param adjacentCountries the adjacent countries
 * @param armyCount the army count
 */

public class Country {
    private String countryName;
    private Player ownedBy;
    private int noOfArmiesPresent;
    private List<String> listOfNeighbours;
    private double longitude;
    private double latitude;
    
    /**
     * Instantiates a new country.
     */
    public Country() {
        listOfNeighbours = new ArrayList<String>();
    }

    /**
     * Instantiates a new country.
     *
     * @param countryName the country name
     * @param continent the continent
     */
    public Country(String countryName, Continent continent) {
        this.setCountryName(countryName);
        this.listOfNeighbours = new ArrayList<String>();
        this.noOfArmiesPresent=0;
        
    }


    /**
     * Getter for countryName
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }


    /**
     * Setter for countryName attribute.
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Getter for owner name
     * @return ownedBy
     */
    public Player getOwnedBy() {
        return ownedBy;
    }

    /**
     * Setter for ownedBy attribute.
     * @param ownedBy
     */
    public void setOwnedBy(Player ownedBy) {
        this.ownedBy = ownedBy;
    }

    /**
     * Getter for number of armies in the country
     * @return noOfArmiesPresent
     */
    public int getNoOfArmiesPresent() {
        return this.noOfArmiesPresent;
    }

    /**
     * Setter for setting armies into country.
     * @param noOfArmiesPresent
     */
    public void setNoOfArmiesPresent(int noOfArmiesPresent) {
        this.noOfArmiesPresent = noOfArmiesPresent;
    }

    /**
     * Getter for list of neighbours for the country
     * @return listOfNeighbours
     */
    public List<String> getListOfNeighbours() {
        return listOfNeighbours;
    }

    /**
     * Setter for setting neighbours attribute.
     * @param listOfNeighbours
     */
    public void setListOfNeighbours(List<String> listOfNeighbours) {
        this.listOfNeighbours = listOfNeighbours;
    }

    /**
     * Getter for longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Setter for longitude attribute.
     * @param longitude
     */
    public void setLongitude(double longitude) {
        longitude = longitude;
    }

    /**
     * Getter for latitude
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter for latitude attribute.
     * @param latitude
     */
    public void setLatitude(double latitude) {
        latitude = latitude;
    }


    /**
     * Adds the no of armies country.
     */
    public void addNoOfArmiesCountry() {
        this.noOfArmiesPresent++;
    }

    /**
     * Removes the no of armies country.
     */
    public void removeNoOfArmiesCountry() {
        this.noOfArmiesPresent--;
    }

}
