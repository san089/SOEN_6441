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

import java.io.*;


import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Phases;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * The Class Gameplay.
 */
// TODO: Auto-generated Javadoc
public class Gameplay extends Observable implements Serializable {

	private static final long serialVersionUID = 45443434343L;
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
	private String gameMode;
	
	
	
	
	/**
	 * The Class GameplayBuilder- Nested static class which is the builder class for Gameplay.
	 */
	public static class GameplayBuilder {
		private int playerCount;
		private ArrayList<Player> players;
		private Map selectedMap;
		private Queue<Player> playerQueue;
		private Player currentPlayer;
		private Phases currentPhase;
		private ArrayList<Player> removedPlayer;
		private ArrayList<String> viewLogger;
		private String gameMode;
		
		
		/**
		 * Instantiates a new gameplay builder with mandatory variables.
		 *
		 * @param players the players
		 * @param selectedMap the selected map
		 * @param currentPhase the current phase
		 * @param viewLogger the view logger
		 */
		public GameplayBuilder(ArrayList<Player> players, Map selectedMap, Phases currentPhase,
				ArrayList<String> viewLogger) {
			this.players = players;
			this.selectedMap = selectedMap;
			this.currentPhase = currentPhase;
			this.viewLogger = viewLogger;
		}
		
		
		/**
		 * Setplayer count.
		 *
		 * @return the gameplay builder
		 */
		public GameplayBuilder setplayerCount() {
			this.playerCount=this.players.size();
			return this;			
		}
		
		/**
		 * Setplayer queue.
		 *
		 * @param playerQueue the player queue
		 * @return the gameplay builder
		 */
		public GameplayBuilder setplayerQueue(Queue<Player> playerQueue) {
			this.playerQueue=playerQueue;
			return this;
		}
		
		/**
		 * Setcurrent player.
		 *
		 * @param currentPlayer the current player
		 * @return the gameplay builder
		 */
		public GameplayBuilder setcurrentPlayer(Player currentPlayer) {
			this.currentPlayer=currentPlayer;
			return this;
		}
		
		/**
		 * Setremoved player.
		 *
		 * @param removedPlayer the removed player
		 * @return the gameplay builder
		 */
		public GameplayBuilder setremovedPlayer(ArrayList<Player> removedPlayer) {
			this.removedPlayer=removedPlayer;
			return this;
		}
		
		/**
		 * Setgame mode.
		 *
		 * @param gameMode the game mode
		 * @return the gameplay builder
		 */
		public GameplayBuilder setgameMode(String gameMode) {
			this.gameMode=gameMode;
			return this;
		}
		
		/**
		 * Builds the.
		 */
		public void build() {
			
			setGamePlayInstance(this);
			
		}
		
	}

	/**
	 * Gets the single instance of Gameplay.
	 *
	 * @return single instance of Gameplay
	 */
	public static Gameplay getInstance() {
		if (gameplayObj == null) {
			gameplayObj = new Gameplay();
			gameplayObj.players = new ArrayList<Player>();
			gameplayObj.selectedMap = null;
			gameplayObj.currentPhase = Phases.Startup;
			gameplayObj.playerCount = 0;
			gameplayObj.playerQueue = new LinkedList<Player>();
			gameplayObj.removedPlayer = new ArrayList<>();
			gameplayObj.viewLogger = new ArrayList<>();
			gameplayObj.currentPlayer = null;
			gameplayObj.gameMode = "Single";
		}
		return gameplayObj;
	}
	
	/**
	 * Sets the game play instance.
	 *
	 * @param builder the new game play instance
	 */
	public static void  setGamePlayInstance(GameplayBuilder builder) {
		
		gameplayObj.players = builder.players;
		gameplayObj.selectedMap = builder.selectedMap;
		gameplayObj.currentPhase = builder.currentPhase;
		gameplayObj.playerCount = builder.playerCount;
		gameplayObj.playerQueue = builder.playerQueue;
		gameplayObj.removedPlayer = builder.removedPlayer;
		gameplayObj.viewLogger = builder.viewLogger;
		gameplayObj.currentPlayer = builder.currentPlayer;
		gameplayObj.gameMode =builder.gameMode;
		
		
	}


	/**
	 * Instantiates a new gameplay.
	 */
	private Gameplay() {

	}

	/**
	 * Sets the game mode.
	 *
	 * @param gameMode the new game mode
	 */
	public void setGameMode(String gameMode){
		this.gameMode = gameMode;
	}
	
	/**
	 * Gets the game mode.
	 *
	 * @return the game mode
	 */
	public String getGameMode(){
		return gameMode;
	}

	/**
	 * Gets the view logger.
	 *
	 * @return the view logger
	 */
	public ArrayList<String> getViewLogger() {
		return viewLogger;
	}

	/**
	 * Adds the to view logger.
	 *
	 * @param logmessage the logmessage
	 */
	public void addToViewLogger(String logmessage) {
		System.out.println(logmessage);
		viewLogger.add(logmessage);
		triggerObserver("logger");
	}

	/**
	 * Sets the players.
	 *
	 * @param players the new players
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * Sets the player queue.
	 *
	 * @param playerQueue the new player queue
	 */
	public void setPlayerQueue(Queue<Player> playerQueue) {
		this.playerQueue = playerQueue;
	}

