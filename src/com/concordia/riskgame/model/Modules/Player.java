/*
 * 
 */
package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.utilities.Phases;

import java.util.ArrayList;
import java.util.Scanner;

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


	public void reinforceArmy(String command) {
		commands = command.split(" ");
		String countryName = commands[1];
		int reinforcement = Integer.parseInt(commands[2]);
		if (!getCountriesOwned().contains(commands[1])) {
			System.out.println("Not your country!");
			return;
		}
		if(armyCount < reinforcement) {
			System.out.println("Entered count more than the number of armies available for the current player.Please enter a smaller value.");
			return;
		}

		setArmyCount(armyCount - reinforcement);
		int n = gameplay.getSelectedMap().searchCountry(countryName).getNoOfArmiesPresent();
		gameplay.getSelectedMap().searchCountry(countryName).setNoOfArmiesPresent(n + reinforcement);
	}


	public void attack(String command, Scanner sc) {
		scanner = sc;
		commands = command.split(" ");
		fromCountry = gameMap.searchCountry(commands[1]);
		toCountry = gameMap.searchCountry(commands[2]);
		defensivePlayer = toCountry.getOwnedBy();
		if (fromCountry == null || toCountry == null) {
			System.out.println("Cannot find the country");
			return;
		}

		if (!checkAttackCommand()) {
			return;
		}

		if ("-allout".equals(commands[3])) {
			autoAttack();
		} else {
			boolean commandDone = false;
			String[] command2;
			System.out.println("Input defend command!");
			while (!commandDone) {
				command2 = scanner.nextLine().split(" ");
				if (!checkDefendCommand(command2)) {
					System.out.println("Incorrect defend command!");
					continue;
				}
				commandDone = true;
			}
			attackOnce();
		}

		if (!checkAvailableAttack()) {
			System.out.println("Moving from " + gameplay.getCurrentPhase() + " Phase to Fortification Phase.");
			gameplay.setCurrentPhase(Phases.Fortification);
		}

	}

	public boolean checkAvailableAttack() {
		boolean attackAvailable = false;
		System.out.println("Next available attacks are:");
		for (String countryName : gameplay.getCurrentPlayer().getCountriesOwned()) {
			Country country = gameplay.getSelectedMap().searchCountry(countryName);
			if (country.getNoOfArmiesPresent() != 1) {
				for (String neighbor : country.getListOfNeighbours()) {
					if (!gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
						Country neighborCountry = gameplay.getSelectedMap().searchCountry(neighbor);
						System.out.println(countryName + country.getNoOfArmiesPresent() + " →" + neighbor + " " + neighborCountry.getNoOfArmiesPresent());
						attackAvailable = true;
					}
				}
			}
		}
		return attackAvailable;
	}

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
			System.out.println("Defensive country doesn't have enough armies to do defence!");
			return false;
		}
		if (numOfDefensiveDice > 2 || numOfDefensiveDice <= 0) {
			System.out.println("Number of defensive dice invalid! It should be 0 < num <=2");
			return false;
		}
		return true;
	}


	private boolean attackOnce() {
		ArrayList<Integer> attackDice = dice.rollNDice(numOfAttackDice);
		ArrayList<Integer> defensiveDice = dice.rollNDice(numOfDefensiveDice);

		System.out.println(attackDice);
		System.out.println(defensiveDice);
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
		System.out.println(fromCountry.getCountryName() + " lose " + offensiveCountryLose + " armies");
		System.out.println(toCountry.getCountryName() + " lose " + defensiveCountryLose + " armies");


		if (fromCountry.getNoOfArmiesPresent() == 1) {
			System.out.println("Offensive country left only one army, cannot do any more attack");
			return true;
		}

		if (toCountry.getNoOfArmiesPresent() == 0) {

			System.out.println("You've conquered " + commands[2]);
			//remove country from enemy Player
			defensivePlayer.getCountriesOwned().remove(commands[2]);
			//Change owner
			gameplay.getCurrentPlayer().setCountriesOwned(commands[2]);
			toCountry.setOwnedBy(this);

			isPlayerOut();

			//Set card flag, give a card at the end of this turn
			setCardFlag();
			System.out.println("Input your move command!");
			System.out.println("Attack country has " + fromCountry.getNoOfArmiesPresent() + "armies");
			boolean moveCommandDone = false;
			int moveArmies = 0;
			while (!moveCommandDone) {
				String[] moveCommands = scanner.nextLine().split(" ");
				if (!checkMoveCommands(moveCommands)) {
					System.out.println("Incorrect move command!");
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
			System.out.println("Not an integer");
			return false;
		}
		if (moveNum > fromCountry.getNoOfArmiesPresent() - 1) {
			System.out.println("Not so many armies");
			return false;
		}
		if (moveNum < 1) {
			System.out.println("You must move at least 1 army to conquered country");
			return false;
		}
		return true;
	}

	private boolean checkAttackCommand() {
		if (!getCountriesOwned().contains(commands[1])) {
			System.out.println("Offensive Country is not your country! Re-input:");
			return false;
		}
		if (getCountriesOwned().contains(commands[2])) {
			System.out.println("You cannot attack yourself! Re-input:");
			return false;
		}
		if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
			System.out.println("Target country is not offensive country's neighbor! Re-input:");
			return false;
		}
		if (fromCountry.getNoOfArmiesPresent() == 1) {
			System.out.println("Offensive country doesn't have army to attack!");
			return false;
		}
		if (!commands[3].equals("-allout")) {
			try {
				numOfAttackDice = Integer.parseInt(commands[3]);
			}catch (NumberFormatException ex) {
				System.out.println("Attack dice is not an integer");
				return false;
			}

			if (numOfAttackDice > 3 || numOfAttackDice <= 0) {
				System.out.println("Attack dice invalid, should be 0 < num <= 3!");
				return false;
			}

			if (numOfAttackDice > fromCountry.getNoOfArmiesPresent() - 1) {
				System.out.println("Offensive country only has " + (fromCountry.getNoOfArmiesPresent() - 1) + " armies to do attack!");
				return false;
			}

		}
		return true;
	}


	private void isPlayerOut() {
		if (defensivePlayer.getCountriesOwned().size() == 0) {
			int n = 0;
			for (Card card : defensivePlayer.getCardsOwned()){
				gameplay.getCurrentPlayer().addNewCard(card);
				n++;
			}
			gameplay.addRemovedPlayer(defensivePlayer);
			gameplay.getPlayers().remove(defensivePlayer);
			System.out.println(defensivePlayer.getPlayerName() + " is out! You've got his "+ n + " cards");
			isWinner();
		}
	}

	private void isWinner() {
		if (gameplay.getPlayers().size() == 1) {
			System.out.println("Game Over! " +"Winner: " + gameplay.getCurrentPlayer().getPlayerName());
			System.exit(0);
		}
	}

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
			System.out.println("Not your country!");
			return false;
		}
		if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
			System.out.println("Target country is not neighbor");
			return false;
		}
		if (num >= gameMap.searchCountry(commands[1]).getNoOfArmiesPresent()) {
			System.out.println("You don't have so many armies");
			return false;
		}
		int n = gameMap.searchCountry(commands[1]).getNoOfArmiesPresent() - num;
		gameMap.searchCountry(commands[1]).setNoOfArmiesPresent(n);
		int m = gameMap.searchCountry(commands[2]).getNoOfArmiesPresent() + num;
		gameMap.searchCountry(commands[2]).setNoOfArmiesPresent(m);
		return true;
	}

}
