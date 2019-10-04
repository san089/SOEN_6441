package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;

import java.util.*;

/**
 * Reinforcement class contains two method, first one is at the game beginning, every country has only one army, each player
 * places their reinforcement in turn, it's only called at the beginning of the game. Second method is each player places
 * their reinforcement to owned countries till they have no more reinforcement. Second method can be called in game phase.
 */


public class Reinforcement extends Gameplay {
    private GameInitDriver gameDriver;
    private Queue<Player> playerQueue;

    /**
     * This method gives each player a chance to place their reinforcements to owned countries.
     * @param
     * @output All reinforcements are placed into countries.
     */
    public void startPhaseReinforce() {
        //add all players to a queue
        playerQueue.addAll(Arrays.asList(super.players));

        while (playerQueue.size() != 0) {
            //each time, pop out a player, when all are pop out, while loop end.
            Player currentPlayer = playerQueue.remove();
            System.out.println(currentPlayer.getPlayerName());
            System.out.println("You Owned Countries:");
            //print out all the country current player has. With a list number.
            for (int i = 1; i <= currentPlayer.getCountriesOwned().size(); i++) {
                System.out.println(i + ". " + currentPlayer.getCountriesOwned().get(i - 1));
            }

            //call second method to let current player places all his reinforcement
            inGameReinforcement(currentPlayer);
        }
    }

    /**
     * This method achieve reinforcement of one player using command prompt. Till he has no more reinforcement.
     * @param currentPlayer
     */

    public void inGameReinforcement(Player currentPlayer) {

        int reinforcementCount = currentPlayer.getReinforcementArmyCount();
        int addArmy;
        //outside while loop done when there are no more reinforcement.
        while (reinforcementCount != 0) {
            System.out.println("You still have " + reinforcementCount + "Armies.");
            String[] command;
            Boolean done;
            //inner while loop done when user input the correct command, other wise keep asking to input command.
            do {
                done= true;
                System.out.println("Input CountryName and the Number You Want to Add");
                String input = new Scanner(System.in).nextLine();
                command = input.split(" ");
                addArmy = Integer.parseInt(command[2]);
                if (command.length != 3) {
                    System.out.println("Incorrect Command");
                    done = false;
                    continue;
                }
                if (!command[0].equals("reinforce")) {
                    System.out.println("Incorrect Command");
                    done = false;
                    continue;
                }
                if (!currentPlayer.getCountriesOwned().contains(command[1])) {
                    System.out.println("Cannot Find the Country");
                    done = false;
                    continue;
                }
                if (addArmy > reinforcementCount || addArmy <= 0) {
                    System.out.println("You cannot place armies more than you have.");
                    done = false;
                }
            } while (!done);
            //get the index of countries from input according the name of the country. super class should have a list of country object
            int index = Collections.binarySearch(super.countries, command[2], getCountryName); // because super class have no country object, so here can't resolve getCountryName
            //add number of armies to selected country
            int currentArmies = countries[index].getArmyCount() + addArmy;
            countries[index].setArmyCount(currentArmies);
            reinforcementCount -= addArmy;
        }
    }
}
