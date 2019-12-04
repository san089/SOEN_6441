/**
 *
 * Test Class for Gameplay.
 *
 * @author Sanchit, Sucheta
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */


package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.utilities.DominationMapTools;
import com.concordia.riskgame.utilities.Phases;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Test class for testing GamePlayer class
 */
public class GameplayTest {
    private Gameplay gamePlay;
    private PrintStream console = null;
    private ByteArrayOutputStream bytes = null;
    private int numberofPlayers;
    private Queue<Player> playerQueue;
    private List<String> countries;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int floorCountryPerPlayer,ceilingCountryperPlayer;
    private Map gameMap;
    private DominationMapTools maptools;
    Scanner sc = new Scanner(System.in);

    @BeforeClass
    public static void classSetup() throws Exception{
        Scanner sc = new Scanner(System.in);
        String userDir=System.getProperty("user.dir");
        CommandController.parseCommand("loadmap "+userDir+"\\Maps\\Valid_Maps\\SmallValidMap.map");
        CommandController.parseCommand("gameplayer -add Sanchit -add Sucheta");
        
    }


    @Before
    public void setUp() throws Exception {
        gamePlay = Gameplay.getInstance();
        gameMap=new Map();
        maptools=new DominationMapTools();
        countries=new ArrayList<String>();
        players=new ArrayList<Player>();
        playerQueue=new LinkedList<Player>();
        numberofPlayers=5;
        for(int i=0;i<numberofPlayers;i++) {
            players.add(new Player(i+1,"Player"+(i+1)));
        }
        gamePlay.setPlayers(players);
        maptools.pickMapFileService(gameMap, System.getProperty("user.dir") + "\\Maps\\Valid_Maps\\AtlanticCity.map");
        gamePlay.setSelectedMap(gameMap);
        countries=gamePlay.getSelectedMap().listOfCountryNames();
        gamePlay.setPlayers(players);
        floorCountryPerPlayer=Math.floorDiv(countries.size(),numberofPlayers);
        ceilingCountryperPlayer=(int) Math.ceil((double)(countries.size()/(double)numberofPlayers));


    
    }

    @After
    public void tearDown() throws Exception {
    }


    
    @Test
    public void validateStartupInputs() {
    	gamePlay.setSelectedMap(null);
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
    	try {
        CommandController.parseCommand("populatecountries");
        CommandController.parseCommand("showphases");
        CommandController.parseCommand("placeall");
        gamePlay.assignReinforcementArmies();
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Exception while parsing commands");
    	}
        int value  = gamePlay.getCurrentPlayer().getArmyCount();
        assertTrue(3<=value);
    }

    /**
     * Method to test if player is added into the system.
     */
    @Test
    public void addPlayer() {
    	
    	gamePlay.setCurrentPhase(Phases.Startup);
        String playerRemoveCommand = "gameplayer -add Haifeng";
        try {
        CommandController.parseCommand(playerRemoveCommand);
        }
        catch(Exception ex)
    	{
    		System.out.println("Exception while parsing commands");
    	}
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
        try {
        CommandController.parseCommand(playerRemoveCommand);
        }
        catch(Exception ex)
    	{
    		System.out.println("Exception while parsing commands");
    	}
        int numPlayersAfter = gamePlay.getPlayerCount();
        assertEquals(numPlayersAfter, numPlayersBefore);
    }

    /**
     * Method to test if the player is remove player command works as expected.
     */
    @Test
    public void removePlayer() {
        String playerRemoveCommand = "gameplayer -remove Sanchit";
        try {
        CommandController.parseCommand(playerRemoveCommand);
        }catch(Exception ex)
    	{
    		System.out.println("Exception while parsing commands");
    	}
        for(Player P : gamePlay.getPlayers()){
            assertFalse(P.getPlayerName().equalsIgnoreCase("Sanchit"));
        }
    }

    /**
     * Initialise players test.
     */
    @Test
    public void initialisePlayersTest() {
        System.out.println("========================INITIALISE PLAYERS TEST CASE START==============================");
        gamePlay.initialisePlayers();
        playerQueue=gamePlay.getPlayerQueue();
        while(!playerQueue.isEmpty()) {
            currentPlayer=playerQueue.remove();
            Assert.assertTrue(currentPlayer.getCountriesOwned().size()==floorCountryPerPlayer||currentPlayer.getCountriesOwned().size()==ceilingCountryperPlayer);
        }
        System.out.println("========================INITIALISE PLAYERS TEST CASE END==============================");

    }

    /**
     * Assign armies test.
     */
    @Test
    public void assignArmiesTest() {
        System.out.println("========================ASSIGN ARMIES TEST CASE START==============================");

        int initialPlayerArmyCount,countryArmyCount = 0,finalPlayerArmyCount = 0;
        gamePlay.assignStartupArmies();
        initialPlayerArmyCount=(gamePlay.getPlayers().get(0).getArmyCount())*(gamePlay.getPlayers().size());
        System.out.println(initialPlayerArmyCount);
        gamePlay.initialisePlayers();
        gamePlay.placeAllArmies();
        for(Player player:gamePlay.getPlayers())
            finalPlayerArmyCount+=player.getArmyCount();
        for(Continent continent:gamePlay.getSelectedMap().getContinents())
            for(Country country:continent.getCountriesPresent()) {
                countryArmyCount+=country.getNoOfArmiesPresent();
                Assert.assertTrue(country.getNoOfArmiesPresent()>0);
            }
        Assert.assertTrue(countryArmyCount==initialPlayerArmyCount);
        Assert.assertTrue(finalPlayerArmyCount==0);
        for(Continent continent:gamePlay.getSelectedMap().getContinents())
            for(Country country:continent.getCountriesPresent())
                Assert.assertTrue(country.getNoOfArmiesPresent()>0);
        System.out.println("========================ASSIGN ARMIES PLAYERS TEST CASE END==============================");

    }



    /**
     * Method to test if the reinforce functionality works as expected.
     */
    @Test
    public void placeArmy() {
    	gamePlay.setCurrentPhase(Phases.Startup);
    	try {
        CommandController.parseCommand("populatecountries");
        CommandController.parseCommand("showphases");
        CommandController.parseCommand("placeall");
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Exception while parsing commands");
    	}
    	gamePlay.assignReinforcementArmies();
        int armyAvailable = gamePlay.getCurrentPlayer().getArmyCount();
        ArrayList<Country> ownedCountries = gamePlay.getSelectedMap().getOwnedCountries(gamePlay.getCurrentPlayer().getPlayerName());
        String countryName = ownedCountries.get(0).getCountryName(); //Get the first country from list to place army in that country
        String command = "reinforce " + countryName + " " + armyAvailable;

        //Checking army count before and after reinforce command run
        int armyBeforeReinforce = ownedCountries.get(0).getNoOfArmiesPresent();
        try {
        CommandController.parseCommand(command);
        }
        catch(Exception ex)
    	{
    		System.out.println("Exception while parsing commands");
    	}
        int armyAfterReinforce = ownedCountries.get(0).getNoOfArmiesPresent();
        assertEquals(armyAfterReinforce, armyBeforeReinforce + armyAvailable);
    }

    @Test
    public void getAbandonedCountryCount() {
    }

    

    @Test
    public void placeAllArmies() {
    }
}