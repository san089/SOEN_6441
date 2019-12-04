/**
 *
 * The Player class handled the player data as well as helps simulate gameplay commands during card exchange,
 * reinforcement, fortification, attack phases of the gameplay.
 *
 * @author Hai Feng
 * @version 2.0
 *
 *
 */
package com.concordia.riskgame.model.Modules;
import com.concordia.riskgame.model.Modules.Stratigies.Human;
import com.concordia.riskgame.utilities.Phases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;



/**
 * The Class Player.
 */
public class Player extends Observable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private  int playerIndex;
	
	private String playerName;
	
	private ArrayList<String> countriesOwned;
	
	private ArrayList<Card> cardsOwned;
	
	private int armyCount;

	private boolean cardFlag;

	private int cardExchangeIndex;

	private Strategy strategy;




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
		this.strategy = new Human();
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
		Gameplay.getInstance().triggerObserver("domination");
		Gameplay.getInstance().triggerObserver("showmap");
	}



	public int getPlayerIndex() {
		return playerIndex;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	
	public ArrayList<String> getCountriesOwned() {
		ArrayList<String> countriesOwned=new ArrayList<String>();
		for (Country country:Gameplay.getInstance().getSelectedMap().getOwnedCountries(this.playerName))
			countriesOwned.add(country.getCountryName());
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
	//private Gameplay gameplay = Gameplay.getInstance();;
	private Map gameMap = Gameplay.getInstance().getSelectedMap();
	private Player defensivePlayer;
	private boolean defendCommandInput = false;
	private boolean attackMoveCommandInput =false;
	private HashMap<Country, ArrayList<Country>> availableAttacks = new HashMap<>();

	public HashMap<Country, ArrayList<Country>> getAvailableAttacks() {
		return availableAttacks;
	}

	public boolean isAttackMoveCommandInput() {
		return attackMoveCommandInput;
	}

	/**
	 * Reinforce army to one country, parse country and number from command, then do move.
	 * @param command command to reinforce.
	 */

	public void reinforceArmy(String command) {
		commands = command.split(" ");
		String countryName = commands[1];
		int reinforcement = Integer.parseInt(commands[2]);
		if (!getCountriesOwned().contains(commands[1])) {
			Gameplay.getInstance().addToViewLogger("Not your country!");
			return;
		}
		if(armyCount < reinforcement) {
			Gameplay.getInstance().addToViewLogger("Entered count more than the number of armies available for the current player.Please enter a smaller value.");
			return;
		}

		setArmyCount(armyCount - reinforcement);
		int n = Gameplay.getInstance().getSelectedMap().searchCountry(countryName).getNoOfArmiesPresent();
		Gameplay.getInstance().getSelectedMap().searchCountry(countryName).setNoOfArmiesPresent(n + reinforcement);

	}

	/**
	 * Perform attack command sparse
	 * @param command command to attack a country
	 */

	public void attack(String command) {
		if (attackMoveCommandInput || defendCommandInput) {
			System.out.println("You have not finished last attack!");
			return;
		}
		commands = command.split(" ");
		fromCountry = gameMap.searchCountry(commands[1]);
		toCountry = gameMap.searchCountry(commands[2]);
		defensivePlayer = toCountry.getOwnedBy();
		if (fromCountry == null || toCountry == null) {
			Gameplay.getInstance().addToViewLogger("Cannot find the country");
			return;
		}

		if (!checkAttackCommand()) {
			return;
		}

		if ("-allout".equals(commands[3])) {
			autoAttack();
			Gameplay.getInstance().triggerObserver("domination");
			Gameplay.getInstance().triggerObserver("showmap");
            if (isWinner()) {
                return;
            }
		} else {
			Gameplay.getInstance().addToViewLogger("Input defend command!");
			defendCommandInput = true;
		}
		
	}

	/**
	 * To get player's number of infantry card
	 * @return
	 */
	public int getNumOfInfCard() {
		int n = 0;
		for (Card card : cardsOwned) {
			if (card == Card.INFANTRY) {
				n++;
			}
		}
		return n;
	}

	/**
	 * To get player's number of cavalery card
	 * @return
	 */
	public int getNumOfCavCard() {
		int n = 0;
		for (Card card : cardsOwned) {
			if (card == Card.CAVALRY) {
				n++;
			}
		}
		return n;
	}

	/**
	 * To get number of art cards
	 * @return
	 */
	public int getNumOfArtCard() {
		int n = 0;
		for (Card card : cardsOwned) {
			if (card == Card.ARTILLERY) {
				n++;
			}
		}
		return n;
	}

	/**
	 * check defending command
	 * @param command
	 */

	public void defendCommand(String command) {
		if (!defendCommandInput) {
			System.out.println("Not defending time");
			return;
		}
		String[] command2 = command.split(" ");
		if (!checkDefendCommand(command2)) {
			Gameplay.getInstance().addToViewLogger("Incorrect defend command!");
			return;
		}
		attackOnce();
		if (isWinner()) {
			return;
		}
		Gameplay.getInstance().triggerObserver("domination");
		Gameplay.getInstance().triggerObserver("showmap");

		if (!checkAvailableAttack()) {
			Gameplay.getInstance().addToViewLogger("Moving from " + Gameplay.getInstance().getCurrentPhase() + " Phase to Fortification Phase.");
			Gameplay.getInstance().setCurrentPhase(Phases.Fortification);
		}
		defendCommandInput = false;
	}

	/**
	 * Check available attack in attack phase, print out all available attacks.
	 * @return If there is available attack, return true, otherwise false.
	 */

	public boolean checkAvailableAttack() {
		availableAttacks.clear();
		ArrayList<Country> defendCountry = new ArrayList<>();

		boolean attackAvailable = false;
		Gameplay.getInstance().addToViewLogger("Next available attacks are:");
		for (String countryName : Gameplay.getInstance().getCurrentPlayer().getCountriesOwned()) {
			Country country = Gameplay.getInstance().getSelectedMap().searchCountry(countryName);
			if (country.getNoOfArmiesPresent() != 1) {
				for (String neighbor : country.getListOfNeighbours()) {
					if (!Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
						Country neighborCountry = Gameplay.getInstance().getSelectedMap().searchCountry(neighbor);
						Gameplay.getInstance().addToViewLogger(countryName + " " + country.getNoOfArmiesPresent() + " → " + neighbor + " " + neighborCountry.getNoOfArmiesPresent());
						attackAvailable = true;
						defendCountry.add(neighborCountry);
					}
				}
				if(defendCountry.size() > 0){
					availableAttacks.put(country, deepCopyArrayList(defendCountry));
					defendCountry.clear();
				}
			}
		}
		return attackAvailable;
	}

	private ArrayList<Country> deepCopyArrayList(ArrayList<Country> defend){
		ArrayList<Country> deepCopyCountry = new ArrayList<>();
		for(Country c: defend){
			deepCopyCountry.add(c);
		}
		return deepCopyCountry;
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
			Gameplay.getInstance().addToViewLogger("Defensive country doesn't have enough armies to do defence!");
			return false;
		}
		if (numOfDefensiveDice > 2 || numOfDefensiveDice <= 0) {
			Gameplay.getInstance().addToViewLogger("Number of defensive dice invalid! It should be 0 < num <=2");
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
		
		
		Gameplay.getInstance().addToViewLogger(attackDiceLine.substring(0, attackDiceLine.length()-1));
		Gameplay.getInstance().addToViewLogger(defenderDiceLine.substring(0, defenderDiceLine.length()-1));
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
		Gameplay.getInstance().addToViewLogger(fromCountry.getCountryName() + " lose " + offensiveCountryLose + " armies");
		Gameplay.getInstance().addToViewLogger(toCountry.getCountryName() + " lose " + defensiveCountryLose + " armies");


		if (fromCountry.getNoOfArmiesPresent() == 1) {
			Gameplay.getInstance().addToViewLogger("Offensive country left only one army, cannot do any more attack");
			if (!checkAvailableAttack()) {
				Gameplay.getInstance().addToViewLogger("Moving from " + Gameplay.getInstance().getCurrentPhase() + " Phase to Fortification Phase.");
				Gameplay.getInstance().setCurrentPhase(Phases.Fortification);
			}
			return true;
		}

		if (toCountry.getNoOfArmiesPresent() == 0) {

			Gameplay.getInstance().addToViewLogger("You've conquered " + commands[2]);
			//remove country from enemy Player
			defensivePlayer.getCountriesOwned().remove(commands[2]);
			//Change owner
			Gameplay.getInstance().getCurrentPlayer().setCountriesOwned(commands[2]);
			toCountry.setOwnedBy(this);

			isPlayerOut();
            if (isWinner()){
                Gameplay.getInstance().addToViewLogger("Game Over! " +"Winner: " + Gameplay.getInstance().getCurrentPlayer().getPlayerName());
                return true;
            }

			//Set card flag, give a card at the end of this turn
			setCardFlag();
			Gameplay.getInstance().addToViewLogger("Input your move command!");
			Gameplay.getInstance().addToViewLogger("Attack country has " + fromCountry.getNoOfArmiesPresent() + "armies");
			attackMoveCommandInput = true;
			return true;
		}
		return false;
	}

	public void attackMove(String command){
		if (!attackMoveCommandInput) {
			System.out.println("Not moving time");
			return;
		}
		String[] moveCommands = command.split(" ");
		if (!checkMoveCommands(moveCommands)) {
			Gameplay.getInstance().addToViewLogger("Incorrect move command!");
			return;
		}
		int moveArmies = Integer.parseInt(moveCommands[1]);
		fromCountry.setNoOfArmiesPresent(fromCountry.getNoOfArmiesPresent() - moveArmies);
		toCountry.setNoOfArmiesPresent(moveArmies);
		if (!checkAvailableAttack()) {
			Gameplay.getInstance().addToViewLogger("Moving from " + Gameplay.getInstance().getCurrentPhase() + " Phase to Fortification Phase.");
			Gameplay.getInstance().setCurrentPhase(Phases.Fortification);
		}
		attackMoveCommandInput = false;
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
			Gameplay.getInstance().addToViewLogger("Not an integer");
			return false;
		}
		if (moveNum > fromCountry.getNoOfArmiesPresent() - 1) {
			Gameplay.getInstance().addToViewLogger("Not so many armies");
			return false;
		}
		if (moveNum < 1) {
			Gameplay.getInstance().addToViewLogger("You must move at least 1 army to conquered country");
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
			Gameplay.getInstance().addToViewLogger("Offensive Country is not your country! Re-input:");
			return false;
		}
		if (getCountriesOwned().contains(commands[2])) {
			Gameplay.getInstance().addToViewLogger("You cannot attack yourself! Re-input:");
			return false;
		}
		if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
			Gameplay.getInstance().addToViewLogger("Target country is not offensive country's neighbor! Re-input:");
			return false;
		}
		if (fromCountry.getNoOfArmiesPresent() == 1) {
			Gameplay.getInstance().addToViewLogger("Offensive country doesn't have army to attack!");
			return false;
		}
		if (!commands[3].equals("-allout")) {
			try {
				numOfAttackDice = Integer.parseInt(commands[3]);
			}catch (NumberFormatException ex) {
				Gameplay.getInstance().addToViewLogger("Attack dice is not an integer");
				return false;
			}

			if (numOfAttackDice > 3 || numOfAttackDice <= 0) {
				Gameplay.getInstance().addToViewLogger("Attack dice invalid, should be 0 < num <= 3!");
				return false;
			}

			if (numOfAttackDice > fromCountry.getNoOfArmiesPresent() - 1) {
				Gameplay.getInstance().addToViewLogger("Offensive country only has " + (fromCountry.getNoOfArmiesPresent() - 1) + " armies to do attack!");
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
				Gameplay.getInstance().getCurrentPlayer().addNewCard(card);
				n++;
			}
			Gameplay.getInstance().addRemovedPlayer(defensivePlayer);
			Gameplay.getInstance().getPlayers().remove(defensivePlayer);
			Gameplay.getInstance().addToViewLogger(defensivePlayer.getPlayerName() + " is out! You've got his "+ n + " cards");
			}
	}

	/**
	 * After each player out, check whether the winner player is the final winner. If it is, exit game.
	 */
	public boolean isWinner() {
		if (Gameplay.getInstance().getPlayers().size() == 1) {
			return true;
		}
		return false;
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

	/**
	 * Player do fortification
	 * @param command
	 * @return
	 */

	public boolean fortifyArmy(String command) {
		String[] commands = command.split(" ");
		fromCountry = gameMap.searchCountry(commands[1]);
		toCountry = gameMap.searchCountry(commands[2]);
		int num = Integer.parseInt(commands[3]);

		if (!getCountriesOwned().contains(commands[1]) || !getCountriesOwned().contains(commands[2])) {
			Gameplay.getInstance().addToViewLogger("Not your country!");
			return false;
		}
		if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
			Gameplay.getInstance().addToViewLogger("Target country is not neighbor");
			return false;
		}
		if (num >= gameMap.searchCountry(commands[1]).getNoOfArmiesPresent()) {
			Gameplay.getInstance().addToViewLogger("You don't have so many armies");
			return false;
		}
		int n = gameMap.searchCountry(commands[1]).getNoOfArmiesPresent() - num;
		gameMap.searchCountry(commands[1]).setNoOfArmiesPresent(n);
		int m = gameMap.searchCountry(commands[2]).getNoOfArmiesPresent() + num;
		gameMap.searchCountry(commands[2]).setNoOfArmiesPresent(m);
		Gameplay.getInstance().triggerObserver("domination");
		Gameplay.getInstance().triggerObserver("showmap");
		
		return true;
	}


	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
}
