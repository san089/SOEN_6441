package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.controller.CommandController;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Test class for testing GamePlayer class
 */
public class GameplayTest {
    Gameplay gamePlay;
    PrintStream console = null;
    ByteArrayOutputStream bytes = null;
    Scanner sc = new Scanner(System.in);

    @BeforeClass
    public static void classSetup() throws Exception{
        Scanner sc = new Scanner(System.in);
        CommandController.parseCommand("loadmap D:\\SOEN_6441\\Maps\\Valid_Maps\\SmallValidMap.map", sc);
        CommandController.parseCommand("gameplayer -add Sanchit -add Sucheta", sc);
    }


    @Before
    public void setUp() throws Exception {
        gamePlay = Gameplay.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void validateStartupInputs() {
       String expected =  "Please select a valid map for current game.";
        Map map = new Map();
        assertEquals(expected, gamePlay.validateStartupInputs());
        gamePlay.setSelectedMap(map);
        assertNotEquals(expected, gamePlay.validateStartupInputs());

    }

    @Test
    public void roundRobinPlayer() {
    }

    @Test
    public void assignReinforcementArmies() {
        CommandController.parseCommand("populatecountries", sc);
        CommandController.parseCommand("showphases", sc);
        CommandController.parseCommand("placeall", sc);
        int value  = gamePlay.getCurrentPlayer().getArmyCount();
        assertEquals(3, value);
    }

    /**
     * Method to test if player is added into the system.
     */
    @Test
    public void addPlayer() {
        String playerRemoveCommand = "gameplayer -add Haifeng";
        CommandController.parseCommand(playerRemoveCommand, (new Scanner(System.in)));
        List<String> playerNames = new ArrayList<String>();

        for(Player P : gamePlay.getPlayers()){
            playerNames.add(P.getPlayerName());
        }
        assertTrue(playerNames.contains("Haifeng"));
    }

    /**
     * Test method to test that duplicate players should not be added into the system.
     */
    @Test
    public void existDuplicatePlayer() {
        String playerRemoveCommand = "gameplayer -add Sanchit";
        int numPlayersBefore = gamePlay.getPlayerCount();
        CommandController.parseCommand(playerRemoveCommand, (new Scanner(System.in)));
        int numPlayersAfter = gamePlay.getPlayerCount();
        assertEquals(numPlayersAfter, numPlayersBefore);
    }

    /**
     * Method to test if the player is remove player command works as expected.
     */
    @Test
    public void removePlayer() {
        String playerRemoveCommand = "gameplayer -remove Sanchit";
        CommandController.parseCommand(playerRemoveCommand, (new Scanner(System.in)));
        for(Player P : gamePlay.getPlayers()){
            assertFalse(P.getPlayerName().equalsIgnoreCase("Sanchit"));
        }
    }

    @Test
    public void initialisePlayers() {
    }

    @Test
    public void assignStartupArmies() {
    }

    /**
     * Method to test if the reinforce functionality works as expected.
     */
    @Test
    public void placeArmy() {
        CommandController.parseCommand("populatecountries", sc);
        CommandController.parseCommand("showphases", sc);
        CommandController.parseCommand("placeall", sc);
        int armyAvailable = gamePlay.getCurrentPlayer().getArmyCount();
        ArrayList<Country> ownedCountries = gamePlay.getSelectedMap().getOwnedCountries(gamePlay.getCurrentPlayer().getPlayerName());
        String countryName = ownedCountries.get(0).getCountryName(); //Get the first country from list to place army in that country
        String command = "reinforce " + countryName + " " + armyAvailable;

        //Checking army count before and after reinforce command run
        int armyBeforeReinforce = ownedCountries.get(0).getNoOfArmiesPresent();
        CommandController.parseCommand(command, sc);
        int armyAfterReinforce = ownedCountries.get(0).getNoOfArmiesPresent();
        assertEquals(armyAfterReinforce, armyBeforeReinforce + 3);
    }

    @Test
    public void getAbandonedCountryCount() {
    }

    @Test
    public void displayArmyDistribution() {
    }

    @Test
    public void placeAllArmies() {
    }
}