package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Phases;

import java.util.InputMismatchException;

public class ReinforcementController {

	
	public static void reinforceArmy(String command, Gameplay gameplay) {
//		System.out.println("\nReinforcement Functionality is currently abstract.\n==============================================================\n");
//		System.out.println(" Rules for Reinforcement Phase: \nThe player is given a number of armies that depends on the number of countries he owns (# of countries owned divided by 3, rounded down). " +
//				"If the player owns all the countries of an\n" +
//				"entire continent, the player is given an amount of armies corresponding to the continent’s control value. Finally, if\n" +
//				"the player owns three cards of different sorts or the same sorts, he can exchange them for armies. The number of\n" +
//				"armies a player will get for cards is first 5, then increases by 5 every time any player does so (i.e. 5, 10, 15, …). In\n" +
//				"any case, the minimal number of reinforcement armies is 3. Once the total number of reinforcements is\n" +
//				"determined for the player’s turn, the player may place the armies on any country he owns, divided as he wants.\n" +
//				"Once all the reinforcement armies have been placed by the player, the attacks phase begins");
        String[] commands = command.split(" ");
        String countryName = commands[1];
        int armyCount = Integer.parseInt(commands[2]);
        int currentReinforce = gameplay.getCurrentPlayer().getArmyCount();
        if(currentReinforce < armyCount) {
            System.out.println("Entered count more than the number of armies available for the current player.Please enter a smaller value.");
            return;
        }

		gameplay.getCurrentPlayer().setArmyCount(currentReinforce - armyCount);
		int n = gameplay.getSelectedMap().searchCountry(countryName).getNoOfArmiesPresent();
		gameplay.getSelectedMap().searchCountry(countryName).setNoOfArmiesPresent(n + armyCount);
	}
}
