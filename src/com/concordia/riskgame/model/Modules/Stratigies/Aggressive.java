package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Strategy;
import com.concordia.riskgame.utilities.Phases;

import java.io.Serializable;
import java.util.*;

/**
 * This class is one of the player's strategy, aggressive player .
 *
 */

public class Aggressive implements Strategy,Serializable {

	private static final long serialVersionUID = 1L;
	private String strategyName = "Aggressive";
    public String ANSI_BLUE = "\u001B[34m";
    private Country  strongestFrontCountry;

    public String getColor() {
        return ANSI_BLUE;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * Aggressive player do card exchange once cards are available.
     */

    public void doCardExchange(){
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

    /**
     * Aggressive player do reinforcement in reinforce phase.
     */

    public void doReinforcement(){
        try {
            strongestFrontCountry = getStrongestFrontCountry();
            String command = "reinforce " + strongestFrontCountry.getCountryName() + " " + Gameplay.getInstance().getCurrentPlayer().getArmyCount();
            System.out.println("Bot Executing Command : " + command);
            CommandController.parseCommand(command);
        }
        catch (Exception e) {
            System.out.println("Some exception occured while reinforce command.");
        }

    }

    /**
     * Aggressive player do attack in attack phase.
     */

    public void doAttack(){
        try {
            for (String neighbor : strongestFrontCountry.getListOfNeighbours()) {
                if (Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    continue;
                }
                String command = "attack " + strongestFrontCountry.getCountryName() + " " + neighbor + " -allout";
                System.out.println("Bot Executing Command : " + command);
                CommandController.parseCommand(command);
                if (Gameplay.getInstance().getCurrentPlayer().isWinner()){
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

    /**
     * Aggressive player do fortification in fortify phase. First find the player's front battle line country, check the
     * most reinforce they can get, and where they can get, then find the best country, get the best neighbor.
     */

    public void doFortification() {
        Map<Country, Integer> frontBattleCountry = new HashMap<>();
        HashMap<Country, Country> frontBattleCountryNeighbor = new HashMap<>();
        Country bestCountry = frontBattleLine().get(0);
        Country bestNeighbor = null;
        for (Country country : frontBattleLine()) {
            int most = 0;
            for (String neighbor : country.getListOfNeighbours()){
                if (Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    Country currentNeighbor = Gameplay.getInstance().getSelectedMap().searchCountry(neighbor);
                    if ((currentNeighbor.getNoOfArmiesPresent() - 1) > most) {
                        most = currentNeighbor.getNoOfArmiesPresent() - 1;
                        bestNeighbor = currentNeighbor;
                    }
                }
            }
            frontBattleCountry.put(country, most + country.getNoOfArmiesPresent());
            frontBattleCountryNeighbor.put(country, bestNeighbor);
        }
        //find the best country
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

    /**
     * This function is to find the strongest country to do attack
     * @return
     */
    public Country getStrongestFrontCountry(){
        Country strongest = frontBattleLine().get(0);
        for(Country c : frontBattleLine()){
            if(c.getNoOfArmiesPresent() > strongest.getNoOfArmiesPresent()){
                strongest = c;
            }
        }
        return strongest;
    }

    /**
     * this country find all the available attack country
     * @return front line country arraylist
     */
    public ArrayList<Country> frontBattleLine() {
        ArrayList<Country> frontBattleCountry = new ArrayList<>();
        for (String country : Gameplay.getInstance().getCurrentPlayer().getCountriesOwned()) {
            Country currentCountry = Gameplay.getInstance().getSelectedMap().searchCountry(country);
            for (String neighbor : currentCountry.getListOfNeighbours()){
                if (!Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    frontBattleCountry.add(currentCountry);
                    break;
                }
            }
        }
        return frontBattleCountry;
    }
}
