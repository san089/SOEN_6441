package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;

import java.util.Collections;
import java.util.Scanner;

/**
 * This class is fortification phase of game. There is only one method, it can be called
 * in game.
 */

public class Fortification extends Gameplay {


    /**
     * This method achieve fortification phase of game, it asks current player to input
     * correct command to add army from one country to another country.
     * @param currentPlayer
     */


    public void fortification(Player currentPlayer) {

        System.out.println("Now is fortification phase, enter your command here:");
        //Declare an string array for command.
        String[] command;
        int movedArmy = 0;
        int index1 = -1;
        int index2;
        Boolean done;

        //keep asking player input correct command
        do {
            done= true;
            String input = new Scanner(System.in).nextLine();
            command = input.split(" ");

            //if command length is not 2 or 4, command must be incorrect
            if ((command.length != 2) && (command.length != 4)) {
                System.out.println("Incorrect Command, Retry:");
                done = false;
                continue;
            }
            //if first command is not fortify, it must be incorrect.
            if (!command[0].equals("fortify")) {
                System.out.println("Incorrect Command, Retry:");
                done = false;
                continue;
            }
            //if second command is none, just return.
            if (command[1].equals("none")){
                return;
            }
            //if first country cannot be found, do again
            if (!currentPlayer.getCountriesOwned().contains(command[1])) {
                System.out.println("Cannot Find First Country, Retry:");
                done = false;
                continue;
            }
            //if second country cannot be found, do again
            if (!currentPlayer.getCountriesOwned().contains(command[2])) {
                System.out.println("Cannot Find Second Country, Retry:");
                done = false;
                continue;
            }
            //find the index of first country from countries list
            index1 = Collections.binarySearch(super.countries, command[1], getCountryName);
            //if second country is not one neighbour of the first country, do again
            if (!countries[index1].getAdjacentCountries().contains(command[2])) {
                System.out.println("Two countries are not neighbor, Retry:");
                done = false;
                continue;
            }
            movedArmy = Integer.parseInt(command[3]);
            //find the number of army in first county, if less than to be moved armies, retry
            if (movedArmy >= countries[index1].getArmyCount() || movedArmy <= 0) {
                System.out.println("You cannot place armies more than you have.");
                done = false;
            }
        } while (!done);
        //if command is correct and moved armies is reasonable, do move
        index2 = Collections.binarySearch(super.countries, command[2], getCountryName);
        int secondCountryArmies = countries[index2].getArmyCount() + movedArmy;
        int firstCountryArmies = countries[index1].getArmyCount() - movedArmy;
        countries[index2].setArmyCount(secondCountryArmies);
        countries[index1].setArmyCount(firstCountryArmies);
    }

}
