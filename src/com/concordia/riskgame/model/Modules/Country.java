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

}
