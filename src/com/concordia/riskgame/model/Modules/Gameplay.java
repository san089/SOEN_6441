package com.concordia.riskgame.model.Modules;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.MapTools;

public class Gameplay extends Observable{

	private  int playerCount;
	private  ArrayList<Player> players;
	//protected String mapfilePath;
	private  Map selectedMap;
	private  String currentPhase;
	private  MapTools mapTools;
	
	private static Gameplay gameplayObj = null;

	public static Gameplay getInstance(){
		if(gameplayObj == null){
			gameplayObj = new Gameplay();
			gameplayObj.players = new ArrayList<Player>();
			gameplayObj.selectedMap = null;
			gameplayObj.currentPhase = null;
			gameplayObj.playerCount=6;
		}
		return gameplayObj;
	}
	
	
	private Gameplay() {
		this.players = new ArrayList<Player>();
		this.selectedMap = null;
		this.currentPhase = null;
		this.playerCount=6;
	}

	
	
	
	public void setPlayerCount(int playerCount) {
		gameplayObj.playerCount = playerCount;
	}


	public int getPlayerCount() {
		return playerCount;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
			
	public String addPlayer(String playerName) {
		if(players.size()==playerCount)
		{
			//System.out.println("PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS");
			setChanged();
			notifyObservers(playerCount);
			return "PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS";
		}

		if(existDuplicatePlayer(playerName))
			return "Another player with the same name exists.Please enter a different name";
		else{
			players.add(new Player(players.size()+1, playerName));
			System.out.println("Player " + playerName + " added to the game.");
			return "Player"+playerName+"added to the game";
		}

	}
	
	public boolean existDuplicatePlayer(String playerName)
	{
		for(Player player:players)
			if(player.getPlayerName().equalsIgnoreCase(playerName))
				return true;
		
		return false;
			
		
	}
	
	
	public void removePlayer(String playerName) {
		Player currentPlayer;
		boolean playerFound = false;
		for(Iterator<Player> playerIt=players.iterator();playerIt.hasNext();)
			{
			currentPlayer=playerIt.next();
			if(currentPlayer.getPlayerName().equalsIgnoreCase(playerName)) {
				playerFound = true;
				playerIt.remove();
				System.out.println("Player " + playerName + " removed from the game.");
				setChanged();
				notifyObservers(players.size());
				}
			}
		if(playerFound == false)
		{
			System.out.println("Player not found.");
		}
	
	}
		
	
	public Map getSelectedMap() {
		return selectedMap;
		
	}


	public boolean setSelectedMap(String selectedMapPath) {
		
		/*
		Map selectedMap = new File(selectedMapPath);
		if(selectedMap.listOfCountryNames().size()<playerCount)
			return false;
		*/
		return true;
	
	}

	
	
	public String getCurrentPhase() {
		return currentPhase;
	}
	
	
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
	

	
}
