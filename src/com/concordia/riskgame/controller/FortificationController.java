package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;

public class FortificationController {

	public static void fortifyArmy(String command, Player currentPlayer, Map gameMap) {
		System.out.println("This is the abstract class for fortification phase");
		String[] commands = command.split(" ");
		if (!currentPlayer.getCountriesOwned().contains(commands[1]) || !currentPlayer.getCountriesOwned().contains(commands[3])) {
			System.out.println("Not your country!");
			return;
		}
		int num = Integer.parseInt(commands[3]);
		if (num >= gameMap.searchCountry(commands[1]).getNoOfArmiesPresent()) {
			System.out.println("You don't have so many armies");
			return;
		}

		int n = gameMap.searchCountry(commands[1]).getNoOfArmiesPresent() - num;
		gameMap.searchCountry(commands[1]).setNoOfArmiesPresent(n);
		int m = gameMap.searchCountry(commands[2]).getNoOfArmiesPresent() + num;
		gameMap.searchCountry(commands[2]).setNoOfArmiesPresent(m);

	}
	
}
