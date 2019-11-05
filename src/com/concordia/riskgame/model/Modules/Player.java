/*
 * 
 */
package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.utilities.Phases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import java.util.Scanner;
import java.util.logging.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player extends Observable {
	
	private static final int COUNTRY_DIVISION_FACTOR=9;
	
	private  int playerIndex;
	
	private String playerName;
	
	private ArrayList<String> countriesOwned;
	
	private ArrayList<Card> cardsOwned;
	
	private int armyCount;

	private boolean cardFlag;

	private int cardExchangeIndex;




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
		this.cardFlag = false;
		this.cardExchangeIndex = 0;
	}

	public int getNumOfInfCard() {
		int n = 0;
		for (Card card : cardsOwned) {
			if (card == Card.INFANTRY) {
				n++;
			}
		}
		return n;
	}

	public int getNumOfCavCard() {
		int n = 0;
		for (Card card : cardsOwned) {
			if (card == Card.CAVALRY) {
				n++;
			}
		}
		return n;
	}
	public int getNumOfArtCard() {
		int n = 0;
		for (Card card : cardsOwned) {
			if (card == Card.ARTILLERY) {
				n++;
			}
		}
		return n;
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
		gameplay.triggerObserver("domination");
		gameplay.triggerObserver("showmap");
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
	public void addNewCard(Card card) {
		cardsOwned.add(card);
	}

	
	public int getArmyCount() {
		return armyCount;
	}


	public boolean getCardFlag() {
		return cardFlag;
	}
	public void setCardFlag() {
		cardFlag = true;
	}
	public void resetCardFlag() {
		cardFlag = false;
	}

	public int getCardExchangeNum() {
		return cardExchangeIndex;
	}

	public void addCardExchangeNum() {
		cardExchangeIndex++;
	}


	private Dice dice = new Dice();
	private int numOfAttackDice;
	private int numOfDefensiveDice;
	private Country fromCountry;
	private Country toCountry;
	private String[] commands;
	private Gameplay gameplay = Gameplay.getInstance();;
	private Map gameMap = gameplay.getSelectedMap();
	private Player defensivePlayer;
	private Scanner scanner;

	/**
	 * Reinforce army to one country, parse country and number from command, then do move.
	 * @param command
	 * @return Add number of army to corresponding armies.
	 */

	public void reinforceArmy(String command) {
		commands = command.split(" ");
		String countryName = commands[1];
		int reinforcement = Integer.parseInt(commands[2]);
		if (!getCountriesOwned().contains(commands[1])) {
			gameplay.addToViewLogger("Not your country!");
			return;
		}
		if(armyCount < reinforcement) {
			gameplay.addToViewLogger("Entered count more than the number of armies available for the current player.Please enter a smaller value.");
			return;
		}

		setArmyCount(armyCount - reinforcement);
		int n = gameplay.getSelectedMap().searchCountry(countryName).getNoOfArmiesPresent();
		gameplay.getSelectedMap().searchCountry(countryName).setNoOfArmiesPresent(n + reinforcement);

	}

	/**
	 * Perform attack command sparse
	 * @param command
	 * @param sc
	 */

	public void attack(String command, Scanner sc) {
		scanner = sc;
		commands = command.split(" ");
		fromCountry = gameMap.searchCountry(commands[1]);
		toCountry = gameMap.searchCountry(commands[2]);
		defensivePlayer = toCountry.getOwnedBy();
		if (fromCountry == null || toCountry == null) {
			gameplay.addToViewLogger("Cannot find the country");
			return;
		}

		if (!checkAttackCommand()) {
			return;
		}

		if ("-allout".equals(commands[3])) {
			autoAttack();
			gameplay.triggerObserver("domination");
			gameplay.triggerObserver("showmap");
		} else {
			boolean commandDone = false;
			String[] command2;
			gameplay.addToViewLogger("Input defend command!");
			while (!commandDone) {
				command2 = scanner.nextLine().split(" ");
				if (!checkDefendCommand(command2)) {
					gameplay.addToViewLogger("Incorrect defend command!");
					continue;
				}
				commandDone = true;
			}
			attackOnce();
			gameplay.triggerObserver("domination");
			gameplay.triggerObserver("showmap");
		}

		if (!checkAvailableAttack()) {
			gameplay.addToViewLogger("Moving from " + gameplay.getCurrentPhase() + " Phase to Fortification Phase.");
			gameplay.setCurrentPhase(Phases.Fortification);
		}
		
	}

	/**
	 * Check available attack in attack phase, print out all available attacks.
	 * @return If there is available attack, return true, otherwise false.
	 */

	public boolean checkAvailableAttack() {
		boolean attackAvailable = false;
		gameplay.addToViewLogger("Next available attacks are:");
		for (String countryName : gameplay.getCurrentPlayer().getCountriesOwned()) {
			Country country = gameplay.getSelectedMap().searchCountry(countryName);
			if (country.getNoOfArmiesPresent() != 1) {
				for (String neighbor : country.getListOfNeighbours()) {
					if (!gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
						Country neighborCountry = gameplay.getSelectedMap().searchCountry(neighbor);
						gameplay.addToViewLogger(countryName + " " + country.getNoOfArmiesPresent() + " → " + neighbor + " " + neighborCountry.getNoOfArmiesPresent());
						attackAvailable = true;
					}
				}
			}
		}
		return attackAvailable;
	}

	/**
	 * Check defend command, whether the defend dice num is valid or not.
	 * @param commands
	 * @return
	 */
	private boolean checkDefendCommand(String[] commands){
		if (commands.length != 2) {
			return false;
		}
		if (!commands[0].equals("defend")) {
			return false;
		}
		try {
			numOfDefensiveDice = Integer.parseInt(commands[1]);
		}catch (NumberFormatException ex) {
			return false;
		}
		if (numOfDefensiveDice > toCountry.getNoOfArmiesPresent()) {
			gameplay.addToViewLogger("Defensive country doesn't have enough armies to do defence!");
			return false;
		}
		if (numOfDefensiveDice > 2 || numOfDefensiveDice <= 0) {
			gameplay.addToViewLogger("Number of defensive dice invalid! It should be 0 < num <=2");
			return false;
		}
		return true;
	}

	/**
	 * Perform one time attack, compare the result of dice, do deduction of result. check if the country is conquered
	 * @return attack done, return true.
	 */

	private boolean attackOnce() {
		String attackDiceLine="";
		String defenderDiceLine="";
		ArrayList<Integer> attackDice = dice.rollNDice(numOfAttackDice);
		ArrayList<Integer> defensiveDice = dice.rollNDice(numOfDefensiveDice);

		for(Integer dice:attackDice)
			attackDiceLine+=String.valueOf(dice)+",";

		for(Integer dice:defensiveDice)
			defenderDiceLine+=String.valueOf(dice)+",";
		
		
		gameplay.addToViewLogger(attackDiceLine.substring(0, attackDiceLine.length()-1));
		gameplay.addToViewLogger(defenderDiceLine.substring(0, defenderDiceLine.length()-1));
		int defensiveCountryLose = 0;
		int offensiveCountryLose = 0;
		for (int i = 0; i < Math.min(defensiveDice.size(), attackDice.size()); i++) {

			if (attackDice.get(i) > defensiveDice.get(i)) {
				toCountry.removeNoOfArmiesCountry();
				defensiveCountryLose++;
			} else {
				fromCountry.removeNoOfArmiesCountry();
				offensiveCountryLose++;
			}
		}
		gameplay.addToViewLogger(fromCountry.getCountryName() + " lose " + offensiveCountryLose + " armies");
		gameplay.addToViewLogger(toCountry.getCountryName() + " lose " + defensiveCountryLose + " armies");


		if (fromCountry.getNoOfArmiesPresent() == 1) {
			gameplay.addToViewLogger("Offensive country left only one army, cannot do any more attack");
			return true;
		}

		if (toCountry.getNoOfArmiesPresent() == 0) {

			gameplay.addToViewLogger("You've conquered " + commands[2]);
			//remove country from enemy Player
			defensivePlayer.getCountriesOwned().remove(commands[2]);
			//Change owner
			gameplay.getCurrentPlayer().setCountriesOwned(commands[2]);
			toCountry.setOwnedBy(this);

			isPlayerOut();

			//Set card flag, give a card at the end of this turn
			setCardFlag();
			gameplay.addToViewLogger("Input your move command!");
			gameplay.addToViewLogger("Attack country has " + fromCountry.getNoOfArmiesPresent() + "armies");
			boolean moveCommandDone = false;
			int moveArmies = 0;
			while (!moveCommandDone) {
				String[] moveCommands = scanner.nextLine().split(" ");
				if (!checkMoveCommands(moveCommands)) {
					gameplay.addToViewLogger("Incorrect move command!");
					continue;
				}
				moveCommandDone = true;
				moveArmies = Integer.parseInt(moveCommands[1]);
			}
			
			fromCountry.setNoOfArmiesPresent(fromCountry.getNoOfArmiesPresent() - moveArmies);
			toCountry.setNoOfArmiesPresent(moveArmies);

			return true;
		}
		return false;
	}
	/**
	 * Check attack move command, the number of movement should be valid.
	 * @param moveCommands
	 * @return
	 */

	private boolean checkMoveCommands(String[] moveCommands) {
		int moveNum;
		if (moveCommands.length != 2) {
			return false;
		}
		if (!moveCommands[0].equals("attackmove")) {
			return false;
		}
		try {
			moveNum = Integer.parseInt(moveCommands[1]);
		}catch (NumberFormatException ex) {
			gameplay.addToViewLogger("Not an integer");
			return false;
		}
		if (moveNum > fromCountry.getNoOfArmiesPresent() - 1) {
			gameplay.addToViewLogger("Not so many armies");
			return false;
		}
		if (moveNum < 1) {
			gameplay.addToViewLogger("You must move at least 1 army to conquered country");
			return false;
		}
		return true;
	}
	/**
	 * Check attack command, check attack country and defending country's owner, neighbour, armies.
	 * @return
	 */
	private boolean checkAttackCommand() {
		if (!getCountriesOwned().contains(commands[1])) {
			gameplay.addToViewLogger("Offensive Country is not your country! Re-input:");
			return false;
		}
		if (getCountriesOwned().contains(commands[2])) {
			gameplay.addToViewLogger("You cannot attack yourself! Re-input:");
			return false;
		}
		if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
			gameplay.addToViewLogger("Target country is not offensive country's neighbor! Re-input:");
			return false;
		}
		if (fromCountry.getNoOfArmiesPresent() == 1) {
			gameplay.addToViewLogger("Offensive country doesn't have army to attack!");
			return false;
		}
		if (!commands[3].equals("-allout")) {
			try {
				numOfAttackDice = Integer.parseInt(commands[3]);
			}catch (NumberFormatException ex) {
				gameplay.addToViewLogger("Attack dice is not an integer");
				return false;
			}

			if (numOfAttackDice > 3 || numOfAttackDice <= 0) {
				gameplay.addToViewLogger("Attack dice invalid, should be 0 < num <= 3!");
				return false;
			}

			if (numOfAttackDice > fromCountry.getNoOfArmiesPresent() - 1) {
				gameplay.addToViewLogger("Offensive country only has " + (fromCountry.getNoOfArmiesPresent() - 1) + " armies to do attack!");
				return false;
			}

		}
		return true;
	}

	/**
	 * check whether one conquered country's owner is out, if it is, add it to removed player list.
	 */

	private void isPlayerOut() {
		if (defensivePlayer.getCountriesOwned().size() == 0) {
			int n = 0;
			for (Card card : defensivePlayer.getCardsOwned()){
				gameplay.getCurrentPlayer().addNewCard(card);
				n++;
			}
			gameplay.addRemovedPlayer(defensivePlayer);
			gameplay.getPlayers().remove(defensivePlayer);
			gameplay.addToViewLogger(defensivePlayer.getPlayerName() + " is out! You've got his "+ n + " cards");
			isWinner();
			
			}
	}

	/**
	 * After each player out, check whether the winner player is the final winner. If it is, exit game.
	 */
	private void isWinner() {
		if (gameplay.getPlayers().size() == 1) {
			gameplay.addToViewLogger("Game Over! " +"Winner: " + gameplay.getCurrentPlayer().getPlayerName());
		}
	}
	/**
	 * When -allout is specified, do auto attack till attack country left only one army or defending country is conquered.
	 */
	private void autoAttack() {
		while (fromCountry.getNoOfArmiesPresent() != 1 && toCountry.getNoOfArmiesPresent() != 0) {
			if (fromCountry.getNoOfArmiesPresent() > 3) {
				numOfAttackDice = 3;
			} else {
				numOfAttackDice = fromCountry.getNoOfArmiesPresent() - 1;
			}
			if (toCountry.getNoOfArmiesPresent() >= 2 ) {
				numOfDefensiveDice = 2;
			} else {
				numOfDefensiveDice = toCountry.getNoOfArmiesPresent();
			}
			
			if (attackOnce()){
				break;
			}
		}
	
		
	}

	public boolean fortifyArmy(String command) {
		String[] commands = command.split(" ");
		fromCountry = gameMap.searchCountry(commands[1]);
		toCountry = gameMap.searchCountry(commands[2]);
		int num = Integer.parseInt(commands[3]);

		if (!getCountriesOwned().contains(commands[1]) || !getCountriesOwned().contains(commands[2])) {
			gameplay.addToViewLogger("Not your country!");
			return false;
		}
		if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
			gameplay.addToViewLogger("Target country is not neighbor");
			return false;
		}
		if (num >= gameMap.searchCountry(commands[1]).getNoOfArmiesPresent()) {
			gameplay.addToViewLogger("You don't have so many armies");
			return false;
		}
		int n = gameMap.searchCountry(commands[1]).getNoOfArmiesPresent() - num;
		gameMap.searchCountry(commands[1]).setNoOfArmiesPresent(n);
		int m = gameMap.searchCountry(commands[2]).getNoOfArmiesPresent() + num;
		gameMap.searchCountry(commands[2]).setNoOfArmiesPresent(m);
		gameplay.triggerObserver("domination");
		gameplay.triggerObserver("showmap");
		
		return true;
	}
	
	

}
