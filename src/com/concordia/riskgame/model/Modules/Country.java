package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private String countryName;
    private Player ownedBy;
    private int noOfArmiesPresent;
    private List<String> listOfNeighbours;
    private double longitude;
    private double latitude;
    private boolean isVisited;



    public Country() {
        listOfNeighbours = new ArrayList<String>();
    }

    public Country(String countryName, Continent continent) {
        this.setCountryName(countryName);
        this.listOfNeighbours = new ArrayList<String>();
        isVisited = false;

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


    public void addNoOfArmiesCountry() {
        this.noOfArmiesPresent++;
    }

    public void removeNoOfArmiesCountry() {
        this.noOfArmiesPresent--;
    }

<<<<<<< HEAD
=======
	private final String countryName;
	private final String continentName;
    private String coordinateX;
    private String coordinateY;
	private String ownerName;
	private ArrayList<Country> adjacentCountries;
	private int armyCount;
	
	
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
	public Country(String countryName, String continentName, String coordinateX, String coordinateY, String ownerIndex,
			ArrayList<Country> adjacentCountries, int armyCount) {
		this.countryName = countryName;
		this.continentName = continentName;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.armyCount = 0;
	}

	public ArrayList<Country> getAdjacentCountries() {
		return adjacentCountries;
	}


	public int getArmyCount() {
		return armyCount;
	}


	public void setArmyCount(int armyCount) {
		this.armyCount = armyCount;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	
>>>>>>> SOEN6441_sucheta
}
