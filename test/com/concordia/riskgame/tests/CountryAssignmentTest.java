package com.concordia.riskgame.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.concordia.riskgame.controller.GameInitDriver;
import com.concordia.riskgame.model.Modules.Player;

// TODO: Auto-generated Javadoc
public class CountryAssignmentTest {

	private GameInitDriver gameDriver;
	private int numberofPlayers,numberofCountries;
	private Queue<Player> playerQueue;
	private ArrayList<String> Countries;
	private Player currentPlayer;
	private int floorCountryPerPlayer,ceilingCountryperPlayer;
	
	/**
	 * Initialise inputs.
	 */
	@Before
	public void initialiseInputs() {
		gameDriver=new GameInitDriver();
		Countries=new ArrayList<String>();
		playerQueue=new LinkedList<Player>();
		numberofPlayers=10;
		numberofCountries=11;
		for(int i=0;i<numberofCountries;i++) {
		Countries.add("Country"+(i+1));
		}
		floorCountryPerPlayer=Math.floorDiv(Countries.size(),numberofPlayers);
		ceilingCountryperPlayer=(int) Math.ceil((double)(Countries.size()/(double)numberofPlayers));
		}
	
	/*/
	 * Function to test random country assignment
	 */
	
	/**
	 * Initialise players test.
	 */
	@Test
	public void initialisePlayersTest() {
		gameDriver.initialisePlayers(numberofPlayers, Countries);
		playerQueue=gameDriver.getPlayerQueue();
		while(!playerQueue.isEmpty()) {
			currentPlayer=playerQueue.remove();
			Assert.assertTrue(currentPlayer.getCountriesOwned().size()==floorCountryPerPlayer||currentPlayer.getCountriesOwned().size()==ceilingCountryperPlayer);
			Assert.assertTrue(Countries.size()==0);
			}
		
	}
	
	
	
	
	
	
	
	
	
	
}
