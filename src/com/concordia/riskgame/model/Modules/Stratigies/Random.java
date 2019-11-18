package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.*;

import java.util.*;

public class Random implements Strategy {

    private String strategyName = "Random";
    public String ANSI_CYAN = "\u001B[36m";
    Gameplay gameplay = Gameplay.getInstance();

    public String getColor() {

        return ANSI_CYAN;
    }

    @Override
    public String getStrategyName() {

        return strategyName;
    }

    public void doCardExchange(){
        try {
            System.out.println("Bot executing command : " + " exchangecards -none");
            CommandController.parseCommand("exchangecards -none");
        }
        catch (Exception e){
            System.out.println("Some exception occured while exchange cards command.");
        }
    }

    public void doReinforcement(){
        int armies_available = gameplay.getCurrentPlayer().getArmyCount();
        int randomIndex = generateRandomNumber(0, gameplay.getCurrentPlayer().getCountriesOwned().size());
        String countryName = gameplay.getCurrentPlayer().getCountriesOwned().get(randomIndex);
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
        int numberOfTimesToAttack = generateRandomNumber(1, 10);
        System.out.println("\nBot will perform attack " + numberOfTimesToAttack + " times\n");
        for(int i=0;i<numberOfTimesToAttack;i++){
            System.out.println("\n\nAttack ID : " + i+1);
            if(gameplay.getCurrentPlayer().checkAvailableAttack()){
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

    public void performRandomAttack(){
        Country attackCountry= null;
        Country defendCountry= null;
        try {
            HashMap<Country, ArrayList<Country>> availableAttacks = gameplay.getCurrentPlayer().getAvailableAttacks();

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

        if(gameplay.getCurrentPlayer().isAttackMoveCommandInput()){
            try {
                CommandController.parseCommand("attackmove 1");
            }
            catch (Exception e){
                System.out.println("Some exception occured while executing attackmove command.");
            }
        }

    }



    public void doFortification() {
        int armies_available = gameplay.getCurrentPlayer().getArmyCount();
        int randomIndex = generateRandomNumber(0, gameplay.getCurrentPlayer().getCountriesOwned().size());
        String countryName = gameplay.getCurrentPlayer().getCountriesOwned().get(randomIndex);
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

    public int generateRandomNumber(int min, int max){
        java.util.Random rand = new java.util.Random(); //using fully qualified name as my class name is also Random
        return rand.nextInt((max - min) ) + min ;
    }

}
