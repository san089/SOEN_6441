/*
 * 
 */
package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
public class Map {
    private String name;
    private String authorName;
    private String path;
    private boolean errorOccurred;
    private String errorMessage;
    private List<Continent> continents;



    /**
     * Instantiates a new map.
     */
    public Map() {
        continents = new ArrayList<Continent>();
        path = "";
        name = "";
        errorOccurred = false;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<Continent> getContinents() {
        return continents;
    }


    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }


    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }


    public boolean getErrorOccurred() {
        return errorOccurred;
    }


    public void setErrorOccurred(boolean errorOccurred) {
        this.errorOccurred = errorOccurred;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    /**
     * Search continent.
     *
     * @param continentName the continent name
     * @return the string
     */
    public String searchContinent(String continentName) {
        for (Continent name : getContinents()) {
            if (name.getContinentName().equalsIgnoreCase(continentName)) {
                return name.getContinentName();
            }
        }
        return "";
    }


    /**
     * Removes the continent.
     *
     * @param continent the continent
     */
    public void removeContinent(Continent continent) {
        getContinents().remove(continent);
    }


    /**
     * Adds the continent.
     *
     * @param continent the continent
     */
    public void addContinent(Continent continent) {
        if (searchContinent(continent.getContinentName()) == "") {
            getContinents().add(continent);
        }
    }


    /**
     * List of country names.
     *
     * @return the list
     */
    public List<String> listOfCountryNames() {
        List<String> countryNames = new ArrayList<String>();
        for (Continent continent : getContinents()) {
            for (Country country : continent.getCountriesPresent()) {
                countryNames.add(country.getCountryName());
            }
        }
        return countryNames;
    }


    /**
     * List of continent names.
     *
     * @return the list
     */
    public List<String> listOfContinentNames() {
        List<String> continentNames = new ArrayList<String>();
        for (Continent continent : getContinents()) {
            continentNames.add(continent.getContinentName());
        }
        return continentNames;
    }

    /**
     * Search country.
     *
     * @param countryName the country name
     * @param continentName the continent name
     * @return the string
     */
    public String searchCountry(String countryName, String continentName) {
        for (Continent name : getContinents()) {
            if (name.getContinentName().equalsIgnoreCase(continentName)) {
                for (Country cName : name.getCountriesPresent()) {
                    if (cName.getCountryName().equalsIgnoreCase(countryName)) {
                        return countryName;
                    }
                }
            }
        }
        return "";
    }


    /**
     * Search country.
     *
     * @param countryName the country name
     * @return the country
     */
    public Country searchCountry(String countryName) {
        for (Continent name : getContinents()) {
            for (Country cName : name.getCountriesPresent()) {
                if (cName.getCountryName().equalsIgnoreCase(countryName)) {
                    return cName;
                }
            }
        }
        return null;
    }

    /**
     * Search continent.
     *
     * @param c the c
     * @return the continent
     */
    public Continent searchContinent(Country c) {
        for (Continent cont : getContinents()) {
            if (cont.getCountriesPresent().contains(c)) {
                return cont;
            }
        }
        return null;
    }




	/**
	 * Return the continents owned by a particular player.
	 *
	 * @param playerName the player name
	 * @return the list of continents owned by the player
	 */
	public ArrayList<Continent> getOwnedContinents(String playerName) {
		ArrayList<Continent> ownedContinents=new ArrayList<Continent>();
		for(Continent continent:continents)
		{
			boolean isOwned=true;
			for(Country country:continent.getCountriesPresent()) {
				if(!country.getOwnedBy().getPlayerName().equals(playerName))
					{isOwned=false;
					break;
					}
			}
			if(isOwned)
				ownedContinents.add(continent);
				
		}
		return ownedContinents;
				
	}
	
	/**
	 * Return the continents owned by a particular player.
	 *
	 * @param playerName the player name
	 * @return the list of continents owned by the player
	 */
	public ArrayList<Country> getOwnedCountries(String playerName) {
		ArrayList<Country> ownedCountries=new ArrayList<Country>();
		for(Continent continent:continents)
		{
			for(Country country:continent.getCountriesPresent()) {
				if(country.getOwnedBy().getPlayerName().equals(playerName))
					{
					ownedCountries.add(country);
					}
			}
				
		}
		return ownedCountries;
				
	}
	
	
	
}
