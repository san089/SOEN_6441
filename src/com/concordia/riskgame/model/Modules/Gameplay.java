package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;

public class Gameplay {

	protected int playerCount;
	protected ArrayList<Player> players;
	//protected String mapfilePath;
	protected Map selectedMap;
	protected String currentPhase;
	
	
	
	
	public Gameplay() {
		this.players = new ArrayList<Player>();
		this.selectedMap = null;
		this.currentPhase = null;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
			
	public void addPlayer(String playerName) {
		players.add(new Player(players.size()+1, playerName));
	
	}
	
	public void removePlayer(String playerName) {
		for(Player player:players)
			if(player.getPlayerName().equalsIgnoreCase(playerName))
				players.remove(player);
	
	}
		
	
	public Map getSelectedMap() {
		return selectedMap;
	}
	public boolean setSelectedMap(String selectedMapPath) {
		boolean result=false;
		//stub method to open the map from path and verify its validity
		//set the attribute if valid and return true
		return result;
	
	}

	public String getCurrentPhase() {
		return currentPhase;
	}
	
	
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
	
	
	
}
