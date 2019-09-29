/*
 * 
 */
package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;

/**
 * The Class Map.
 */
public class Map {
	
	private ArrayList<Continent> continents;
	
	
	public ArrayList<Country> getCountryList(String Continent) {
		return null;
	}
	
	
	public ArrayList<Continent> getContinents() {
		return null;	
	}

	
	public ArrayList<Continent> ownedContinents(String playerName) {
		ArrayList<Continent> ownedContinents=new ArrayList<Continent>();
		for(Continent continent:continents)
		{
			boolean isOwned=true;
			for(Country country:continent.getContainingCountries().values())
				if(!country.getOwnerName().equalsIgnoreCase(playerName))
					{isOwned=false;
					break;
					}
			if(isOwned)
				ownedContinents.add(continent);
				
		}
		return ownedContinents;
				
	}
	
	
}
