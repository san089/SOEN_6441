package com.concordia.riskgame.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;



// TODO: Auto-generated Javadoc
/**
 * This class is to initialize the  gameplay and and assign countries to players.
 */
public class StartupPhaseController extends Gameplay {
	private Queue<Player> playerQueue;
	private List<String> countries;
	
		
	public StartupPhaseController() {
		playerQueue=new LinkedList<Player>();
		countries=new ArrayList<String>();
	}


	public List<String> getCountries() {
		return countries;
	}


	public void setCountries(ArrayList<String> countries) {
		this.countries = countries;
	}


	public void setPlayerQueue(Queue<Player> playerQueue) {
		this.playerQueue = playerQueue;
	}


	public Queue<Player> getPlayerQueue() {
		return playerQueue;
	}

	
	public boolean validateStartupInputs() {
		if(getPlayers().size()==getPlayerCount() && getSelectedMap()!=null)
			return true;
		else
			return false;
		
	}
		

	/**
	 * Initialise the gameplay class with the player count,playernames and map selected.Assign available countries equally to players.
	 *
	 * @param playerCount -Number of players in the current game
	 * @param countries -Number of countries in the map currently selected
	 */
	public void initialisePlayers() {
		Player currentPlayer;
		playerQueue.addAll(getPlayers());
		countries=getSelectedMap().listOfCountryNames();
		
		while(countries.size()!=0) {
			Random random = new Random();
			int index=random.nextInt(countries.size());
			currentPlayer=playerQueue.remove();
			currentPlayer.setCountriesOwned(countries.get(index));
			playerQueue.add(currentPlayer);			
			System.out.println(currentPlayer+":"+countries.get(index));
			countries.remove(index);
			
		}
		
		assignArmies();
			
	}
	
	
	public void assignArmies() {
		
		for(Player player:getPlayers()) {
			player.setReinforcementArmyCount((player.getCountriesOwned().size()/3));
			for(Continent continent:getSelectedMap().ownedContinents(player.getPlayerName()))
				player.setReinforcementArmyCount((player.getReinforcementArmyCount()+continent.getControlValue()));
			if(player.getReinforcementArmyCount()<3)
				player.setReinforcementArmyCount(3);
			
		}	
		
		
		
	}
	
	
	public void placeArmy(Player player,Country country) {
		if(player.getCountriesOwned().contains(country))
			{country.setNoOfArmiesPresent(country.getNoOfArmiesPresent()+1);
			player.setReinforcementArmyCount(player.getReinforcementArmyCount()-1);
			}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
