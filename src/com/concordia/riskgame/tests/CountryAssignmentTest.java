package com.concordia.riskgame.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.concordia.riskgame.controller.GameDriver;
import com.concordia.riskgame.model.Modules.Player;

public class CountryAssignmentTest {

	private GameDriver gameDriver;
	private String numberofPlayers;
	private Queue<Player> playerQueue;
	private ArrayList<String> Countries;
	private Player currentPlayer;
	
	@Before
	public void initialiseInputs() {
		gameDriver=new GameDriver();
		Countries=new ArrayList<String>();
		playerQueue=new LinkedList<Player>();
		numberofPlayers="3";
		Countries.add("India");
		Countries.add("America");
		Countries.add("Iran");	
		Countries.add("Canada");
		Countries.add("Pakistan");
		Countries.add("Japan");
		Countries.add("China");
		Countries.add("Afganistan");
		Countries.add("Ukraine");
		Countries.add("Malaysia");
	}
	
	/*/
	 * Function to test random country assignment
	 */
	
	@Test
	public void initialisePlayersTest() {
		gameDriver.initialisePlayers(numberofPlayers, Countries);
		playerQueue=gameDriver.getPlayerQueue();
		while(!playerQueue.isEmpty()) {
			currentPlayer=playerQueue.remove();
			Assert.assertTrue(currentPlayer.getCountriesOwned().size()==3||currentPlayer.getCountriesOwned().size()==4);
			Assert.assertTrue(Countries.size()==0);
			}
		
	}
	
	
	
	
	
	
	
	
	
	
}
