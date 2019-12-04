package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.*;

import java.io.Serializable;
import java.util.*;

/**
 * Random player's turn
 */

public class Random implements Strategy,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strategyName = "Random";
    public String ANSI_CYAN = "\u001B[36m";

    public String getColor() {

        return ANSI_CYAN;
    }

    @Override
    public String getStrategyName() {

        return strategyName;
    }

    /**
     * Random player do card exchange
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
     * Random player do reinforcement
     */

    public void doReinforcement(){
        int armies_available = Gameplay.getInstance().getCurrentPlayer().getArmyCount();
        int randomIndex = generateRandomNumber(0, Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().size());
        String countryName = Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().get(randomIndex);
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

    /**
     * Random player do attack
     */

    public void doAttack(){
        int numberOfTimesToAttack = generateRandomNumber(1, 10);
        System.out.println("\nBot will perform attack " + numberOfTimesToAttack + " times\n");
        for(int i=0;i<numberOfTimesToAttack;i++){
            System.out.println("\n\nAttack ID : " + i+1);
            if(Gameplay.getInstance().getCurrentPlayer().checkAvailableAttack()){
                performRandomAttack();
            }
            else {
                System.out.println("No Attack Available. Exiting attack phase by executing 'attack -noattack'");
                try {
                    CommandController.parseCommand("attack -noattack");
                }
                catch (Exception e){
                    System.out.println("Some exception occured while executing command 'attack -noattack'");
                }
                break;
            }
        }

        System.out.println("\n\nAttack Finished by bot. Moving to fortify.\n\n");
        try {
            CommandController.parseCommand("attack -noattack");
        }
        catch (Exception e){
            System.out.println("In attack phase of bot " + getStrategyName()+ ". Some exception occured while executing command 'attack -noattack'");
        }
    }

    /**
     * Random player do random attack
     */

    public void performRandomAttack(){
        Country attackCountry= null;
        Country defendCountry= null;
        try {
            HashMap<Country, ArrayList<Country>> availableAttacks = Gameplay.getInstance().getCurrentPlayer().getAvailableAttacks();

            //Setting attacking country randomly
            int randomAttackIndex = generateRandomNumber(0, availableAttacks.size());
            ArrayList<Country> tempList = new ArrayList<>(availableAttacks.keySet());
            attackCountry = tempList.get(randomAttackIndex);
            System.out.println("Attacking country : " + attackCountry.getCountryName() + " |||| armies available :" + attackCountry.getNoOfArmiesPresent());


            //Setting defending country randomly
            for (Country c : availableAttacks.keySet()) {
                if (c.getCountryName().equals(attackCountry.getCountryName())) {
                    tempList = availableAttacks.get(c);
                }
            }
            int randomDefendIndex = generateRandomNumber(0, tempList.size());
            defendCountry = tempList.get(randomDefendIndex);
            System.out.println("Defending country : " + defendCountry.getCountryName() + " |||| armies available :" + defendCountry.getNoOfArmiesPresent());
        }
        catch (Exception e){
            System.out.println("BOT : " + getStrategyName() + ". Some exception occured while fetching attack and defend country in attack phase.");
            return;
        }

        //Doing attackonce
        String attackCommand = "attack " + attackCountry.getCountryName() + " " + defendCountry.getCountryName() + " 1";
        String defendCommand = "defend 1";
        System.out.println("Bot executing command : " + attackCommand);
        System.out.println("Bot executing command : " + defendCommand);
        try{
            CommandController.parseCommand(attackCommand);
            CommandController.parseCommand(defendCommand);
        }
        catch (Exception e){
            System.out.println("Some exception occured while executing attack command.");
        }

        if(Gameplay.getInstance().getCurrentPlayer().isAttackMoveCommandInput()){
            try {
                CommandController.parseCommand("attackmove 1");
            }
            catch (Exception e){
                System.out.println("Some exception occured while executing attackmove command.");
            }
        }

    }

    /**
     * Random player do fortification
     */

    public void doFortification() {
        int armies_available = Gameplay.getInstance().getCurrentPlayer().getArmyCount();
        int randomIndex = generateRandomNumber(0, Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().size());
        String countryName = Gameplay.getInstance().getCurrentPlayer().getCountriesOwned().get(randomIndex);
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

    /**
     * Generate a random number for reinforcement, attack, fortification
     * @param min
     * @param max
     * @return a random integer
     */
    public int generateRandomNumber(int min, int max){
        java.util.Random rand = new java.util.Random(); //using fully qualified name as my class name is also Random
        return rand.nextInt((max - min) ) + min ;
    }

}
