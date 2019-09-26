package com.concordia.riskgame.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;



// TODO: Auto-generated Javadoc
/**
 * This class is to initialize the  gameplay and and assign countries to players.
 */
public class GameInitDriver extends Gameplay {
	private int numberofPlayers;
	private Queue<Player> playerQueue;
	
	
		
	
	//what if the counties cannot be evenly distributed
	
	public int getNumberofPlayers() {
		return numberofPlayers;
	}




	public Queue<Player> getPlayerQueue() {
		return playerQueue;
	}




	/**
	 * Initialise the gameplay class with the player count,playernames and map selected.Assign available countries equally to players.
	 *
	 * @param playerCount -Number of players in the current game
	 * @param countries -Number of countries in the map currently selected
	 */
	public void initialisePlayers(int playerCount,ArrayList<String> countries) {
		Player currentPlayer;
		players=new Player[playerCount];
		super.playerCount=playerCount;
		playerQueue=new LinkedList<Player>();

		for(int i=0;i<playerCount;i++)
		{
			players[i]=new Player(i+1, "Player"+(i+1));
			playerQueue.add(players[i]);
		}
		
		while(countries.size()!=0) {
			Random random = new Random();
			int index=random.nextInt(countries.size());
			currentPlayer=playerQueue.remove();
			currentPlayer.setCountriesOwned(countries.get(index));
			playerQueue.add(currentPlayer);			
			System.out.println(currentPlayer+":"+countries.get(index));
			countries.remove(index);
			
		}
		
		
	}
	
	
	
	
}
