package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.*;

import java.util.ArrayList;
import java.util.Scanner;

public class AttackPhaseController {
    private static Dice dice = new Dice();
    private static int numOfAttackDice;
    private static int numOfDefensiveDice;
    private static Country fromCountry;
    private static Country toCountry;
    private static String[] commands;
    private static Gameplay gameplay;
    private static Map gameMap;


    public static void attack(String command, Gameplay gamePlay) {
        gameMap = gamePlay.getSelectedMap();
        gameplay = gamePlay;
        commands = command.split(" ");
        fromCountry = gameplay.getSelectedMap().searchCountry(commands[1]);
        toCountry = gameplay.getSelectedMap().searchCountry(commands[2]);
        switch (commands[3]) {
            case "auto":
                autoAttack();
                break;
            default:
                numOfAttackDice = Integer.parseInt(commands[3]);
                numOfDefensiveDice = Integer.parseInt(commands[4]);
                attackOnce();
        }
        isPlayerOut();
        isWinner();

    }

    private static void attackOnce() {
        ArrayList<Integer> attackDice = dice.rollNDice(numOfAttackDice);
        ArrayList<Integer> defensiveDice = dice.rollNDice(numOfDefensiveDice);

        System.out.println(attackDice);
        System.out.println(defensiveDice);
        int defensiveCountryLose = 0;
        int offensiveCountryLose = 0;
        for (int i = 0; i < Math.min(defensiveDice.size(), attackDice.size()); i++) {

            if (attackDice.get(i) > defensiveDice.get(i)) {
                toCountry.removeNoOfArmiesCountry();
                defensiveCountryLose++;
            } else {
                fromCountry.removeNoOfArmiesCountry();
                offensiveCountryLose++;
            }
        }
        System.out.println(fromCountry.getCountryName() + " lose " + offensiveCountryLose + "armies");
        System.out.println(toCountry.getCountryName() + " lose " + defensiveCountryLose + "armies");


        if (fromCountry.getNoOfArmiesPresent() == 1) {
            System.out.println("Offensive country left only one army, cannot do any more attack");
        }

        if (toCountry.getNoOfArmiesPresent() == 0) {
            System.out.println("You've conquered " + commands[2]);
            //Change owner
            gameplay.getCurrentPlayer().setCountriesOwned(commands[2]);
            toCountry.setOwnedBy(gameplay.getCurrentPlayer());
            //Set card flag, give a card at the end of this turn
            gameplay.getCurrentPlayer().setCardFlag();
            //move left attacking armies to conquered country
            toCountry.setNoOfArmiesPresent(numOfAttackDice - offensiveCountryLose);
            //subtract attacking armies from offensive country
            fromCountry.setNoOfArmiesPresent(fromCountry.getNoOfArmiesPresent() - numOfAttackDice);
        }
    }

    public static boolean checkCommand() {
        if (!gameplay.getCurrentPlayer().getCountriesOwned().contains(commands[1])) {
            System.out.println("Offensive Country is not your country! Re-input:");
            return false;
        }
        if (gameplay.getCurrentPlayer().getCountriesOwned().contains(commands[2])) {
            System.out.println("You cannot attack yourself! Re-input:");
            return false;
        }
        if (!fromCountry.getListOfNeighbours().contains(commands[2])) {
            System.out.println("Target country is not offensive country's neighbor! Re-input:");
            return false;
        }
        if (fromCountry.getNoOfArmiesPresent() == 1) {
            System.out.println("Offensive country doesn't have army to attack!");
            return false;
        }
        if (numOfAttackDice > 3 || numOfAttackDice <= 0) {
            System.out.println("Attack dice invalid, should be 0 < num <= 3!");
            return false;
        }

        if (numOfAttackDice > fromCountry.getNoOfArmiesPresent() - 1) {
            System.out.println("Offensive country only has " + (fromCountry.getNoOfArmiesPresent() - 1) + " armies to do attack!");
            return false;
        }

        if (numOfDefensiveDice > toCountry.getNoOfArmiesPresent()) {
            System.out.println("Defensive country doesn't have enough armies to do defence!");
            return false;
        }
        if (numOfDefensiveDice > 2 || numOfDefensiveDice <=0) {
            System.out.println("Number of defensive dice invalid! It should be 0 < num <=2");
            return false;
        }
        return true;
    }


    public static void isPlayerOut() {
        Player conqueredPlayer = gameMap.searchCountry(commands[2]).getOwnedBy();
        if (conqueredPlayer.getCountriesOwned().size() != 0) {
            return;
        } else {
            int n = 0;
            for (Card card : conqueredPlayer.getCardsOwned()){
                gameplay.getCurrentPlayer().addNewCard(card);
                n++;
            }
            gameplay.setRemovedPlayer(conqueredPlayer);
            gameplay.getPlayers().remove(conqueredPlayer);
            System.out.println(conqueredPlayer.getPlayerName() + " is out! You've got his "+ n + " cards");
        }
    }

    public static void isWinner() {
        if (gameplay.getPlayers().size() == 1) {
            System.out.println("Game Over! " +"Winner: " + gameplay.getCurrentPlayer().getPlayerName());
        }
    }

    public static void autoAttack() {
        while (fromCountry.getNoOfArmiesPresent() != 1 && toCountry.getNoOfArmiesPresent() != 0) {
            if (fromCountry.getNoOfArmiesPresent() > 3) {
                numOfAttackDice = 3;
            } else {
                numOfAttackDice = fromCountry.getNoOfArmiesPresent() - 1;
            }
            if (toCountry.getNoOfArmiesPresent() >= 2 ) {
                numOfDefensiveDice = 2;
            } else {
                numOfDefensiveDice = toCountry.getNoOfArmiesPresent();
            }
            attackOnce();
        }
    }
}
