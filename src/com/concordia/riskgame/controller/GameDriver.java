package com.concordia.riskgame.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;

public class GameDriver extends Gameplay {
	private int numberofPlayers;
	private Queue<Player> playerQueue;
	
	
		
	
	//what if the counties cannot be evenly distributed
	
	public int getNumberofPlayers() {
		return numberofPlayers;
	}




	public Queue<Player> getPlayerQueue() {
		return playerQueue;
	}




	public void initialisePlayers(String playerCount,ArrayList<String> countries) {
		Player currentPlayer;
		numberofPlayers=Integer.parseInt(playerCount);
		players=new Player[numberofPlayers];
		playerQueue=new LinkedList<Player>();

		for(int i=0;i<numberofPlayers;i++)
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
