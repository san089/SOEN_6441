package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Strategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cheater player's turn
 */
public class Cheater implements Strategy,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strategyName = "Cheater";
    public String ANSI_PURPLE = "\u001B[35m";

    public String getColor() {
        return ANSI_PURPLE;
    }


    @Override
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * Cheater do card exchange
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
     * cheater do reinforcement
     */

    public void doReinforcement(){

        Country tempCountry = null;
        System.out.println(getStrategyName() + " bot playing reinforcement phase.");
        System.out.println(" Current player is "+Gameplay.getInstance().getCurrentPlayer().getPlayerName());
        ArrayList<Country> ownedCountries = Gameplay.getInstance().getSelectedMap().getOwnedCountries(Gameplay.getInstance().getCurrentPlayer().getPlayerName());


        //Doubling armies in all countries
        for(Country c : ownedCountries){
            System.out.println("Double the armies in " + c.getCountryName());
            c.setNoOfArmiesPresent(2*c.getNoOfArmiesPresent());
            if(c.getNoOfArmiesPresent() > Gameplay.getInstance().getCurrentPlayer().getArmyCount()){
                tempCountry = c;
            }
        }

        //Building reinforcement command
        String reinforceCommand = "";
        if(tempCountry != null){
            reinforceCommand = "reinforce " + tempCountry.getCountryName() + " " + Gameplay.getInstance().getCurrentPlayer().getArmyCount();

        }
        else{
            reinforceCommand = "reinforce " + ownedCountries.get(0).getCountryName() + " " + Gameplay.getInstance().getCurrentPlayer().getArmyCount();
        }

        //Executing reinforcement command
        try{
            System.out.println("Bot Executing command : "+ reinforceCommand);
            CommandController.parseCommand(reinforceCommand);
        }
        catch (Exception e){
            System.out.println("EXCEPTION!! Bot : " + getStrategyName() + " during reinforce command execution.");
        }
    }

    /**
     * cheater do attack
     */
    public void doAttack(){
        System.out.println(getStrategyName() + " bot playing reinforcement phase.");
        ArrayList<Country> ownedCountries = Gameplay.getInstance().getSelectedMap().getOwnedCountries(Gameplay.getInstance().getCurrentPlayer().getPlayerName());

        outerloop:
        for(Country c : ownedCountries){
            List<String> neighbourCountries = c.getListOfNeighbours();

            if(neighbourCountries.size() != 0){
                for(String neigbourCountry : neighbourCountries){
                    Country neighbour = Gameplay.getInstance().getSelectedMap().searchCountry(neigbourCountry);

                    //if neighbour is owned by player itself do not attack
                    if(neighbour.getOwnedBy().getPlayerName().equals(Gameplay.getInstance().getCurrentPlayer().getPlayerName())){
                        continue;
                    }

                    int attackArmyCount = c.getNoOfArmiesPresent();
                    int defendArmyCount = neighbour.getNoOfArmiesPresent();

                    try{
                        c.setNoOfArmiesPresent(999);
                        String attackCommand = "attack " + c.getCountryName() + " " + neigbourCountry + " -allout";
                        CommandController.parseCommand(attackCommand);
                        if(Gameplay.getInstance().getCurrentPlayer().isWinner()){
                            break outerloop;
                        }
                        String attackmove = "attackmove " + defendArmyCount;
                        CommandController.parseCommand(attackmove);
                        c.setNoOfArmiesPresent(attackArmyCount);
                    }
                    catch (Exception e){
                        System.out.println("EXCEPTION!! BOT " + getStrategyName() + " attack phase. Executing attack commands.");
                    }
                }
            }
        }

        if(Gameplay.getInstance().getCurrentPlayer().isWinner()){
            return;
        }

        //Executing no attack to move to fortification phase
        try{
            System.out.println("Bot executing : attack -noattack");
            CommandController.parseCommand("attack -noattack");
        }
        catch (Exception e){
            System.out.println("EXCEPTION!! BOT " + getStrategyName()  + ". Some exception occured ");
        }
    }

    /**
     * cheater do fortification
     */

    public void doFortification() {

        if(Gameplay.getInstance().getCurrentPlayer().isWinner()){
            return;
        }


        for(Country c : Gameplay.getInstance().getSelectedMap().getOwnedCountries(Gameplay.getInstance().getCurrentPlayer().getPlayerName())){
            List<String> neighbourCounties = c.getListOfNeighbours();

            for(String neighbour : neighbourCounties){
                String ownerOfNeighbour = Gameplay.getInstance().getSelectedMap().searchCountry(neighbour).getOwnedBy().getPlayerName();

                //If neighbour country owned by other player double the army in the country.
                if(!ownerOfNeighbour.equals(Gameplay.getInstance().getCurrentPlayer().getPlayerName())){
                    c.setNoOfArmiesPresent(2*c.getNoOfArmiesPresent());
                    break;
                }
            }
        }

        try{
            String fortifyCommand = "fortify -none";
            System.out.println("Bot executing command : " + fortifyCommand);
            CommandController.parseCommand(fortifyCommand);
        }
        catch (Exception e){
            System.out.println("\nEXCEPTION !! Bot " + getStrategyName() + ". Executing fortification command");
        }
    }
}
