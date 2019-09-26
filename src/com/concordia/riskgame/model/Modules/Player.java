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
	
	/** The Constant COUNTRY_DIVISION_FACTOR. */
	private static final int COUNTRY_DIVISION_FACTOR=9;
	
	
	/** The player index. */
	private  int playerIndex;
	
	/** The player name. */
	private String playerName;
	
	/** The countries owned. */
	private ArrayList<String> countriesOwned;
	
	/** The cards owned. */
	private ArrayList<Card> cardsOwned;
	
	/** The reinforcement army count. */
	private int reinforcementArmyCount;
	
	
	
	/**
	 * Sets the player index.
	 *
	 * @param playerIndex the new player index
	 */
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	/**
	 * Sets the player name.
	 *
	 * @param playerName the new player name
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Sets the countries owned.
	 *
	 * @param country the new countries owned
	 */
	public void setCountriesOwned(String country) {
		this.countriesOwned.add(country); 
	}

	/**
	 * Sets the cards owned.
	 *
	 * @param cardsOwned the new cards owned
	 */
	public void setCardsOwned(ArrayList<Card> cardsOwned) {
		this.cardsOwned = cardsOwned;
	}

	/**
	 * Sets the reinforcement army count.
	 *
	 * @param reinforcementArmyCount the new reinforcement army count
	 */
	public void setReinforcementArmyCount(int reinforcementArmyCount) {
		this.reinforcementArmyCount = reinforcementArmyCount;
	}

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
		this.reinforcementArmyCount = 0;
	}

	/**
	 * Gets the player index.
	 *
	 * @return the player index
	 */
	public int getPlayerIndex() {
		return playerIndex;
	}
	
	/**
	 * Gets the player name.
	 *
	 * @return the player name
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	
	/**
	 * Gets the countries owned.
	 *
	 * @return the countries owned
	 */
	public ArrayList<String> getCountriesOwned() {
		return countriesOwned;
	}

	/**
	 * Gets the cards owned.
	 *
	 * @return the cards owned
	 */
	public ArrayList<Card> getCardsOwned() {
		return cardsOwned;
	}

	/**
	 * Gets the reinforcement army count.
	 *
	 * @return the reinforcement army count
	 */
	public int getReinforcementArmyCount() {
		return reinforcementArmyCount;
	}
	
		
}
