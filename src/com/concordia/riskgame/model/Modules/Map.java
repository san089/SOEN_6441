/*
 *
 */
package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Map.
 */
public class Map {
    private String name;
    private String authorName;
    private String path;
    private boolean errorOccurred;
    private String errorMessage;
    private List<Continent> continents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isErrorOccurred() {
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

    public List<Continent> getContinents() {
        return continents;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }


    public void removeContinent(Continent continent) {
        getContinents().remove(continent);
    }


    public void addContinent(Continent continent) {
        if (searchContinent(continent.getContinentName()) == "") {
            getContinents().add(continent);
        }
    }

    public List<String> listOfCountryNames() {
        List<String> countryNames = new ArrayList<String>();
        for (Continent continent : getContinents()) {
            for (Country country : continent.getCountriesPresent()) {
                countryNames.add(country.getCountryName());
            }
        }
        return countryNames;
    }


    public String searchContinent(String continentName) {
        for (Continent name : getContinents()) {
            if (name.getContinentName().equalsIgnoreCase(continentName)) {
                return name.getContinentName();
            }
        }
        return "";
    }

    public List<String> listOfContinentNames() {
        List<String> continentNames = new ArrayList<String>();
        for (Continent continent : getContinents()) {
            continentNames.add(continent.getContinentName());
        }
        return continentNames;
    }
}
