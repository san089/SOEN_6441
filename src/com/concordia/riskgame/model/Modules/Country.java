package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
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


    public String getCountryName() {
        return countryName;
    }


    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public Player getOwnedBy() {
        return ownedBy;
    }


    public void setOwnedBy(Player ownedBy) {
        this.ownedBy = ownedBy;
    }


    public int getNoOfArmiesPresent() {
        return this.noOfArmiesPresent;
    }


    public void setNoOfArmiesPresent(int noOfArmiesPresent) {
        this.noOfArmiesPresent = noOfArmiesPresent;
    }


    public List<String> getListOfNeighbours() {
        return listOfNeighbours;
    }


    public void setListOfNeighbours(List<String> listOfNeighbours) {
        this.listOfNeighbours = listOfNeighbours;
    }


    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(double longitude) {
        longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }


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

	/*
	 * <<<<<<< HEAD ======= private final String countryName; private final String
	 * continentName; private String coordinateX; private String coordinateY;
	 * private String ownerName; private ArrayList<Country> adjacentCountries;
	 * private int armyCount;
	 */
	
	

	
}
