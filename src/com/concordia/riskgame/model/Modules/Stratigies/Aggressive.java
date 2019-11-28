package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Strategy;
import com.concordia.riskgame.utilities.Phases;

import java.io.Serializable;
import java.util.*;

public class Aggressive implements Strategy,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strategyName = "Aggressive";
    public String ANSI_BLUE = "\u001B[34m";
    private Gameplay gameplay =Gameplay.getInstance();
    private Country  strongestFrontCountry;

    public String getColor() {
        return ANSI_BLUE;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    public void doCardExchange(){
    	gameplay =Gameplay.getInstance();
        try {
        	
            System.out.println("Bot Executing Command : " + "exchangecards 3 0 0");
            CommandController.parseCommand("exchangecards 3 0 0");
            System.out.println("Bot Executing Command : " + "exchangecards 0 3 0");
            CommandController.parseCommand("exchangecards 0 3 0");
            System.out.println("Bot Executing Command : " + "exchangecards 0 0 3");
            CommandController.parseCommand("exchangecards 0 0 3");
            System.out.println("Bot Executing Command : " + "exchangecards 1 1 1");
            CommandController.parseCommand("exchangecards 1 1 1");
            System.out.println("Bot Executing Command : " + "exchangecards -none");
            CommandController.parseCommand("exchangecards -none");
        }
        catch (Exception e){
            System.out.println("Some exception occured while exchange cards command.");
        }

    }

    public void doReinforcement(){
        try {
            strongestFrontCountry = getStrongestFrontCountry();
            String command = "reinforce " + strongestFrontCountry.getCountryName() + " " + gameplay.getCurrentPlayer().getArmyCount();
            System.out.println("Bot Executing Command : " + command);
            CommandController.parseCommand(command);
        }
        catch (Exception e) {
            System.out.println("Some exception occured while reinforce command.");
        }

    }

    public void doAttack(){
        try {
            for (String neighbor : strongestFrontCountry.getListOfNeighbours()) {
                if (gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    continue;
                }
                String command = "attack " + strongestFrontCountry.getCountryName() + " " + neighbor + " -allout";
                System.out.println("Bot Executing Command : " + command);
                CommandController.parseCommand(command);
                if (gameplay.getCurrentPlayer().isWinner()){
                    return;
                }
                if (strongestFrontCountry.getNoOfArmiesPresent() != 1) {
                    command = "attackmove 1";
                    System.out.println("Bot Executing Command : " + command);
                    CommandController.parseCommand(command);
                } else {
                    break;
                }
            }
            if (strongestFrontCountry.getNoOfArmiesPresent() !=1) {
                String command = "attack -noattack";
                System.out.println("Bot Executing Command : " + command);
                CommandController.parseCommand(command);
            }
        }
        catch (Exception e) {
            System.out.println("Some exception occured while attack command.");
        }
    }

    public void doFortification() {
        Map<Country, Integer> frontBattleCountry = new HashMap<>();
        HashMap<Country, Country> frontBattleCountryNeighbor = new HashMap<>();
        Country bestCountry = frontBattleLine().get(0);
        Country bestNeighbor = null;
        for (Country country : frontBattleLine()) {
            int most = 0;
            for (String neighbor : country.getListOfNeighbours()){
                if (gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    Country currentNeighbor = gameplay.getSelectedMap().searchCountry(neighbor);
                    if ((currentNeighbor.getNoOfArmiesPresent() - 1) > most) {
                        most = currentNeighbor.getNoOfArmiesPresent() - 1;
                        bestNeighbor = currentNeighbor;
                    }
                }
            }
            frontBattleCountry.put(country, most + country.getNoOfArmiesPresent());
            frontBattleCountryNeighbor.put(country, bestNeighbor);
        }

        for (Country country : frontBattleCountry.keySet()) {
            int m = frontBattleCountry.get(country);
            int n = frontBattleCountry.get(bestCountry);
            if ( m > n ) {
                bestCountry = country;
            }
        }
        bestNeighbor = frontBattleCountryNeighbor.get(bestCountry);
        if (bestNeighbor != null) {
            int army = bestNeighbor.getNoOfArmiesPresent() - 1;
            String command = "fortify " + bestNeighbor.getCountryName() + " " + bestCountry.getCountryName() + " " + army;
            try {
                System.out.println("Bot Executing Command : " + command);
                CommandController.parseCommand(command);
            } catch (Exception e) {
                System.out.println("Some exception occured while fortify command.");
            }
        } else {
            String command = "fortify -none";
            try {
                System.out.println("Bot Executing Command : " + command);
                CommandController.parseCommand(command);
            } catch (Exception e) {
                System.out.println("Some exception occured while fortify command.");
            }
        }
    }

    public Country getStrongestFrontCountry(){
        Country strongest = frontBattleLine().get(0);
        for(Country c : frontBattleLine()){
            if(c.getNoOfArmiesPresent() > strongest.getNoOfArmiesPresent()){
                strongest = c;
            }
        }
        return strongest;
    }

    public ArrayList<Country> frontBattleLine() {
        ArrayList<Country> frontBattleCountry = new ArrayList<>();
        for (String country : gameplay.getCurrentPlayer().getCountriesOwned()) {
            Country currentCountry = gameplay.getSelectedMap().searchCountry(country);
            for (String neighbor : currentCountry.getListOfNeighbours()){
                if (!gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    frontBattleCountry.add(currentCountry);
                    break;
                }
            }
        }
        return frontBattleCountry;
    }
}
