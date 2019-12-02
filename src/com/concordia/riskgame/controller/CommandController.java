package com.concordia.riskgame.controller;

import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.concordia.riskgame.model.Modules.Card;
import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Gameplay.GameplayBuilder;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.model.Modules.Strategy;
import com.concordia.riskgame.model.Modules.TournamentGame;
import com.concordia.riskgame.model.Modules.Stratigies.Aggressive;
import com.concordia.riskgame.model.Modules.Stratigies.Benevolent;
import com.concordia.riskgame.model.Modules.Stratigies.Cheater;
import com.concordia.riskgame.model.Modules.Stratigies.Human;
import com.concordia.riskgame.model.Modules.Stratigies.Random;
import com.concordia.riskgame.utilities.*;
import com.concordia.riskgame.view.CardExchangeView;
import com.concordia.riskgame.view.MapEditorView;
import com.concordia.riskgame.view.PhaseView;


// TODO: Auto-generated Javadoc
/**
 * Class to parse the commands entered by user and perform actions based on commands
 */
public class CommandController implements Serializable {

    public static String commandType; // command type
    public static HashMap<String, Integer> addContinent = new HashMap<>();
    public static ArrayList<String> removeContinent = new ArrayList<>();
    public static HashMap<String, String> addCountry = new HashMap<>();
    public static ArrayList<String> removeCountry = new ArrayList<>();
    public static HashMap<String, String> addNeighbour = new HashMap<>();
    public static HashMap<String, String> removeNeighbour = new HashMap<>();
    public static ArrayList<String> addPlayer = new ArrayList<>();
    public static ArrayList<String> removePlayer = new ArrayList<>();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private String sSaveFileName="";

   // public static Gameplay gameplay=Gameplay.getInstance();

    public static MapEditorController mapEditor=new MapEditorController(new MapEditorView(new Map()));
    public static DominationMapTools mapTools=new ConquestMapAdapter(new ConquestMapTools());
    private static final long serialVersionUID = 45443434343L;
    public static PhaseView phaseView;

