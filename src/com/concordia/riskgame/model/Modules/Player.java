package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;


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


	/**
	 * Setter for setting player Index
	 * @param playerIndex
	 */
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}


	/**
	 * Setter for setting Player Name
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	/**
	 * Setter for setting countries owned by player
	 * @param country
	 */
	public void setCountriesOwned(String country) {
		this.countriesOwned.add(country); 
	}

	/**
	 * Setter for cards owned by player
	 * @param cardsOwned
	 */
	public void setCardsOwned(ArrayList<Card> cardsOwned) {
		this.cardsOwned = cardsOwned;
	}

	/**
	 * Setter for army count
	 * @param armyCount
	 */
	public void setArmyCount(int armyCount) {
		this.armyCount = armyCount;
	}


	/**
	 * Getter for playerindex
	 * @return playerIndex
	 */
	public int getPlayerIndex() {
		return playerIndex;
	}

	/**
	 * Getter for playerName
	 * @return playerName
	 */
	public String getPlayerName() {
		return playerName;
	}


	/**
	 * Getter for countries owned
	 * @return countriesOwned
	 */
	public ArrayList<String> getCountriesOwned() {
		return countriesOwned;
	}

	/**
	 * Getter for getting cards owned
	 * @return cardsOwned
	 */
	public ArrayList<Card> getCardsOwned() {
		return cardsOwned;
	}


	/**
	 * Getter for getting army count
	 * @return armyCount
	 */
	public int getArmyCount() {
		return armyCount;
	}
	
		
}
