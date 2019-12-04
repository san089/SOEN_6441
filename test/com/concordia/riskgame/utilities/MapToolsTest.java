/**
 *
 * Test Class for Maps.
 *
 * @author Sanchit
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */


package com.concordia.riskgame.utilities;

import com.concordia.riskgame.model.Modules.Map;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Test Class to test Map Tools class
 */
public class MapToolsTest {

    public String currentPath;
    public String pathSuffixValidMaps;
    public String pathSuffixInvalidMaps;
    public String countryNotConnectedInItsContinent;
    public String informationMissing;
    public String mapWithNoCountryTest;
    public String twoContinentNotConnected;
    public String invalidMapFile;
    public String smallValidMap;

    @Before
    public void setup(){
        currentPath = System.getProperty("user.dir");
        pathSuffixValidMaps = currentPath + "\\Maps\\Valid_Maps\\";
        pathSuffixInvalidMaps = currentPath + "\\Maps\\Invalid_Maps\\";
        countryNotConnectedInItsContinent = pathSuffixInvalidMaps + "CountryNotConnectedInItsContinent.map";
        informationMissing = pathSuffixInvalidMaps + "InformationMissing.map";
        mapWithNoCountryTest = pathSuffixInvalidMaps + "MapWithNoCountryTest.map";
        twoContinentNotConnected = pathSuffixInvalidMaps + "TwoContinentNotConnected.map";
        invalidMapFile = pathSuffixInvalidMaps + "InvalidMapFile.maap";
        smallValidMap = pathSuffixValidMaps + "SmallValidMap.map";

    }
    

    @Test
    public void countryNotConnextedInContinent() {
        DominationMapTools mp = new DominationMapTools();
        printLine();
        System.out.println("Test Case 1. Country is a neighbour of country in other continent but not a neighbour in its own continent");
        assertFalse(mp.pickMapFileService(new Map(), countryNotConnectedInItsContinent));
    }


    @Test
    public void informationMissing(){
        DominationMapTools mp = new DominationMapTools();
        printLine();
        System.out.println("Test Case 2. A missing tag/annotation in map file.");
        assertFalse(mp.pickMapFileService(new Map(), informationMissing));
    }


    @Test
    public void continentWithNoCountry(){
        DominationMapTools mp = new DominationMapTools();
        printLine();
        System.out.println("Test Case 3. A map that has a continent with no country");
        assertFalse(mp.pickMapFileService(new Map(), mapWithNoCountryTest));
    }

    @Test
    public void continentNotConnected(){
        DominationMapTools mp = new DominationMapTools();
        printLine();
        System.out.println("Test Case 4. A map having two continent but the continents are not connected.");
        assertFalse(mp.pickMapFileService(new Map(), twoContinentNotConnected));
    }

    @Test
    public void invalidMapFile(){
        DominationMapTools mp = new DominationMapTools();
        printLine();
        System.out.println("Test Case 5. Map File Invalid.");
        assertFalse(mp.pickMapFileService(new Map(), invalidMapFile));
    }

    @Test
    public void validMapTest(){
        DominationMapTools mp = new DominationMapTools();
        printLine();
        System.out.println("Test Case 6. A Small valid map.");
        assertTrue(mp.pickMapFileService(new Map(), smallValidMap));
    }


    public void printLine(){
        System.out.println("========================================================================================");
    }
}
