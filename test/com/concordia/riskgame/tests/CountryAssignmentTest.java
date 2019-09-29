package com.concordia.riskgame.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.concordia.riskgame.controller.StartupPhaseController;
import com.concordia.riskgame.model.Modules.Player;

// TODO: Auto-generated Javadoc
public class CountryAssignmentTest {

	private StartupPhaseController sController;
	private int numberofPlayers,numberofCountries;
	private Queue<Player> playerQueue;
	private ArrayList<String> countries;
	private ArrayList<Player> players;
	private Player currentPlayer;
	private int floorCountryPerPlayer,ceilingCountryperPlayer;
	
	/**
	 * Initialise inputs.
	 */
	@Before
	public void initialiseInputs() {
		sController=new StartupPhaseController();
		countries=new ArrayList<String>();
		players=new ArrayList<Player>();
		playerQueue=new LinkedList<Player>();
		numberofPlayers=10;
		numberofCountries=11;
		for(int i=0;i<numberofCountries;i++) {
		countries.add("Country"+(i+1));
		}
		for(int i=0;i<numberofPlayers;i++) {
			players.add(new Player(i+1,"Player"+(i+1)));
		}
		playerQueue.addAll(players);
		sController.setCountries(countries);
		sController.setPlayerQueue(playerQueue);
		floorCountryPerPlayer=Math.floorDiv(countries.size(),numberofPlayers);
		ceilingCountryperPlayer=(int) Math.ceil((double)(countries.size()/(double)numberofPlayers));
		}
	
	/*/
	 * Function to test random country assignment
	 */
	
	/**
	 * Initialise players test.
	 */
	@Test
	public void initialisePlayersTest() {
		sController.initialisePlayers();
		playerQueue=sController.getPlayerQueue();
		while(!playerQueue.isEmpty()) {
			currentPlayer=playerQueue.remove();
			Assert.assertTrue(currentPlayer.getCountriesOwned().size()==floorCountryPerPlayer||currentPlayer.getCountriesOwned().size()==ceilingCountryperPlayer);
			Assert.assertTrue(countries.size()==0);
			}
		
	}
	
}