	/**
	 * Sets the current player.
	 *
	 * @param currentPlayer the new current player
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		triggerObserver("player");
	}

	/**
	 * Gets the player queue.
	 *
	 * @return the player queue
	 */
	public Queue<Player> getPlayerQueue() {
		return playerQueue;
	}

	/**
	 * Gets the current player.
	 *
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Gets the removed player.
	 *
	 * @return the removed player
	 */
	public ArrayList<Player> getRemovedPlayer() {
		return removedPlayer;
	}

	/**
	 * Adds the removed player.
	 *
	 * @param player the player
	 */
	public void addRemovedPlayer(Player player) {
		removedPlayer.add(player);
	}

	/**
	 * Sets the player count.
	 *
	 * @param playerCount the new player count
	 */
	public void setPlayerCount(int playerCount) {
		gameplayObj.playerCount = playerCount;
	}

	/**
	 * Gets the player count.
	 *
	 * @return the player count
	 */
	public int getPlayerCount() {
		return gameplayObj.playerCount;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
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

	/**
	 * Simulate bot play.
	 */
	public void simulateBotPlay() {

		gameplayObj.setCurrentPhase(Phases.Reinforcement);
		System.out.println("\n\nCurrent player is a bot and strategy is : " + gameplayObj.currentPlayer.getStrategy().getStrategyName());
		System.out.println("Simulating Bot Play");
		System.out.println(CommandController.ANSI_RESET);

		System.out.println(gameplayObj.currentPlayer.getStrategy().getColor());
		System.out.println("==========================" + gameplayObj.currentPlayer.getStrategy().getStrategyName().toUpperCase() +" Bot performing Card Exchange=========================");
		gameplayObj.currentPlayer.getStrategy().doCardExchange();
		waitOneSecond();
		System.out.println("==========================" + gameplayObj.currentPlayer.getStrategy().getStrategyName().toUpperCase() +" Bot performing Reinforcement=========================");
		gameplayObj.currentPlayer.getStrategy().doReinforcement();
		waitOneSecond();
		System.out.println("==========================" + gameplayObj.currentPlayer.getStrategy().getStrategyName().toUpperCase() +" Bot performing Attack=========================");
		gameplayObj.currentPlayer.getStrategy().doAttack();
		waitOneSecond();
		if(gameplayObj.currentPlayer.isWinner()) {
			return;
		}
		System.out.println("==========================" + gameplayObj.currentPlayer.getStrategy().getStrategyName().toUpperCase() +" Bot performing Fortification=========================");
		gameplayObj.currentPlayer.getStrategy().doFortification();
		waitOneSecond();
		System.out.println(CommandController.ANSI_RESET);

		return;
	}

	/**
	 * Sets the army count.
	 *
	 * @param count the new army count
	 */
	public void setArmyCount(int count) {
		for (Player player : getPlayers()) {
			player.setArmyCount(count);
		}
	}

	/**
	 * Gets the selected map.
	 *
	 * @return the selected map
	 */
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

	/**
	 * Gets the current phase.
	 *
	 * @return the current phase
	 */
	public Phases getCurrentPhase() {
		return currentPhase;
	}

	/**
	 * Sets the current phase.
	 *
	 * @param currentPhase the new current phase
	 */
	public void setCurrentPhase(Phases currentPhase) {
		gameplayObj.currentPhase = currentPhase;
		viewLogger.clear();
		triggerObserver("phase-logger");
	}

	/**
	 * Adds the player.
	 *
	 * @param playerName the player name
	 * @param strategy the strategy
	 * @return the string
	 */
	public String addPlayer(String playerName, Strategy strategy) {
		if ((players.size() == playerCount && playerCount != 0) || players.size() == MAX_PLAYER_LIMIT) {
			addToViewLogger("PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS");
			return "PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS";
		}
		if (existDuplicatePlayer(playerName)) {
			addToViewLogger("Another player with the same name exists.Please enter a different name");
			return "Another player with the same name exists.Please enter a different name";
		} else {
			Player p = new Player(players.size() + 1, playerName);
			p.setStrategy(strategy);
			players.add(p);
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
			//addToViewLogger("No Players Added");
			return "No Players Added";
		} else if ((getPlayerCount() != 0) && !(getPlayers().size() == getPlayerCount())) {
			//addToViewLogger("Number of players added is less than " + getPlayerCount() + ".Please add more players.");
			return "Number of players added is less than " + getPlayerCount() + ".Please add more players.";

		} else if (getPlayers().size() < 2) {
			//addToViewLogger("Number of players less than 2.Please add atleast 2 players.");
			return "Number of players less than 2.Please add atleast 2 players.";
		} else if (getPlayers().size() > getSelectedMap().listOfCountryNames().size()) {
			//addToViewLogger("Number of countries less than the number of players.Please select another valid map");
			return "Number of countries less than the number of players.Please select another valid map";
		}
		//addToViewLogger("Success");
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
	 * Get Abandoned Country count.
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
	 * Displays army distribution.
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

	/**
	 * Trigger observer.
	 *
	 * @param observerName the observer name
	 */
	public void triggerObserver(String observerName) {
		setChanged();
		notifyObservers(observerName);
	}






	/**
	 * Wait one second.
	 */
	public void waitOneSecond() {
		try {
			TimeUnit.SECONDS.sleep(1);
		}
		catch (Exception e){
			System.out.println("Gameplay class. Some Exception occured while waiting.");
		}
	}

	

}
