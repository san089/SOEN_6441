package com.concordia.riskgame.model.Modules;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.MapTools;
import com.concordia.riskgame.view.StartUpPhaseView;

// TODO: Auto-generated Javadoc
public class Gameplay extends Observable{

	private  int playerCount;
	private  ArrayList<Player> players;
	private  Map selectedMap;
	private  String currentPhase;
	private Queue<Player> playerQueue;
	private Player currentPlayer;
	private static final int MAX_PLAYER_LIMIT=6;
	private static Gameplay gameplayObj = null;
	
	public static Gameplay getInstance(){
		if(gameplayObj == null){
			gameplayObj = new Gameplay();
			gameplayObj.players = new ArrayList<Player>();
			gameplayObj.selectedMap = null;
			gameplayObj.currentPhase = "startup";
			gameplayObj.playerCount=0;
			gameplayObj.playerQueue=new LinkedList<Player>();
		}
		return gameplayObj;
	}
	
	/**
	 * Instantiates a new gameplay.
	 */
	private Gameplay() {
		
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void setPlayerQueue(Queue<Player> playerQueue) {
		this.playerQueue = playerQueue;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Queue<Player> getPlayerQueue() {
		return playerQueue;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
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
	
	
	
	public void setArmyCount(int count) {
		for(Player player:getPlayers()) {
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
		this.selectedMap=selectedMap;
		return true;
	
	}

	
	
	public String getCurrentPhase() {
		return currentPhase;
	}
	
	
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
	
	


			
	/**
	 * Adds the player.
	 *
	 * @param playerName the player name
	 * @return the string
	 */
	public String addPlayer(String playerName) {
		if((players.size()==playerCount && playerCount!=0) ||players.size()==MAX_PLAYER_LIMIT )
		{
			setChanged();
			notifyObservers(playerCount);
			return "PLAYER LIMIT REACHED.CANNOT ADD MORE PLAYERS";
		}
		if(existDuplicatePlayer(playerName))
			return "Another player with the same name exists.Please enter a different name";
		else{
			players.add(new Player(players.size()+1, playerName));
			return "Player "+playerName+" added to the game";
		}

	}
	
	/**
	 * Exist duplicate player.
	 *
	 * @param playerName the player name
	 * @return true, if successful
	 */
	public boolean existDuplicatePlayer(String playerName)
	{
		for(Player player:players)
			if(player.getPlayerName().equalsIgnoreCase(playerName))
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
		for(Iterator<Player> playerIt=players.iterator();playerIt.hasNext();)
			{
			currentPlayer=playerIt.next();
			if(currentPlayer.getPlayerName().equalsIgnoreCase(playerName)) {
				playerFound = true;
				playerIt.remove();
				setChanged();
				notifyObservers(players.size());
				return ("Player " + playerName + " removed from the game.");
				
				}
			}
		if(playerFound == false)
		{
			return("Player not found.");
		}
		return "";
	
	}
		
		/**
		 * Validate startup inputs.
		 *
		 * @return the string
		 */
		public String validateStartupInputs() {
		if(getSelectedMap()==null)
			return "Please select a valid map for current game." ;
		
		else if(getPlayers().size()==0)
			return "No Players Added";
		else if((getPlayerCount()!=0)&& !(getPlayers().size() == getPlayerCount()))
				return "Number of players added is less than "+getPlayerCount()+".Please add more players.";
		else if(getPlayers().size()>getSelectedMap().listOfCountryNames().size())
				return "Number of countries less than the number of players.Please select another valid map";
		return "Success";
		
	}
		
	/**
	 * Initialise players.
	 */
	public void initialisePlayers() {
		System.out.println("Total number of countries "+selectedMap.listOfCountryNames().size());
		List<String> countries=new ArrayList<>();
		playerQueue.addAll(getPlayers());
		countries=getSelectedMap().listOfCountryNames();
		for(Player player:getPlayers())
			player.getCountriesOwned().clear();
		
		while(countries.size()!=0) {
			Random random = new Random();
			int index=random.nextInt(countries.size());
			currentPlayer=playerQueue.remove();
			currentPlayer.setCountriesOwned(countries.get(index));
			getSelectedMap().searchCountry(countries.get(index)).setOwnedBy(currentPlayer);
			playerQueue.add(currentPlayer);			
			countries.remove(index);
		}
		
		for(Player player:getPlayers()) {
			System.out.println(player.getPlayerName()+" owns "+player.getCountriesOwned());
			
		}
		assignStartupArmies();
		System.out.println("Populate Countries Operation Succesful.Entering Army Placement Phase ");
		playerQueue.clear();
		playerQueue.addAll(getPlayers());
		currentPlayer=playerQueue.element();
		System.out.println("PLAYER TURN : Place army for "+currentPlayer.getPlayerName()+". Number of remaining armies "+currentPlayer.getArmyCount());
				
			
	}
		
	
	/**
	 * Assign startup armies.
	 */
	public void assignStartupArmies() {
		switch(players.size()) {
		
		case 2:setArmyCount(40);
				break;		
		case 3:setArmyCount(35);
				break;		

		case 4:setArmyCount(30);
				break;		

		case 5:setArmyCount(25);
				break;		

		case 6:setArmyCount(20);
				break;		
				
		default:System.out.println("The number of players in not in the range of 2-6 ");
				
		}
		
		
	}
	
	/**
	 * Assign reinforcement armies.
	 */
	public void assignReinforcementArmies() {
		
		for(Player player:getPlayers()) {
			int reinforcementArmyCount=((player.getCountriesOwned().size())/3);
			for(Continent continent:getSelectedMap().ownedContinents(player.getPlayerName())) {
				reinforcementArmyCount=reinforcementArmyCount+continent.getControlValue();
			}
			reinforcementArmyCount=(reinforcementArmyCount<3)?(3):reinforcementArmyCount;
			player.setArmyCount(reinforcementArmyCount+player.getArmyCount());			
	}		
	}
	
	
	/**
	 * Place army.
	 *
	 * @param countryName the country name
	 * @param count the count
	 * @return true, if successful
	 */
	public boolean placeArmy(String countryName,int count) {
		if(!getSelectedMap().listOfCountryNames().contains(countryName)) {
			System.out.println("The country "+countryName+" do not exist in the current game map");
			return false;
			}
		else if(!currentPlayer.getCountriesOwned().contains(countryName)){
			System.out.println("The country "+countryName+" is not owned by "+currentPlayer.getPlayerName());
			return false;
			}
		
		else
		{
			for(Continent continent:getSelectedMap().getContinents()) {
				Country country=continent.searchCountry(countryName);
				if(country==null)
					continue;
				country.setNoOfArmiesPresent(country.getNoOfArmiesPresent()+count);
				System.out.println("Country "+countryName+" now has "+country.getNoOfArmiesPresent()+" armies");
	        	break;
			
			}
			currentPlayer.setArmyCount(currentPlayer.getArmyCount()-count);
			
			return true;
		}
	}
	
	
	
	
	/**
	 * Place all armies.
	 */
	public void placeAllArmies() {
		Queue<Player> tempQueue=getPlayerQueue();
		while(tempQueue.size()!=0) {
    		setCurrentPlayer(tempQueue.remove());
    		ArrayList<String> countries=getCurrentPlayer().getCountriesOwned();
			Random random = new Random();
			System.out.println("Placing army for "+getCurrentPlayer().getPlayerName());
			int countryindex=0;
			while(getCurrentPlayer().getArmyCount()!=0) 
			{
			int armycount=1;
			placeArmy(countries.get(countryindex), armycount);
			countryindex=(countryindex==countries.size()-1)?0:++countryindex;		
			}
    	}
		
		
	}
	
}
