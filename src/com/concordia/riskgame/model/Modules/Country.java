/**
 *
 * This Module holds the data & methods related to a Country.
 *
 * @author Shubham, Sucheta
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.model.Modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * Instantiates a new country.
 *
 */

public class Country extends Observable implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     * @param countryName Country Name
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
     * @param ownedBy Owned By
     */
    public void setOwnedBy(Player ownedBy) {
        this.ownedBy = ownedBy;
        Gameplay.getInstance().triggerObserver("domination");
        Gameplay.getInstance().triggerObserver("showmap");
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
     * @param noOfArmiesPresent number of armies
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
     * @param listOfNeighbours list of neighbours
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
     * @param longitude longitude value
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
     * @param latitude latitude value
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    /**
     * Adds the no of armies country.
     */
    public void addNoOfArmiesCountry() {
        ++this.noOfArmiesPresent;
    }

    /**
     * Removes the no of armies country.
     */
    public void removeNoOfArmiesCountry() {
        this.noOfArmiesPresent--;
    }

}
