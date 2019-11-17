package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Strategy;
import com.concordia.riskgame.utilities.Phases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Aggressive implements Strategy {

    private String strategyName = "Aggressive";
    public String ANSI_BLUE = "\u001B[34m";
    private Gameplay gameplay =Gameplay.getInstance();
    private String  strongestCountry;

    public String getColor() {
        return ANSI_BLUE;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    public void doCardExchange(){
        try {
            System.out.println("Bot executing command : " + " exchangecards -none");
            CommandController.parseCommand("exchangecards 3 0 0");
            CommandController.parseCommand("exchangecards 0 3 0");
            CommandController.parseCommand("exchangecards 0 0 3");
            CommandController.parseCommand("exchangecards 1 1 1");
        }
        catch (Exception e){
            System.out.println("Some exception occured while exchange cards command.");
        }

    }

    public void doReinforcement(){
        try {
            strongestCountry = getStrongestCountry();
            String command = "reinforce " + strongestCountry + " " + gameplay.getCurrentPlayer().getArmyCount();
            CommandController.parseCommand(command);
        }
        catch (Exception e) {
            System.out.println("Some exception occured while reinforce command.");
        }

    }

    public void doAttack(){
        Country strongest = gameplay.getSelectedMap().searchCountry(strongestCountry);
        try {
            for (String neighbor : strongest.getListOfNeighbours()) {
                if (gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    continue;
                }
                String command = "attack" + strongestCountry + " " + neighbor + " -allout";
                CommandController.parseCommand(command);
                if (strongest.getNoOfArmiesPresent() != 1) {
                    command = "attackmove 1";
                    CommandController.parseCommand(command);
                } else {
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Some exception occured while attack command.");
        }
    }

    public void doFortification() {
        HashMap<Integer, Country> frontBattleCountry = new HashMap<>();
        HashMap<Country, Country> frontBattleCountryNeighbor = new HashMap<>();
        int i = 0;
        for (String country : gameplay.getCurrentPlayer().getCountriesOwned()) {
            Country currentCountry = gameplay.getSelectedMap().searchCountry(country);
            for (String neighbor : currentCountry.getListOfNeighbours()){
                if (!gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor)) {
                    frontBattleCountry.put(i--, currentCountry);
                }
            }
        }

        for (Country country : frontBattleCountry.values()) {
            int most = 0;
            Country bestNeighbor = null;
            for (String neighbor : country.getListOfNeighbours()){
                Country currentNeighbor = gameplay.getSelectedMap().searchCountry(neighbor);
                if ((currentNeighbor.getNoOfArmiesPresent() - 1) > most) {
                    most = currentNeighbor.getNoOfArmiesPresent() - 1;
                    bestNeighbor = currentNeighbor;
                }
            }
            frontBattleCountry.put(most, country);
            frontBattleCountryNeighbor.put(country, bestNeighbor);
        }
        List<Integer> sortedKeys = new ArrayList<>(frontBattleCountry.keySet());
        Collections.sort(sortedKeys);
        Collections.reverse(sortedKeys);
        Country strongest = frontBattleCountry.get(sortedKeys.get(0));
        Country bestNeighbor = frontBattleCountryNeighbor.get(strongest);
        int army = bestNeighbor.getNoOfArmiesPresent() - 1;
        String command = "fortify " + bestNeighbor .getCountryName()+ " " + strongest.getCountryName() + " " + army;
        try {
            CommandController.parseCommand(command);
        }
        catch (Exception e) {
            System.out.println("Some exception occured while fortify command.");
        }
    }

    public String getStrongestCountry(){
        Country strongest = gameplay.getSelectedMap().getOwnedCountries(gameplay.getCurrentPlayer().getPlayerName()).get(0);
        ArrayList<Country> countryOwnedList = gameplay.getSelectedMap().getOwnedCountries(gameplay.getCurrentPlayer().getPlayerName());
        for(Country c : countryOwnedList){
            if(c.getNoOfArmiesPresent() > strongest.getNoOfArmiesPresent()){
                strongest = c;
            }
        }
        return strongest.getCountryName();
    }
}
