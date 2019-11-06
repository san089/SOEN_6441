/**
 *
 *
 * <b> This is a singleton. </b>
 * This Module is the core of the game. It has various functionalities that controls the gameplay.
 *
 * @author Sucheta, Hai Feng, Shubham, Sanchit
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Phases;

// TODO: Auto-generated Javadoc
public class Gameplay extends Observable {

	private static final int MAX_PLAYER_LIMIT = 6;
	private int playerCount;
	private ArrayList<Player> players;
	private Map selectedMap;
	private Queue<Player> playerQueue;
	private Player currentPlayer;
	private Phases currentPhase;
	private static Gameplay gameplayObj = null;
	private ArrayList<Player> removedPlayer;
	private ArrayList<String> viewLogger;

	public static Gameplay getInstance() {
		if (gameplayObj == null) {
			gameplayObj = new Gameplay();
			gameplayObj.players = new ArrayList<Player>();
			gameplayObj.selectedMap = null;
			gameplayObj.currentPhase = Phases.MapEditor;
			gameplayObj.playerCount = 0;
			gameplayObj.playerQueue = new LinkedList<Player>();
			gameplayObj.removedPlayer = new ArrayList<>();
			gameplayObj.viewLogger = new ArrayList<>();
			gameplayObj.currentPlayer = null;
		}
		return gameplayObj;
	}

	/**
	 * Instantiates a new gameplay.
	 */
	private Gameplay() {

	}

	public ArrayList<String> getViewLogger() {
		return viewLogger;
	}

	public void addToViewLogger(String logmessage) {
		System.out.println(logmessage);
		viewLogger.add(logmessage);
		triggerObserver("logger");
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void setPlayerQueue(Queue<Player> playerQueue) {
		this.playerQueue = playerQueue;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		triggerObserver("player");
	}

	public Queue<Player> getPlayerQueue() {
		return playerQueue;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public ArrayList<Player> getRemovedPlayer() {
		return removedPlayer;
	}

	public void addRemovedPlayer(Player player) {
		removedPlayer.add(player);
	}

	public void setPlayerCount(int playerCount) {
		gameplayObj.playerCount = playerCount;
	}

	public int getPlayerCount() {
		return gameplayObj.playerCount;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * after first reinforcement start, round player strictly.
	 *
	 */
	public void roundRobinPlayer() {
		setCurrentPlayer(playerQueue.remove());
		setCurrentPlayer(currentPlayer);
		playerQueue.add(currentPlayer);
	}

	public void setArmyCount(int count) {
		for (Player player : getPlayers()) {
			player.setArmyCount(count);
		}

	}

	public Map getSelectedMap() {
		return selectedMap;

	}

	/**
	 * Sets the selected map.
	 *
	 * @param selectedMap the selected map
	 * @return true, if successful
	 */
	public boolean setSelectedMap(Map selectedMap) {
		this.selectedMap = selectedMap;
		return true;

	}

	public Phases getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(Phases currentPhase) {
		gameplayObj.currentPhase = currentPhase;
		viewLogger.clear();
		triggerObserver("phase-logger");
	}

	/**
	 * Adds the player.
	 *
	 * @param playerName the player name
	 * @return the string
	 */
	public String addPlayer(String playerName) {
		if ((players.size() == playerCount && playerCount != 0) || players.size() == MAX_PLAYER_LIMIT) {
			addToViewLogger("PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS");
			return "PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS";
		}
		if (existDuplicatePlayer(playerName)) {
			addToViewLogger("Another player with the same name exists.Please enter a different name");
			return "Another player with the same name exists.Please enter a different name";
		} else {
			players.add(new Player(players.size() + 1, playerName));
			triggerObserver("domination");
			triggerObserver("phase");
			triggerObserver("showmap");
			addToViewLogger("Player " + playerName + " added to the game");
			return "Player " + playerName + " added to the game";
		}

	}

	/**
	 * Exist duplicate player.
	 *
	 * @param playerName the player name
	 * @return true, if successful
	 */
	public boolean existDuplicatePlayer(String playerName) {
		for (Player player : players)
			if (player.getPlayerName().equalsIgnoreCase(playerName))
				return true;

		return false;

	}

	/**
	 * Removes the player.
	 *
	 * @param playerName the player name
	 * @return the string
	 */
	public String removePlayer(String playerName) {
		Player currentPlayer;
		boolean playerFound = false;
		for (Player player:players) {
			currentPlayer = player;
			if (currentPlayer.getPlayerName().equalsIgnoreCase(playerName)) {
				playerFound = true;
				for(Country country:getSelectedMap().getOwnedCountries(playerName))
					country.setOwnedBy(null);
				players.remove(players.indexOf(player));
				triggerObserver("domination");
				triggerObserver("showmap");
				addToViewLogger("Player " + playerName + " removed from the game.");
				return ("Player " + playerName + " removed from the game.");

			}
		}
		if (playerFound == false) {
			addToViewLogger("Player not found.");
			return ("Player not found.");
		}
		return "";

	}

	/**
	 * Validate startup inputs.
	 *
	 * @return the string
	 */
	public String validateStartupInputs() {
		if (getSelectedMap() == null)
			return "Please select a valid map for current game.";

		else if (getPlayers().size() == 0) {
			addToViewLogger("No Players Added");
			return "No Players Added";
		} else if ((getPlayerCount() != 0) && !(getPlayers().size() == getPlayerCount())) {
			addToViewLogger("Number of players added is less than " + getPlayerCount() + ".Please add more players.");
			return "Number of players added is less than " + getPlayerCount() + ".Please add more players.";

		} else if (getPlayers().size() < 2) {
			addToViewLogger("Number of players less than 2.Please add atleast 2 players.");
			return "Number of players less than 2.Please add atleast 2 players.";
		} else if (getPlayers().size() > getSelectedMap().listOfCountryNames().size()) {
			addToViewLogger("Number of countries less than the number of players.Please select another valid map");
			return "Number of countries less than the number of players.Please select another valid map";
		}
		addToViewLogger("Success");
		return "Success";

	}

	/**
	 * Initialise players.
	 */
	public void initialisePlayers() {

		playerQueue.clear();
		playerQueue.addAll(getPlayers());
		List<String> countries = new ArrayList<>();
			
		countries = getSelectedMap().listOfCountryNames();
		for (Player player : getPlayers())
			player.getCountriesOwned().clear();

		while (countries.size() != 0) {
			Random random = new Random();
			int index = random.nextInt(countries.size());
			setCurrentPlayer(playerQueue.remove());
			currentPlayer.setCountriesOwned(countries.get(index));
			getSelectedMap().searchCountry(countries.get(index)).setOwnedBy(currentPlayer);
			playerQueue.add(currentPlayer);
			countries.remove(index);
		}

		for (Player player : getPlayers()) {
			addToViewLogger(player.getPlayerName() + " owns " + player.getCountriesOwned());
		}
		assignStartupArmies();
		addToViewLogger("*******Populate Countries Operation Succesful.Entering Army Placement Phase******** ");
		playerQueue.clear();
		playerQueue.addAll(getPlayers());
		setCurrentPlayer(playerQueue.element());
		addToViewLogger("PLAYER TURN : Place army for " + currentPlayer.getPlayerName()
				+ ". Number of remaining armies " + currentPlayer.getArmyCount());
		
		triggerObserver("domination");
		triggerObserver("showmap");
		

	}

	/**
	 * Assign startup armies.
	 */
	public void assignStartupArmies() {
		switch (players.size()) {

		case 2:
			setArmyCount(40);
			break;
		case 3:
			setArmyCount(35);
			break;

		case 4:
			setArmyCount(30);
			break;

		case 5:
			setArmyCount(25);
			break;

		case 6:
			setArmyCount(20);
			break;

		default:
			addToViewLogger("The number of players in not in the range of 2-6 ");

		}

	}

	/**
	 * Assign reinforcement armies.
	 */
	public void assignReinforcementArmies(){
		int reinforcementArmyCount = ((currentPlayer.getCountriesOwned().size())/3);
		for(Continent continent:getSelectedMap().getOwnedContinents(currentPlayer.getPlayerName())) {
			reinforcementArmyCount = reinforcementArmyCount + continent.getControlValue();
		}
		reinforcementArmyCount = reinforcementArmyCount + currentPlayer.getArmyCount();
		reinforcementArmyCount = Math.max(reinforcementArmyCount, 3);
		currentPlayer.setArmyCount(reinforcementArmyCount);
		triggerObserver("domination");
		triggerObserver("showmap");
	}
/*
	public void assignReinforcementArmies() {

		for(Player player:getPlayers()) {
			
		int reinforcementArmyCount = ((player.getCountriesOwned().size()) / 3);
		for (Continent continent : getSelectedMap().getOwnedContinents(player.getPlayerName())) {
			reinforcementArmyCount = reinforcementArmyCount + continent.getControlValue();
		}
		reinforcementArmyCount = reinforcementArmyCount + player.getArmyCount();
		reinforcementArmyCount = Math.max(reinforcementArmyCount, 3);
		player.setArmyCount(reinforcementArmyCount);
		triggerObserver("domination");
		triggerObserver("showmap");
		}
	}
 */
	  /**
		 * Place army.
		 *
		 * @param countryName the country name
		 * @param count       the count
		 * @param displayArmy display army
		 * @return true true, if successful
		 */
	public boolean placeArmy(String countryName, int count, boolean displayArmy) {
		if (!getSelectedMap().listOfCountryNames().contains(countryName)) {
			addToViewLogger("The country " + countryName + " do not exist in the current game map");
			return false;
		} else if (!currentPlayer.getCountriesOwned().contains(countryName)) {
			addToViewLogger("The country " + countryName + " is not owned by " + currentPlayer.getPlayerName());
			return false;
		} else {
			for (Country country : getSelectedMap().getOwnedCountries(currentPlayer.getPlayerName())) {
				if (!country.getCountryName().equalsIgnoreCase(countryName))
					continue;
				country.setNoOfArmiesPresent(country.getNoOfArmiesPresent() + count);
				addToViewLogger("The country " + countryName + " now has " + country.getNoOfArmiesPresent()+" armies.");
				break;

			}
			currentPlayer.setArmyCount(currentPlayer.getArmyCount() - count);
			/*
			 * if(displayArmy) displayArmyDistribution();
			 */
			triggerObserver("domination");
			triggerObserver("showmap");
			return true;
		}

	}

	/**
	 * Get Abandoned Country count
	 * 
	 * @return count count of countries abondoned.
	 */

	public int getAbandonedCountryCount() {
		int count = 0;
		for (Country country : selectedMap.getOwnedCountries(currentPlayer.getPlayerName()))
			if (country.getNoOfArmiesPresent() == 0)
				count++;
		return count;

	}


	/**
	 * Displays army distribution
	 * 
	 */
	public void displayArmyDistribution() {
		addToViewLogger("PLAYER NAME : [(Country , Armies in the country)]");
		for (Player player : getPlayers()) {
			String line="";
			line=line+(player.getPlayerName() + " :  [");
			for (Country country : selectedMap.getOwnedCountries(player.getPlayerName()))
				line=line+("(" + country.getCountryName() + " , " + country.getNoOfArmiesPresent() + ") ");
			line=line+("]");
			addToViewLogger(line);
			//addToViewLogger("\n");
		}

	}
	

	/**
	 * Place all armies.
	 */
	public void placeAllArmies() {
		Queue<Player> tempQueue = new LinkedList<Player>();
		tempQueue.addAll(players);
		Random random = new Random();

		do {
			Player tempPlayer = tempQueue.remove();
			setCurrentPlayer(tempPlayer);
			for (Country country : selectedMap.getOwnedCountries(tempPlayer.getPlayerName())) {
				if (country.getNoOfArmiesPresent() == 0) {
					country.addNoOfArmiesCountry();
					tempPlayer.setArmyCount(tempPlayer.getArmyCount() - 1);
				}

			}
			int n = tempPlayer.getArmyCount();
			while (n != 0) {
				int index = random.nextInt(tempPlayer.getCountriesOwned().size());
				int armyCount = random.nextInt(tempPlayer.getArmyCount()+1);
				placeArmy(tempPlayer.getCountriesOwned().get(index), armyCount, false);
				n = n - armyCount;
			}

		} while (tempQueue.size() != 0);

		displayArmyDistribution();
		triggerObserver("domination");
		triggerObserver("showmap");

	}

	public void triggerObserver(String observerName) {
		setChanged();
		notifyObservers(observerName);

	}

}
