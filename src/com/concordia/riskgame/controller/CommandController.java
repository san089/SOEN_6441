package com.concordia.riskgame.controller;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to parse the commands entered by user and perform actions based on commands
 */
public class CommandController {

    public static String commandType; // command type
    public static HashMap<String, Integer> addContinent;
    public static ArrayList<String> removeContinent;
    public static HashMap<String, String> addCountry;
    public static ArrayList<String> removeCountry;
    public static HashMap<String, String> addNeighbour;
    public static HashMap<String, String> removeNeighbour;
    public static ArrayList<String> addPlayer;
    public static ArrayList<String> removePlayer;


    /**
     * This method takes command as input and calls respective methods corresponding to the command and executes the method.
     *
     * @param command takes command input from user
     */
    public static void parseCommand(String command) {
        command = command.trim().replace(" +", " "); //replace multiple whitespaces with one.
        commandType = command.split(" ")[0];

        switch (commandType)
        {
            case "editcontinent": editContinent(command); break;
            case "editcountry": editCountry(command); break;
            case "editneighbor": editNeighbour(command); break;
            case "showmap": System.out.println("Yet to configure"); break;
            case "savemap": System.out.println("Yet to configure"); break;
            case "editmap": System.out.println("Yet to configure"); break;
            case "vaildatemap": System.out.println("Yet to configure"); break;
            case "loadmap" : System.out.println("Yet to configure"); break;
            case "gameplayer" : gamePlayer(command); break;
            case "populatecountries" : System.out.println("Yet to configure"); break;
            case "placearmy" : System.out.println("Yet to configure"); break;
            case "placeall" : System.out.println("Yet to configure"); break;
            case "reinforce" : System.out.println("Yet to configure"); break;
            case "fortify" : System.out.println("Yet to configure"); break;
            case "help" : showHelpOptions(); break;
            default: System.out.println("Invalid Command"); showHelpOptions(); break;
        }
    }


    /**
     * This method performs add continent and remove continent actions based on the valid command.
     *
     * @param command Command to execute
     */
    public static void editContinent(String command)
    {
        if(validateEditContinentCommand(command)){
            for(String Key : addContinent.keySet())
            {
                //Add continent functiono call.
            }
            for(String val : removeContinent)
            {
                //Remove continent function call.
            }
        }
    }

    /**
     * @param val Takes a string input
     * @return return true if the string has only characters a-z, else return false.
     */
    public static boolean verifyAllCharacters(String val)
    {
        return  val.matches("^[a-zA-Z]*$");
    }

