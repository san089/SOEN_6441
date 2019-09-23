package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;

public class Country {

	private final String countryName;
	private final String continentName;
    private String coordinateX;
    private String coordinateY;
	private String ownerIndex;
	private ArrayList<Country> adjacentCountries;
	private int armyCount;
	
	
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

	
}
