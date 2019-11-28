package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Strategy;
import com.concordia.riskgame.model.Modules.Gameplay;

import java.io.Serializable;
import java.util.ArrayList;

public class Benevolent implements Strategy,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strategyName = "Benevolent";
    public String ANSI_YELLOW = "\u001B[33m";
    Gameplay gameplay = Gameplay.getInstance();

    public String getColor() {
        return ANSI_YELLOW;
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
    	gameplay =Gameplay.getInstance();
        int armies_available = gameplay.getCurrentPlayer().getArmyCount();
        String countryName = getWeakestCountry();
        String reinforceCommand;

        if(armies_available > 0){
            reinforceCommand = "reinforce " + countryName + " " + armies_available;
        }
        else{
            reinforceCommand = "reinforce -none";
        }

        try {
            System.out.println("Bot executing command : " + reinforceCommand);
            CommandController.parseCommand(reinforceCommand);
        }
        catch (Exception e){
            System.out.println("Some exception occured while exchange cards command.");
        }
    }

    public void doAttack(){
        System.out.println("Benevolent Bot Does not attack.");
        try{
            System.out.println("Bot executing command : " + "attack -noattack");
            CommandController.parseCommand("attack -noattack");
        }
        catch (Exception e){
            System.out.println("Some exception occured in bot attack command.");
        }

    }

    public void doFortification() {
        int armies_available = gameplay.getCurrentPlayer().getArmyCount();
        String countryName = getWeakestCountry();
        String fortifyCommand;

        if(armies_available > 0){
            fortifyCommand = "fortify " + countryName + " " + armies_available;
        }
        else {
            fortifyCommand = "fortify -none";
        }



        try {
            System.out.println("Bot executing command : " + fortifyCommand);
            CommandController.parseCommand(fortifyCommand);
        }
        catch (Exception e){
            System.out.println("Some exception occured while exchange cards command.");
        }
    }

    public String getWeakestCountry(){
        Country weakest = gameplay.getSelectedMap().getOwnedCountries(gameplay.getCurrentPlayer().getPlayerName()).get(0);
        ArrayList<Country> countryOwnedList = gameplay.getSelectedMap().getOwnedCountries(gameplay.getCurrentPlayer().getPlayerName());
        for(Country c : countryOwnedList){
            if(c.getNoOfArmiesPresent() < weakest.getNoOfArmiesPresent()){
                weakest = c;
            }
        }
        return weakest.getCountryName();
    }

}
