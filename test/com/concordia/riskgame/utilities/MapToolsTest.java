package com.concordia.riskgame.utilities;

import com.concordia.riskgame.model.Modules.Map;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Test Class to test Map Tools class
 */
public class MapToolsTest {

    @Test
    public void pickMapFileService() {
        String currentPath = System.getProperty("user.dir");
        String pathSuffixValidMaps = currentPath + "\\Maps\\Valid_Maps\\";
        String pathSuffixInvalidMaps = currentPath + "\\Maps\\Invalid_Maps\\";

        //=============================INVALID MAPS=============================================
        String countryNotConnectedInItsContinent = pathSuffixInvalidMaps + "CountryNotConnectedInItsContinent.map";
        String informationMissing = pathSuffixInvalidMaps + "InformationMissing.map";
        String mapWithNoCountryTest = pathSuffixInvalidMaps + "MapWithNoCountryTest.map";
        String twoContinentNotConnected = pathSuffixInvalidMaps + "TwoContinentNotConnected.map";

        //=============================VALID MAPS=============================================
        String bigValidMap = pathSuffixValidMaps + "BigValidMap.map";
        String smallValidMap = pathSuffixValidMaps + "SmallValidMap.map";


        MapTools mp = new MapTools();

        printLine();
        System.out.println("Test Case 1. Country is a neighbour of country in other continent but not a neighbour in its own continent");
        assertFalse(mp.pickMapFileService(new Map(), countryNotConnectedInItsContinent));
        printLine();
        System.out.println("Test Case 2. A missing tag/annotation in map file.");
        assertFalse(mp.pickMapFileService(new Map(), informationMissing));
        printLine();
        System.out.println("Test Case 3. A map that has a continent with no country");
        assertFalse(mp.pickMapFileService(new Map(), mapWithNoCountryTest));
        printLine();
        System.out.println("Test Case 4. A map having two continent but the continents are not connected.");
        assertFalse(mp.pickMapFileService(new Map(), twoContinentNotConnected));

        printLine();
        System.out.println("Test Case 5. A Small valid map.");
        assertTrue(mp.pickMapFileService(new Map(), smallValidMap));
        printLine();
        System.out.println("Test Case 5. A Big valid map.");
        assertTrue(mp.pickMapFileService(new Map(), bigValidMap));

    }

    public void printLine(){
        System.out.println("========================================================================================");
    }
}