/*
 * 
 */
package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player {
	
	private static final int COUNTRY_DIVISION_FACTOR=9;
	
	private  int playerIndex;
	
	private String playerName;
	
	private ArrayList<String> countriesOwned;
	
	private ArrayList<Card> cardsOwned;
	
	private int armyCount;
	
	
	
	/**
	 * Instantiates a new player.
	 *
	 * @param playerIndex the player index
	 * @param playerName the player name
	 */
	public Player(int playerIndex, String playerName) {
		this.playerIndex = playerIndex;
		this.playerName = playerName;
		this.countriesOwned = new ArrayList<String>();
		this.cardsOwned = new ArrayList<Card>();
		this.armyCount = 0;
	}

	
	
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	
	public void setCountriesOwned(String country) {
		this.countriesOwned.add(country); 
	}

	public void setCardsOwned(ArrayList<Card> cardsOwned) {
		this.cardsOwned = cardsOwned;
	}

	public void setArmyCount(int armyCount) {
		this.armyCount = armyCount;
	}


	public int getPlayerIndex() {
		return playerIndex;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	
	public ArrayList<String> getCountriesOwned() {
		return countriesOwned;
	}

	public ArrayList<Card> getCardsOwned() {
		return cardsOwned;
	}

	
	public int getArmyCount() {
		return armyCount;
	}
	
		
}