    /**
     * This method takes command as input and calls respective methods corresponding to the command and executes the method.
     *
     * @param command takes command input from user
     */
    public static void parseCommand(String command) {
        command = command.trim().replaceAll(" +", " "); //replace multiple whitespaces with one.
        commandType = command.split(" ")[0];
        //gameplay=Gameplay.getInstance();
        switch (commandType) {
            case "editcontinent":
                editContinent(command);
                break;
            case "editcountry":
                editCountry(command);
                break;
            case "editneighbor":
                editNeighbour(command);
                break;
            case "showmap":
                showMap();
                break;
            case "savemap":
                saveMap(command);
                break;
            case "savegame":
               try {
				saveGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
                break;
            case "loadGame":
                loadGame(command);
                break;
            case "editmap":
                editMap(command);
                break;
            case "validatemap":
                validateMap();
                break;
            case "loadmap":
                loadMap(command);
                break;
            case "gameplayer":
                gamePlayer(command);
                break;
            case "populatecountries":
                populateCountries();
                break;
            case "placearmy":
                placeArmy(command);
                break;
            case "placeall":
                placeAll();
                break;
            case "reinforce":
                reinforce(command);
                break;
            case "exchangecards":
                exchangeCards(command);
                break;
            case "attack":
                attack(command);
                break;
            case "defend":
                defend(command);
                break;
            case "attackmove":
                attackmove(command);
                break;
            case "fortify":
                fortify(command);
                break;
            case "botplay":
                botPlay();
                break;
            case "tournament":
                System.out.println("/////////  TOURNAMENT MODE SELECTED  ///////////////");
                runTournament(command);
                break;
            case "help":
                showHelpOptions();
                break;
            case "exit":
                System.exit(0);
                break;
            case "showphases":
                Gameplay.getInstance().addToViewLogger("Current phase is : " + Gameplay.getInstance().getCurrentPhase());
                break;
            case "closephaseview":
                closePhaseView();
                break;
            default:
                invalidCommandMessage();
                break;
        }
    }

    /**
     *This method parse attack command from command terminal
     * @param command
     */
    private static void attack(String command) {
        if (Gameplay.getInstance().getCurrentPhase() != Phases.Attack) {
            Gameplay.getInstance().addToViewLogger("Now it's not attack phase, you cannot attack");
            return;
        }

        if (command.split(" ").length != 4 && command.split(" ").length != 2) {
            Gameplay.getInstance().addToViewLogger("Incorrect command!");
            return;
        }
        if (command.split(" ").length == 2) {
            if (command.split(" ")[1].equals("-noattack")) {
                Gameplay.getInstance().addToViewLogger("Moving from " + Gameplay.getInstance().getCurrentPhase() + " Phase to Fortification Phase.");
                Gameplay.getInstance().setCurrentPhase(Phases.Fortification);
                return;
            } else {
                Gameplay.getInstance().addToViewLogger("Incorrect command");
                return;
            }
        }

        Gameplay.getInstance().getCurrentPlayer().attack(command);


    }




    /**
     * Verify all characters.
     *
     * @param val Takes a string input
     * @return return true if the string has only characters a-z, else return false.
     */
    public static boolean verifyAllCharacters(String val) {
        return val.matches("^[a-zA-Z]*$");
    }

    /**
     * Verify number.
     *
     * @param val the val
     * @return true, if successful
     */
    public static boolean verifyNumber(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Invalid command message.
     */
    public static void invalidCommandMessage() {
        Gameplay.getInstance().addToViewLogger(ANSI_RED + "INVALID COMMAND !!, Check the command format below. ");
        showHelpOptions();
        Gameplay.getInstance().addToViewLogger(ANSI_RESET);
    }


    /**
     * This method performs add continent and remove continent actions based on the valid command.
     *
     * @param command Command to execute
     */
    public static void editContinent(String command) {
        try {
            if (Gameplay.getInstance().getCurrentPhase() != Phases.MapEditor) {
                Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
                return;
            }
            if (validateEditContinentCommand(command)) {
                for (String Key : addContinent.keySet()) {
                    mapEditor.addContinentService(Key, addContinent.get(Key), false);//Add continent functiono call.
                }
                for (String val : removeContinent) {
                    mapEditor.removeContinentService(val, false);//Add continent functiono call.
                    //Remove continent function call.
                }
            } else {
                invalidCommandMessage();
            }
        } catch (Exception e) {
            System.out.println("Some exception occured.");
        }
    }
    /**
     * This method reads editcontinent command, validates the command and add continent to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     *
     * @throws  IOException when the command entered is invalid.
     */
    public static boolean validateEditContinentCommand(String command) throws IOException {

        addContinent.clear();
        removeContinent.clear();

        String[] args = command.split(" ");
        String arg_type;
        String value1;
        String value2;
        try {
            for (int i = 1; i < args.length; i += 2) {
                arg_type = args[i];
                if (arg_type.trim().equals("-add")) {
                    value1 = args[i + 1];
                    value2 = args[i + 2];
                    if (verifyAllCharacters(value1) && verifyNumber(value2)) {
                        addContinent.put(value1, Integer.parseInt(value2));
                    }
                    i += 1;
                } else if (arg_type.trim().equals("-remove")) {
                    value1 = args[i + 1];
                    if (verifyAllCharacters(value1)) {
                        removeContinent.add(value1);
                    }
                } else {
                    return false;
                }
            }
        }catch (Exception e){
            Gameplay.getInstance().addToViewLogger("Some execption occured while parsing command.");
            return false;
        }
        return true;
    }

    /**
     * This method takes editcountry command as input and perform actions as per command.
     *
     * @param command command to execute
     */
    public static void editCountry(String command) {
        try {
            if(Gameplay.getInstance().getCurrentPhase() != Phases.MapEditor){
                Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
                return;
            }
            if (validateEditCountryCommand(command)) {
                for (String countryName : addCountry.keySet()) {
                    mapEditor.addCountriesService(addCountry.get(countryName), countryName,false);
                }
                for (String countryName : removeCountry) {
                    mapEditor.removeCountryService(new Continent(), countryName,false);
                }
            } else {
                invalidCommandMessage();
            }
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }

    }

    /**
     * This method reads editcountry command, validates the command and add country to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     *
     * @throws  IOException when the command entered is invalid.
     */
    public static boolean validateEditCountryCommand(String command) throws  IOException{
        addContinent.clear();
        removeCountry.clear();
        addCountry.clear();


        String[] args = command.split(" ");
        String arg_type;
        String value1;
        String value2;
        try {
            for (int i = 1; i < args.length; i += 2) {
                arg_type = args[i];
                if (arg_type.trim().equals("-add")) {
                    value1 = args[i + 1];
                    value2 = args[i + 2];
                    if (verifyAllCharacters(value1) && verifyAllCharacters(value2)) {
                        addCountry.put(value1, value2);
                    }
                    i += 1;
                } else if (arg_type.trim().equals("-remove")) {
                    value1 = args[i + 1];
                    if (verifyAllCharacters(value1)) {
                        removeCountry.add(value1);
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e){
            Gameplay.getInstance().addToViewLogger("Some execption occured while parsing command.");
            return false;
        }
        return true;
    }

    /**
     * This method takes editneighbour command as input and perform actions as per command.
     *
     * @param command command to execute
     */
    public static void editNeighbour(String command) {
        try {
            if (Gameplay.getInstance().getCurrentPhase() != Phases.MapEditor) {
                Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
                return;
            }
            if (validateEditNeighbourCommand(command)) {
                for (String countryName : addNeighbour.keySet()) {
                    String neighbourName = addNeighbour.get(countryName);
                    mapEditor.addNeighbourService(countryName, neighbourName);
                }
                for (String countryName : removeNeighbour.keySet()) {
                    String neighbourName = removeNeighbour.get(countryName);
                    mapEditor.removeNeighbourService(countryName, neighbourName);
                }
            } else {
                invalidCommandMessage();
            }
        } catch (Exception e) {
            System.out.println("Some exception Occured.");
        }
    }

    /**
     * This method reads editneighbour command, validates the command and add neighbour to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     *
     * @throws  IOException when the command entered is invalid.
     */
    public static boolean validateEditNeighbourCommand(String command) throws IOException{
        addNeighbour.clear();
        removeNeighbour.clear();

        String[] args = command.split(" ");
        String arg_type;
        String value1;
        String value2;
        try{
            for (int i = 1; i < args.length; i += 3) {
                arg_type = args[i];
                value1 = args[i + 1];
                value2 = args[i + 2];
                if (verifyAllCharacters(value1) && verifyAllCharacters(value2)) {
                    if (arg_type.trim().equals("-add")) {
                        addNeighbour.put(value1, value2);
                    } else if (arg_type.trim().equals("-remove")) {
                        removeNeighbour.put(value1, value2);
                    } else {
                        return false;
                    }
                }
            }
        }
        catch (Exception e){
            Gameplay.getInstance().addToViewLogger("Some execption occured while parsing command.");
            return false;
        }
        return true;
    }


    /**
     * This method reads Gameplay.getInstance()er command, validates the command and add or remove player.
     *
     * @param command Command to validate
     *
     */
    public static void gamePlayer(String command) {
        try {

            if(Gameplay.getInstance().getCurrentPhase() != Phases.Startup){
                Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
                return;
            }
            if (validateGamePlayerCommand(command)) {
                Gameplay.getInstance().addToViewLogger("");
            } else {
                invalidCommandMessage();
            }
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }
    }


    /**
     * This method add or remove player based on the type of operation user wants to perform.
     *
     * @param operation Type of Operation, either add or remove
     * @param playername The name of the player to be added or removed.
     * @param playerstrategy The playerstrategy.
     */
    public static void gamePlayerAddRemove(String operation, String playername, String playerstrategy){
        if(operation.trim().equals("-add"))
            Gameplay.getInstance().addPlayer(playername, createPlayerStrategy(playerstrategy));
        else if(operation.trim().equals("-remove"))
           Gameplay.getInstance().removePlayer(playername);
        else
            Gameplay.getInstance().addToViewLogger("Invalid Operation");

    }

    /**
     * This method reads gamplayer command, validates the command and add player to add or remove to list.
     *
     * @param command Command to validate
     * @return True if the command is valid, else false.
     *
     * @throws  IOException when the command entered is invalid.
     */
    public static boolean validateGamePlayerCommand(String command) throws IOException {
        String[] args = command.split(" ");
        String arg_type;
        String value1;
        String value2;
        try {
            for (int i = 1; i < args.length; i += 2) {
                arg_type = args[i];
                if(arg_type.trim().equals("-add")){
                    value1 = args[i + 1];
                    value2 = args[i + 2];
                    if(verifyAllCharacters(value1) && verifyAllCharacters(value2)){
                        gamePlayerAddRemove(arg_type, value1, value2);
                        i+=1;
                    }
                    else{
                        Gameplay.getInstance().addToViewLogger("Some execption occured while parsing command.");
                        System.out.println("Error in gameplayer command");
                        return false;
                    }
                }
                else if(arg_type.trim().equals("-remove")){
                    value1 = args[i + 1];
                    if (verifyAllCharacters(value1))
                        gamePlayerAddRemove(arg_type, value1, "");
                    else{
                        Gameplay.getInstance().addToViewLogger("Some execption occured while parsing command.");
                        System.out.println("Error in Gameplay.getInstance()er command");
                        return false;
                    }
                }
                else {
                    Gameplay.getInstance().addToViewLogger("Some execption occured while parsing command.");
                    System.out.println("Error in gameplayer command");
                    return false;
                }
            }
        }
        catch (Exception e){
        	e.printStackTrace();
            Gameplay.getInstance().addToViewLogger("Some execption occured while parsing command.");
            return false;
        }
        return true;
    }


    /**
     * This method validates if the map loaded is.
     */
    public static void validateMap() {
        if(Gameplay.getInstance().getCurrentPhase() != Phases.MapEditor){
            Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
            return;
        }
    	mapTools.validateMap(mapEditor.getGameMap(), 2);
    }

    /**
     * Method to display map to the players.
     */
    public static void showMap() {
    	if(Gameplay.getInstance().getCurrentPhase()==Phases.MapEditor)
    		mapEditor.showMapService(mapEditor.getGameMap());
    	else
    		mapEditor.showMapService(Gameplay.getInstance().getSelectedMap());
    }


    /**
     * Save map.
     *
     * @param command the command
     */
    public static void saveMap(String command)
    {
        if(Gameplay.getInstance().getCurrentPhase() != Phases.MapEditor){
            Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
            return;
        }
        String fileName = command.split(" ")[1];
        mapEditor.saveMapService(fileName);


    }

    public static void loadGame(String command){
        String fileName = Constants.saveLocation+command.split(" ")[1];
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(fileName);
            ObjectInputStream os = new ObjectInputStream(fs);
            Gameplay gameModel = (Gameplay) os.readObject();
            new Gameplay.GameplayBuilder(gameModel.getPlayers(),gameModel.getSelectedMap(),gameModel.getCurrentPhase(),gameModel.getViewLogger()).setcurrentPlayer(gameModel.getCurrentPlayer()).setgameMode(gameModel.getGameMode()).setplayerCount().setplayerQueue(gameModel.getPlayerQueue())
            .setremovedPlayer(gameModel.getRemovedPlayer()).build();      
                  
            phaseView = new  PhaseView();
            Gameplay.getInstance().addToViewLogger("Game Loaded Succesfully");
            os.close();
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * Edits the map.
     *
     * @param command the command
     */
    public static void editMap(String command)
    {
        if(Gameplay.getInstance().getCurrentPhase() != Phases.MapEditor){
            Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
            return;
        }
        String fileName = command.split(" ")[1]; 
        Map gameMap=new Map();
        if(mapTools.pickMapFileService(gameMap,fileName))
        	mapEditor.setGameMap(gameMap);
    }


    /**
     * This method will load the map from the file specified by user in the command.
     *
     * @param command loadmap command as input
     */
    public static void loadMap(String command) {
        String fileName = command.split(" ")[1];
        Map selectedMap = new Map();
        if (mapTools.pickMapFileService(selectedMap, fileName)) {
            Gameplay.getInstance().setSelectedMap(selectedMap);
            Gameplay.getInstance().setCurrentPhase(Phases.Startup);
            Gameplay.getInstance().addToViewLogger("Switched to " + Phases.Startup + " Phase.");
            if (phaseView != null) {
                phaseView.setVisible(false);
            }
            phaseView = new  PhaseView();
        	
        } else {
            Gameplay.getInstance().addToViewLogger("The selected Map is invalid.Please select another map.Reason for Invalidity :" + selectedMap.getErrorMessage());
        }
    }

    /**
     * Method to populate countries and assign country to players.
     */
    public static void populateCountries()
    {
        if(Gameplay.getInstance().getCurrentPhase() != Phases.Startup){
            Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot perform action");
            return;
        }
    	String message=Gameplay.getInstance().validateStartupInputs();
		if(!message.contentEquals("Success"))
			Gameplay.getInstance().addToViewLogger(message);
		else
			{
			Gameplay.getInstance().initialisePlayers();
			}

    }

    /**
     * Place army.
     *
     * @param command the command
     *
     */
    public static void placeArmy(String command) {
    	Scanner in = ScannerUtil.sc;
    	int armyCount = 0;
        String countryName = command.split(" ")[1];   
        Gameplay.getInstance().addToViewLogger("Enter the number armies to be placed ");
        boolean loop=true;
        while(loop) {
        try {
        	armyCount=in.nextInt();
        }
        catch(InputMismatchException ex) {
        	Gameplay.getInstance().addToViewLogger("Please enter a number");
        }
        
        if(!(Gameplay.getInstance().getCurrentPlayer().getArmyCount()>=armyCount) )
        	Gameplay.getInstance().addToViewLogger("Entered count more than the number of armies available for the current player.Please enter a different value.");      
        
        else if(Gameplay.getInstance().getAbandonedCountryCount()>Gameplay.getInstance().getCurrentPlayer().getArmyCount()-armyCount)
        	Gameplay.getInstance().addToViewLogger("There are not enough armies to be deploy "+armyCount+" in one country .Please place such that every country has at least one army");
	    else
        	loop=false;
        
        }   	
           	
        if(Gameplay.getInstance().placeArmy(countryName,armyCount,true)) {
        	if(Gameplay.getInstance().getCurrentPlayer().getArmyCount()>0)
        		Gameplay.getInstance().getPlayerQueue().add(Gameplay.getInstance().getPlayerQueue().remove());
        	Gameplay.getInstance().setCurrentPlayer(Gameplay.getInstance().getPlayerQueue().element());
        	Gameplay.getInstance().addToViewLogger("PLAYER TURN : Place army for "+Gameplay.getInstance().getCurrentPlayer().getPlayerName()+". Number of remaining armies "+Gameplay.getInstance().getCurrentPlayer().getArmyCount());
    		
        }

        // after every place army, check if all the reinforcement are 0, if so, change to reinforcement phase.
        ArrayList<Player> players = Gameplay.getInstance().getPlayers();
        int totalNumOfReinforce = 0;
        for (Player player : players) {
            totalNumOfReinforce = totalNumOfReinforce +player.getArmyCount();
        }

        if (totalNumOfReinforce == 0) {
            Gameplay.getInstance().getPlayerQueue().clear();
            Gameplay.getInstance().getPlayerQueue().addAll(players);
            //Gameplay.getInstance().assignReinforcementArmies();
            Gameplay.getInstance().addToViewLogger("Moving from "+ Gameplay.getInstance().getCurrentPhase() +" Phase to Reinforcement Phase.");
            Gameplay.getInstance().setCurrentPhase(Phases.Reinforcement);
            Gameplay.getInstance().roundRobinPlayer();
            Gameplay.getInstance().addObserver(CardExchangeView.getInstance());
            Gameplay.getInstance().addToViewLogger("Now it's " + Gameplay.getInstance().getCurrentPlayer().getPlayerName() + "'s reinforce phase. Exchange your" +
                    " card first or exchange -none");
            if (!Gameplay.getInstance().getCurrentPlayer().getStrategy().getStrategyName().equals("Human") && Gameplay.getInstance().getGameMode().equals("Single")) {
                parseCommand("botplay");
            }
        }

    }


    /**
     * Place the armies in round robin fashion for all players.
     *
     */
    public static void placeAll() {
    	Gameplay.getInstance().placeAllArmies();

        ArrayList<Player> players = Gameplay.getInstance().getPlayers();
        //after placeall, game play starts, initialize player queue
        Gameplay.getInstance().getPlayerQueue().clear();
        Gameplay.getInstance().getPlayerQueue().addAll(players);
        //gameplay.assignReinforcementArmies();

        Gameplay.getInstance().addToViewLogger("Moving from "+ Gameplay.getInstance().getCurrentPhase() +" Phase to Reinforcement Phase.");
        Gameplay.getInstance().setCurrentPhase(Phases.Reinforcement);
        Gameplay.getInstance().displayArmyDistribution();
        //start round robin play
        Gameplay.getInstance().roundRobinPlayer();
        Gameplay.getInstance().addObserver(CardExchangeView.getInstance());
        Gameplay.getInstance().addToViewLogger("Now it's " + Gameplay.getInstance().getCurrentPlayer().getPlayerName() + "'s reinforce phase." +
                "Please exchange cards first or exchange none");
        Gameplay.getInstance().triggerObserver("domination");
        if (!Gameplay.getInstance().getCurrentPlayer().getStrategy().getStrategyName().equals("Human") && Gameplay.getInstance().getGameMode().equals("Single")) {
            parseCommand("botplay");
        }
    }


    /**
     * Reinforce.
     *
     * @param command the command
     *
     */
    public static void reinforce(String command)
    {
        try {
            if(Gameplay.getInstance().getCurrentPhase() == Phases.Reinforcement){
                if (Gameplay.getInstance().getCurrentPlayer().getCardsOwned().size() >= 5) {
                    Gameplay.getInstance().addToViewLogger("You have too many cards, you must exchange");
                    return;
                }
                if (command.split(" ").length != 3) {
                    Gameplay.getInstance().addToViewLogger("Incorrect command!");
                    return;
                }

                String countryName = command.split(" ")[1];
                String num = command.split(" ")[2];
                if (!verifyNumber(num)) {
                    Gameplay.getInstance().addToViewLogger("Not an integer!");
                    return;
                }
                if (Integer.parseInt(num)<=0){
                    Gameplay.getInstance().addToViewLogger("Invalid Number");
                    return;
                }
                Gameplay.getInstance().addToViewLogger("Reinforce " + num + " armies in " + countryName);

                Gameplay.getInstance().getCurrentPlayer().reinforceArmy(command);
                Gameplay.getInstance().addToViewLogger("You still have " + Gameplay.getInstance().getCurrentPlayer().getArmyCount() + " armies");
                if (Gameplay.getInstance().getCurrentPlayer().getArmyCount() <= 0) {
                    Gameplay.getInstance().addToViewLogger("Moving from "+ Gameplay.getInstance().getCurrentPhase() +" Phase to Attack Phase.");
                    Gameplay.getInstance().setCurrentPhase(Phases.Attack);
                    if(!Gameplay.getInstance().getCurrentPlayer().checkAvailableAttack()){
                        Gameplay.getInstance().addToViewLogger("Moving from "+ Gameplay.getInstance().getCurrentPhase() +" Phase to Fortification Phase.");
                        Gameplay.getInstance().setCurrentPhase(Phases.Reinforcement);
                    }
                }
            }else{
                Gameplay.getInstance().addToViewLogger("Current Phase is " + Gameplay.getInstance().getCurrentPhase() + ". Cannot move to " + Phases.Reinforcement + " phase.");
            }
            Gameplay.getInstance().triggerObserver("domination");
            Gameplay.getInstance().triggerObserver("showmap");
        }catch (Exception e){
            Gameplay.getInstance().addToViewLogger("Some exception occurred in reinforcement command.");
            showHelpOptions();
        }
    }

    /**
     * This method perform attack commands acceptance, call Player's playing methods.
     *
     * @param command exchange commands user input.
     * @throws IOException Card view file loading exception
     */


    private static void exchangeCards(String command){
        if (Gameplay.getInstance().getCurrentPhase() != Phases.Reinforcement) {
            Gameplay.getInstance().addToViewLogger("Now is not reinforcement phase!");
            return;
        }
        CardExchangeController cardExchangeController = new CardExchangeController();
        cardExchangeController.addObserver(CardExchangeView.getInstance());
        String[] commands = command.split(" ");
        if (commands.length != 4 && commands.length !=2) {
            Gameplay.getInstance().addToViewLogger("Exchange command incorrect!");
            return;
        }
        if (commands.length == 2) {
            if (commands[1].equals("-none")) {
                if (Gameplay.getInstance().getCurrentPlayer().getCardsOwned().size() >= 5) {
                    Gameplay.getInstance().addToViewLogger("You have too many cards, you must exchange");
                    return;
                } else {
                    CardExchangeView.getInstance().setVisible(false);
                    Gameplay.getInstance().assignReinforcementArmies();
                    Gameplay.getInstance().addToViewLogger("You still have " + Gameplay.getInstance().getCurrentPlayer().getArmyCount() + " armies!");
                    return;
                }
            } else {
                Gameplay.getInstance().addToViewLogger("Incorrect command");
                return;
            }
        }
        if(!cardExchangeController.checkInput(commands[1], commands[2], commands[3])) {
            Gameplay.getInstance().addToViewLogger("Number Invalid!");
            return;
        }
        cardExchangeController.exchange();
        Gameplay.getInstance().assignReinforcementArmies();
        Gameplay.getInstance().addToViewLogger("You still have " + Gameplay.getInstance().getCurrentPlayer().getArmyCount() + " armies!" );
    }


    /**
     * Fortify army from one country to another.
     *
     * @param command the command
     */
    public static void fortify(String command)
    {
        if (Gameplay.getInstance().getCurrentPhase() != Phases.Fortification) {
            Gameplay.getInstance().addToViewLogger("Now is " + Gameplay.getInstance().getCurrentPhase() + " phase, cannot do fortification");
            return;
        }
        try {
            String[] commands = command.split(" ");
            if (!commands[1].equals("-none")) {
                if (!Gameplay.getInstance().getCurrentPlayer().fortifyArmy(command)) {
                    return;
                };
            }
            if (Gameplay.getInstance().getCurrentPlayer().getCardFlag()) {
                if (Card.getNumOfCards() >=42) {
                    Gameplay.getInstance().addToViewLogger("There is no more cards on the table");
                    Gameplay.getInstance().getCurrentPlayer().resetCardFlag();
                } else {
                    Card newCard = Card.getCard(Card.class);
                    Gameplay.getInstance().getCurrentPlayer().addNewCard(newCard);
                    Gameplay.getInstance().getCurrentPlayer().resetCardFlag();
                    Gameplay.getInstance().addToViewLogger("You have got a card: " + newCard);
                }
            }
            //check if the top of player is out, if it is, remove it
            while (Gameplay.getInstance().getRemovedPlayer().contains(Gameplay.getInstance().getPlayerQueue().peek())) {
                Gameplay.getInstance().getPlayerQueue().remove();
            }

            System.out.println(ANSI_RESET);

            Gameplay.getInstance().roundRobinPlayer();
            Gameplay.getInstance().addToViewLogger("Moving from "+ Gameplay.getInstance().getCurrentPhase() +" Phase to Reinforcement Phase.");
            Gameplay.getInstance().setCurrentPhase(Phases.Reinforcement);
            CardExchangeView.getInstance().setVisible(true);
            Gameplay.getInstance().addToViewLogger("Now it's " + Gameplay.getInstance().getCurrentPlayer().getPlayerName() + "'s turn! Player Strategy is : " + Gameplay.getInstance().getCurrentPlayer().getStrategy().getStrategyName());
            if (!Gameplay.getInstance().getCurrentPlayer().getStrategy().getStrategyName().equals("Human") && Gameplay.getInstance().getGameMode().equals("Single")) {
                parseCommand("botplay");
            }
        }catch (Exception e){
            Gameplay.getInstance().addToViewLogger("Some exception occurred");
        }

    }

    public static void botPlay() {
        if(!Gameplay.getInstance().getCurrentPlayer().getStrategy().getStrategyName().equals("Human")) {
            Gameplay.getInstance().simulateBotPlay();
        }
        else{
            System.out.println("Current player is not a bot. Please enter valid command.");
        }
    }

    public static boolean validateTournamentCommand(String command){
        String[] args = command.split(" ");

        if(args.length != 9){
            System.out.println("Some error in arguments of tournament command. Please check if all the arguments are provided");
            return false;
        }

        try {
            if (args[1].equals("-M") && args[3].equals("-P") && args[5].equals("-G") && args[7].equals("-D") ){

                //validate mapfile input, delimited for mapfiles input is pipe (|}
                if(args[2].split("|").length <= 0){
                    System.out.println("Invalid number of map files");
                    return false;
                }

                //validate player strategies
                if(args[4].split("|").length <=0 ){
                    System.out.println("Invalid number of player strategies");
                    return false;
                }

                //validate number of games
                if( (!verifyNumber(args[6])) && (Integer.parseInt(args[6]) <= 0) ){
                    System.out.println("Invalid number of games");
                    return false;
                }

                //validate number of turns
                if( (!verifyNumber(args[8])) && (Integer.parseInt(args[8]) <= 0) ){
                    System.out.println("Invalid number of turns");
                    return false;
                }

            }
            else {
                return false;
            }
        }
        catch (Exception e){
            System.out.println("Some exception occured while parsing tournament command.");
            return false;
        }

        return true;
    }

    public static void runTournament(String Command){
        if (!Gameplay.getInstance().getCurrentPhase().equals(Phases.Startup)){
            Gameplay.getInstance().addToViewLogger("Not Startup Phase, cannot do tournament!");
            return;
        }
        if(validateTournamentCommand(Command)){
            String[] args = Command.split(" ");

            ArrayList<String> mapFiles = new ArrayList<>();
            mapFiles.addAll(Arrays.asList(args[2].split("\\|")));

            ArrayList<String> strategyList = new ArrayList<>();
            strategyList.addAll(Arrays.asList(args[4].split("\\|")));

            int numberOfGames = Integer.parseInt(args[6]);
            int numberOfTurns = Integer.parseInt(args[8]);


            TournamentGame t1 = new TournamentGame(mapFiles, strategyList, numberOfGames, numberOfTurns);
            t1.run();

        }
    }

    public static void closePhaseView(){
        System.out.println("\nClosing Phase View.");
        for(Frame F : Frame.getFrames()){
            F.dispose();
        }
    }


    public static Strategy createPlayerStrategy(String strategyName){
        switch (strategyName.toLowerCase()){
            case "human":
                return new Human();
            case "random":
                return new Random();
            case "aggressive":
                return new Aggressive();
            case "cheater":
                return new Cheater();
            case "benevolent":
                return new Benevolent();
            default:
                return new Human();
        }

    }
    
    /**
     * Function to save game.
     *
     * @throws FileNotFoundException the file not found exception
     * @throws IOException Signals that an I/O exception has occurred.
     */


	public static void saveGame()
			throws FileNotFoundException, IOException {
	//	Scanner in=new Scanner(System.in); 
		Gameplay.getInstance().addToViewLogger("Enter the save file name");
		String saveFilename=ScannerUtil.sc.nextLine();
		
		
		FileOutputStream fs = new FileOutputStream(Constants.saveLocation + saveFilename + ".bin");
		ObjectOutputStream os = new ObjectOutputStream(fs);
		try {
            os.writeObject(Gameplay.getInstance());
            Gameplay.getInstance().addToViewLogger("Game Saved in Saved_Games folder");
		}

		catch(NotSerializableException nse) {
			System.out.println(nse.toString());
		}

		os.flush();
		fs.close();
	}


    private static void attackmove(String command) {
        Gameplay.getInstance().getCurrentPlayer().attackMove(command);
    }

    private static void defend(String command) {
        Gameplay.getInstance().getCurrentPlayer().defendCommand(command);
    }

    /**
     * Method to print help options for commands.
     */
    public static void showHelpOptions()
    {
        Gameplay.getInstance().addToViewLogger("For getting help menu, type help.\n");
        System.out.format("%-20s%-50s%-50s\n","editcontinent", "[-add] <continentname> <continentvalue>", " command to add continent to a map." );
        System.out.format("%-20s%-50s%-50s\n","editcontinent", "[-remove] <continentname> ", " command to remove continent from a map." );
        System.out.format("%-20s%-50s%-50s\n", "editcountry", "[-add] <countryname> <continentname>", " command to add country to a map.");
        System.out.format("%-20s%-50s%-50s\n", "editcountry", "[-remove] <countryname> ", " command to remove country from a map.");
        System.out.format("%-20s%-50s%-50s\n", "editneighbour", "[-add] <countryname> <neighbourcountryname>", " command to add neighbour country to a map.");
        System.out.format("%-20s%-50s%-50s\n", "editneighbour", "[-remove] <countryname> ", " command to remove neighbour country from a map.");
        System.out.format("%-20s%-50s%-50s\n", "showmap", " ", " command to display map.");
        System.out.format("%-20s%-50s%-50s\n", "savemap", "<filename>", " command to save map.");
        System.out.format("%-20s%-50s%-50s\n", "editmap", "<filename> ", " command to load and edit map.");
        System.out.format("%-20s%-50s%-50s\n", "validatemap", " ", " command to validate loaded map.");
        System.out.format("%-20s%-50s%-50s\n", "loadmap", "<filename> ", " command to load map.");
        System.out.format("%-20s%-50s%-50s\n", "gameplayer", "[-add] <playername> ", " command to add player to game.");
        System.out.format("%-20s%-50s%-50s\n", "gameplayer", "[-remove] <playername> ", " command to remove player from game.");
        System.out.format("%-20s%-50s%-50s\n", "populatecountries", " ", " command initialize game and assign country to players.");
        System.out.format("%-20s%-50s%-50s\n", "placearmy", "<countryname> ", " command to place army for a player in a country.");
        System.out.format("%-20s%-50s%-50s\n", "placeall", " ", " automatically randomly place all remaining unplaced armies for all players.");
        System.out.format("%-20s%-50s%-50s\n", "exchangecards", "<num> <num> <num> ", " specify number of cards to exchange for each card type.");
        System.out.format("%-20s%-50s%-50s\n", "exchangecards", "[-none] ", " to skip exchange cards.");
        System.out.format("%-20s%-50s%-50s\n", "reinforce", "<countryname> <num> ", " reinforce number of armies into the given country until reinforce army is 0.");
        System.out.format("%-20s%-50s%-50s\n", "attack", "<fromcountryname> <tocountryname> <numdice> ", " attack from country to another country.");
        System.out.format("%-20s%-50s%-50s\n", "attack", "<fromcountryname> <tocountryname> [-allout] ", " attack from country to another country with max dices until conquered or army is 1.");
        System.out.format("%-20s%-50s%-50s\n", "attack", " [-noattack] ", " skip the attack phase.");
        System.out.format("%-20s%-50s%-50s\n", "defend", " <num> ", " specify number of dice for the defender.");
        System.out.format("%-20s%-50s%-50s\n", "attackmove", " <num> ", " If country conquered during attack, move armies to the country won.");
        System.out.format("%-20s%-50s%-50s\n", "fortify", "<fromcountry> <tocountry> <num> ", " command to Fortify country");
        System.out.format("%-20s%-50s%-50s\n", "fortify", "none ", " commad to choose to not do a move");
        System.out.format("%-20s%-50s%-50s\n", "showphases", "none ", " command to show current running phase.");
        System.out.format("%-20s%-50s%-50s\n", "exit", "none ", " command to stop program execution at any point in time.");
    }
}




