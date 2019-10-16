package com.concordia.riskgame.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.concordia.riskgame.controller.StartupPhaseController;
import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.MapTools;
import com.concordia.riskgame.view.StartUpPhaseView;

// TODO: Auto-generated Javadoc
public class StartUpPhaseTests {

	private int numberofPlayers;
	private Queue<Player> playerQueue;
	private List<String> countries;
	private ArrayList<Player> players;
	private Player currentPlayer;
	private int floorCountryPerPlayer,ceilingCountryperPlayer;
	private Gameplay gameplay;
	private Map gameMap;
	private MapTools maptools;
	
	/**
	 * Initialise inputs.
	 */
	@Before
	public void initialiseInputs() {
		gameplay=Gameplay.getInstance();
		gameMap=new Map();
		maptools=new MapTools();
		countries=new ArrayList<String>();
		players=new ArrayList<Player>();
		playerQueue=new LinkedList<Player>();
		numberofPlayers=5;
		for(int i=0;i<numberofPlayers;i++) {
			players.add(new Player(i+1,"Player"+(i+1)));
		}
		maptools.pickMapFileService(gameMap, "D:\\SourceTree\\Maps\\Valid_Maps\\AtlanticCity.map");
		gameplay.setSelectedMap(gameMap);
		countries=gameplay.getSelectedMap().listOfCountryNames();
		gameplay.setPlayers(players);
		floorCountryPerPlayer=Math.floorDiv(countries.size(),numberofPlayers);
		ceilingCountryperPlayer=(int) Math.ceil((double)(countries.size()/(double)numberofPlayers));
		}
	
	
	/**
	 * Initialise players test.
	 */
	@Test
	public void initialisePlayersTest() {
		System.out.println("========================INITIALISE PLAYERS TEST CASE START==============================");
		gameplay.initialisePlayers();
		playerQueue=gameplay.getPlayerQueue();
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
		gameplay.assignStartupArmies();
		initialPlayerArmyCount=(gameplay.getPlayers().get(0).getArmyCount())*(gameplay.getPlayers().size());
		System.out.println(initialPlayerArmyCount);
		gameplay.initialisePlayers();
		gameplay.placeAllArmies();
		for(Player player:gameplay.getPlayers())
			finalPlayerArmyCount+=player.getArmyCount();
		for(Continent continent:gameplay.getSelectedMap().getContinents())
			for(Country country:continent.getCountriesPresent())
				countryArmyCount+=country.getNoOfArmiesPresent();
		Assert.assertTrue(countryArmyCount==initialPlayerArmyCount);
		Assert.assertTrue(finalPlayerArmyCount==0);	
		for(Continent continent:gameplay.getSelectedMap().getContinents())
			for(Country country:continent.getCountriesPresent())
				Assert.assertTrue(country.getNoOfArmiesPresent()>0);	
		System.out.println("========================ASSIGN ARMIES PLAYERS TEST CASE END==============================");
		
	}
	
	
	
	/**
	 * Reset data.
	 */
	@After
	public void resetData()
	{
		
	}
	
	
	
}