    public static boolean verifyNumber(String val)
    {
        try {
            Integer.parseInt(val);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }

    /**
     * This method reads editcontinent command, validates the command and add continent to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     */
    public static boolean validateEditContinentCommand(String command)
    {
        addContinent.clear();
        removeContinent.clear();

        String[] args = command.split(" ");
        String arg_type;
        String value1;
        String value2;
        for (int i=1; i< args.length ; i+=2)
        {
            arg_type = args[i];
            if(arg_type.trim().equals("-add")) {
                value1 = args[i+1];
                value2 = args[i+2];
                if(verifyAllCharacters(value1) && verifyNumber(value2))
                {
                    addContinent.put(value1, Integer.parseInt(value2));
                }
                i+=1;
            }
            else if(arg_type.trim().equals("-remove"))  {
                value1 = args[i+1];
                if(verifyAllCharacters(value1)) {
                    removeContinent.add(value1);
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    /**
     * This method takes editcountry command as input and perform actions as per command.
     *
     * @param command command to execute
     */
    public static void editCountry(String command)
    {
        if(validateEditCountryCommand(command))
        {
            for(String countryName : addCountry.keySet())
            {
                //add country code
            }
            for(String countryName : removeCountry)
            {
                // remove country code
            }
        }
    }

    /**
     * This method reads editcountry command, validates the command and add country to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     */
    public static boolean validateEditCountryCommand(String command)
    {
        addContinent.clear();
        removeCountry.clear();

        String[] args = command.split(" ");
        String arg_type;
        String value1;
        String value2;
        for (int i=1; i< args.length ; i+=2)
        {
            arg_type = args[i];
            if(arg_type.trim().equals("-add")) {
                value1 = args[i+1];
                value2 = args[i+2];
                if(verifyAllCharacters(value1) && verifyAllCharacters(value2))
                {
                    addCountry.put(value1, value2);
                }
                i+=1;
            }
            else if(arg_type.trim().equals("-remove"))  {
                value1 = args[i+1];
                if(verifyAllCharacters(value1)) {
                    removeCountry.add(value1);
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    /**
     * This method takes editneighbour command as input and perform actions as per command.
     *
     * @param command command to execute
     */
    public static void editNeighbour(String command)
    {
        if(validateEditNeighbourCommand(command))
        {
            for(String countryName : addNeighbour.keySet())
            {
                String neighbourName = addNeighbour.get(countryName);
                //add country code
            }
            for(String countryName : removeNeighbour.keySet())
            {
                String neighbourName = addNeighbour.get(countryName);
                // remove country code
            }
        }
    }


    /**
     * This method reads editneighbour command, validates the command and add neighbour to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     */
    public static boolean validateEditNeighbourCommand(String command)
    {
        addNeighbour.clear();
        removeNeighbour.clear();

        String[] args = command.split(" ");
        String arg_type;
        String value1;
        String value2;
        for (int i=1; i< args.length ; i+=3)
        {
            arg_type = args[i];
            value1 = args[i+1];
            value2 = args[i+2];
            if(verifyAllCharacters(value1) && verifyAllCharacters(value2)) {
                if (arg_type.trim().equals("-add")) {
                    addNeighbour.put(value1, value2);
                } else if (arg_type.trim().equals("-remove")) {
                    removeNeighbour.put(value1, value2);
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * This method reads gameplayer command, validates the command and add or remove player.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     */
    public static void gamePlayer(String command)
    {
        if(validateGamePlayerCommand(command))
        {
            for(String playerName : addPlayer)
            {
                //add player command here
            }
            for(String playerName : removePlayer)
            {
                //remove player command here
            }
        }
    }


    /**
     * This method reads gamplayer command, validates the command and add player to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     */
    public static boolean validateGamePlayerCommand(String command)
    {
        addPlayer.clear();
        removePlayer.clear();

        String[] args = command.split(" ");
        String arg_type;
        String value1;
        for (int i=1; i< args.length ; i+=2)
        {
            arg_type = args[i];
            value1 = args[i+1];

            if (arg_type.trim().equals("-add")) {
                if(verifyAllCharacters(value1)){
                    addPlayer.add(value1);
                }
            }
            else if(arg_type.trim().equals("-remove"))
            {
                if(verifyAllCharacters(value1))
                {
                    removePlayer.add(value1);
                }
            }
            else{
                return false;
            }
        }
        return true;
    }


    /**
     * Method to print help options for commands
     */
    public static void showHelpOptions()
    {
        System.out.println("For getting help on commands, type help.");
        System.out.println("editcontinent [-add] <continentname> <continentvalue> \t command to add continent to a map.");
        System.out.println("editcontinent [-remove] <continentname> \t command to remove continent from a map.");
        System.out.println("editcountry [-add] <countryname> <continentname> \t command to add country to a map.");
        System.out.println("editcountry [-remove] <countryname>  \t command to remove country from a map.");
        System.out.println("editneighbour [-add] <countryname> <neighbourcountryname> \t command to add neighbour country to a map.");
        System.out.println("editcountry [-remove] <countryname> \t command to remove neighbour country from a map.");
        System.out.println("showmap \t command to display map");
        System.out.println("savemap <filename> \t command to save map");
        System.out.println("editmap <filename> \t command to load and edit map");
        System.out.println("validatemap \t command to validate loaded map");
        System.out.println("showmap \t command to display loaded map");
        System.out.println("loadmap <filename> \t command to load a map");
        System.out.println("gameplayer [-add] <playername> \t command to add player to game");
        System.out.println("gameplayer [-remove] <playername> \t command to remove player from game");
        System.out.println("populatecountries \t command initialize game and assign country to players");
        System.out.println("placearmy <countryname> \t command to place army for a player in a country");
        System.out.println("placeall \t automatically randomly place all remaining unplaced armies for all players");
        System.out.println("reinforce <countryname> <num> \t until all reinforcements have been placed");
        System.out.println("fortify <fromcountry> <tocountry> <num> \t command to Fortify country");
        System.out.println("fortify none \t commad to choose to not do a move");

    }
}




