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
import com.concordia.riskgame.utilities.Phases;
import com.concordia.riskgame.view.StartUpPhaseView;

import javax.print.attribute.standard.QueuedJobCount;

// TODO: Auto-generated Javadoc
public class Gameplay extends Observable{

	private  int playerCount;
	private  ArrayList<Player> players;
	private  Map selectedMap;
	private Queue<Player> playerQueue;
	private Player currentPlayer;
	private static final int MAX_PLAYER_LIMIT=6;
	private Phases currentPhase;
	private static Gameplay gameplayObj = null;
    private ArrayList<Player> removedPlayer;    //add a list of removed player

	
	
	public static Gameplay getInstance(){
		if(gameplayObj == null){
			gameplayObj = new Gameplay();
			gameplayObj.players = new ArrayList<Player>();
			gameplayObj.selectedMap = null;
			gameplayObj.currentPhase =Phases.MapEditor;
			gameplayObj.playerCount=0;
			gameplayObj.playerQueue=new LinkedList<Player>();
			gameplayObj.removedPlayer = new ArrayList<>();
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
		return playerCount;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

    /**
     *after first reinforcement start, round player strictly.
     * @param
     */
    public void roundRobinPlayer() {
        currentPlayer = playerQueue.remove();
        playerQueue.add(currentPlayer);
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

	
	
	public Phases getCurrentPhase() {
		return currentPhase;
	}
	
	
	public void setCurrentPhase(Phases currentPhase) {
		gameplayObj.currentPhase = currentPhase;
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
			if(player.getPlayerName().equals(playerName))
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
			if(currentPlayer.getPlayerName().equals(playerName)) {
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
		System.out.println("*******Populate Countries Operation Succesful.Entering Army Placement Phase******** ");
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


            int reinforcementArmyCount = ((currentPlayer.getCountriesOwned().size())/3);
            for(Continent continent:getSelectedMap().getOwnedContinents(currentPlayer.getPlayerName())) {
                reinforcementArmyCount = reinforcementArmyCount + continent.getControlValue();
            }
            reinforcementArmyCount = (reinforcementArmyCount<3)?(3) : reinforcementArmyCount;
            currentPlayer.setArmyCount(reinforcementArmyCount + currentPlayer.getArmyCount());

//            for(Player player:getPlayers()) {
//			int reinforcementArmyCount=((player.getCountriesOwned().size())/3);
//			for(Continent continent:getSelectedMap().getOwnedContinents(player.getPlayerName())) {
//				reinforcementArmyCount=reinforcementArmyCount+continent.getControlValue();
//			}
//			reinforcementArmyCount=(reinforcementArmyCount<3)?(3):reinforcementArmyCount;
//			player.setArmyCount(reinforcementArmyCount+player.getArmyCount());
//        }
	}

    /**
	 * Place army.
	 *
	 * @param countryName the country name
	 * @param count the count
	 * @return true, if successful
	 */
	public boolean placeArmy(String countryName,int count,boolean displayArmy) {
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
				break;
			
			}
			currentPlayer.setArmyCount(currentPlayer.getArmyCount()-count);
			if(displayArmy)
				displayArmyDistribution();
			
			return true;
		}
		
		
	}
	
	
	/**
	 * 
	 */
	
	public int getAbandonedCountryCount() {
		int count=0;
		for(Country country:selectedMap.getOwnedCountries(currentPlayer.getPlayerName()))
			if(country.getNoOfArmiesPresent()==0)
				count++;
		return count;

	}
	
	
	/**
	 * 
	 */
	public void displayArmyDistribution() {
		System.out.println("PLAYER NAME : [(Country , Armies in the country)]");
		for (Player player : getPlayers()) {
			System.out.print(player.getPlayerName() + " :  [");
			for (Country country : selectedMap.getOwnedCountries(player.getPlayerName()))
				System.out.print("(" + country.getCountryName() + " , " + country.getNoOfArmiesPresent() + ") ");
			System.out.print("]");
			System.out.println();
		}

	}
	
	/**
	 * Place all armies.
	 */
	public void placeAllArmies() {
		Queue<Player> tempQueue=getPlayerQueue();
		Random random = new Random();

		do {
			Player tempPlayer = tempQueue.remove();
			int n = tempPlayer.getArmyCount();
			while (n != 0) {
				int index = random.nextInt(tempPlayer.getCountriesOwned().size());
				selectedMap.searchCountry(tempPlayer.getCountriesOwned().get(index)).addNoOfArmiesCountry();
				n--;
			}
			tempPlayer.setArmyCount(0);
			displayArmyDistribution();
		}while (tempQueue.size() != 0);


		
		
	}
	
}
