/**
 *
 * Test Class for Country.
 *
 * @author Sanchit
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */


package com.concordia.riskgame.model.Modules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A test class to test Country Module.
 */
public class CountryTest {
    Country country;

    @Before
    public void setup(){
        Continent continent = new Continent();
        continent.setContinentName("ASIA");
        continent.setControlValue(7);
        country = new Country("India", continent);
        List<Country> countriesPresent = new ArrayList<>();
        countriesPresent.add(country);
        continent.setCountriesPresent(countriesPresent);
    }

    @Test
    public void getCountryName() {
        String countryName = "India";
        assertEquals(country.getCountryName().getClass(), countryName.getClass());
    }

    @Test
    public void setCountryName() {
        String countryName = "China";
        country.setCountryName(countryName);
        assertEquals(country.getCountryName(), countryName);
    }

    @Test
    public void getOwnedBy() {
        assertNull(country.getOwnedBy());
    }

    @Test
    public void setOwnedBy() {
        Player p = new Player(0, "Sanchit");
        country.setOwnedBy(p);
        assertEquals(country.getOwnedBy().getPlayerName(), "Sanchit");
    }

    @Test
    public void getNoOfArmiesPresent() {
        assertEquals(country.getNoOfArmiesPresent(), 0);
    }

    @Test
    public void setNoOfArmiesPresent() {
        country.setNoOfArmiesPresent(10);
        assertEquals(country.getNoOfArmiesPresent(), 10);
    }

    @Test
    public void getListOfNeighbours() {
        country.setListOfNeighbours(new ArrayList<String>(Arrays.asList("India")));
        assertEquals(country.getListOfNeighbours(), new ArrayList<String>(Arrays.asList("India")));
    }

    @Test
    public void setListOfNeighbours() {
        country.setListOfNeighbours(new ArrayList<String>(Arrays.asList("India")));
        assertEquals(country.getListOfNeighbours(), new ArrayList<String>(Arrays.asList("India")));
    }

    @Test
    public void getLongitude() {
        assertEquals(country.getLongitude(), 0.0, 0.0);
    }

    @Test
    public void setLongitude() {
        country.setLongitude(1.1);
        assertEquals(country.getLongitude(), 1.1, 0.0);
    }

    @Test
    public void getLatitude() {
        assertEquals(country.getLatitude(), 0.0, 0.0);
    }

    @Test
    public void setLatitude() {
        country.setLongitude(1.1);
        assertEquals(country.getLongitude(), 1.1, 0.0);
    }

    @Test
    public void addNoOfArmiesCountry() {
        country.addNoOfArmiesCountry();
        assertEquals(country.getNoOfArmiesPresent(), 1);
    }

    @Test
    public void removeNoOfArmiesCountry() {
        country.addNoOfArmiesCountry();
        country.removeNoOfArmiesCountry();
        assertEquals(country.getNoOfArmiesPresent(), 0);
    }
}