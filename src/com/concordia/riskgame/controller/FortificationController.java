package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;

public class FortificationController {
    private static Country fromCountry;
    private static Country toCountry;
    private static int num;
    private static Player currentPlayer;
    private static Map gameMap;

	public static boolean fortifyArmy(String command, Gameplay gameplay) {
		String[] commands = command.split(" ");
		fromCountry = gameplay.getSelectedMap().searchCountry(commands[1]);
		toCountry = gameplay.getSelectedMap().searchCountry(commands[2]);
        num = Integer.parseInt(commands[3]);
        currentPlayer = gameplay.getCurrentPlayer();
        gameMap = gameplay.getSelectedMap();

		if (!currentPlayer.getCountriesOwned().contains(commands[1]) || !currentPlayer.getCountriesOwned().contains(commands[2])) {
			System.out.println("Not your country!");
			return false;
		}
		if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
		    System.out.println("Target country is not neighbor");
		    return false;
        }
		if (num >= gameplay.getSelectedMap().searchCountry(commands[1]).getNoOfArmiesPresent()) {
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
