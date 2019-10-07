package com.concordia.riskgame.model.Modules;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.utilities.MapTools;

public class Gameplay extends Observable{

	private int playerCount;
	private ArrayList<Player> players;
	//protected String mapfilePath;
	private Map selectedMap;
	private String currentPhase;
	private MapTools mapTools;
	
	
	
	
	public Gameplay() {
		this.players = new ArrayList<Player>();
		this.selectedMap = null;
		this.currentPhase = null;
		this.playerCount=0;
	}

	
	
	
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}


	public int getPlayerCount() {
		return playerCount;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
			
	public void addPlayer(String playerName) {
		players.add(new Player(players.size()+1, playerName));
		if(players.size()==playerCount)
		{
			System.out.println("PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS");
			setChanged();
			notifyObservers(playerCount);
		}
		
	
	}
	
	public boolean findDuplicatePlayer(String playerName)
	{
		for(Player player:players)
			if(player.getPlayerName().equalsIgnoreCase(playerName))
				return false;
		
		return true;
			
		
	}
	
	
	public void removePlayer(String playerName) {
		Player currentPlayer;
		for(Iterator<Player> playerIt=players.iterator();playerIt.hasNext();)
			{
			currentPlayer=playerIt.next();
			if(currentPlayer.getPlayerName().equalsIgnoreCase(playerName))
				{
				playerIt.remove();
				setChanged();
				notifyObservers(players.size());
				}
	}
	
	}
		
	
	public Map getSelectedMap() {
		return selectedMap;
		
	}
	public boolean setSelectedMap(String selectedMapPath) {
		
		Map selectedMap=new File(selectedMapPath);
		if(selectedMap.listOfCountryNames().size()<playerCount)
			return false;
				
		return true;
	
	}

	
	
	public String getCurrentPhase() {
		return currentPhase;
	}
	
	
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
	
	
	
}
