/**
 *
 * Test Class for Player.
 *
 * @author Sanchit
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */


package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.utilities.Phases;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.*;

public class PlayerTest {
    String path = System.getProperty("user.dir");
    String mapPath = path + "\\Maps\\Valid_Maps\\SmallValidMap.map";
    Scanner sc = new Scanner(System.in);
    Gameplay gameplay;


    @Before
    public void setup(){
        try {
        gameplay = null;
        CommandController.parseCommand("loadmap "+ mapPath, sc);
        CommandController.parseCommand("gameplayer -add Sanchit -add Sucheta", sc);
        CommandController.parseCommand("populatecountries", sc);
        CommandController.parseCommand("showphases", sc);
        }
        catch(Exception ex)
    	{
    		System.out.println("Exception while parsing commands");
    	}

    }

    @Test
    public void testAsequenceExecution(){
        getCountriesOwned();
        reinforceArmy();
        checkAvailableAttack();
        attack();
        fortifyArmy();
        getNumOfArtCard();
        getNumOfCavCard();
        getNumOfInfCard();
    }


    @Test
    public void getNumOfInfCard() {
        try {
            CommandController.parseCommand("placeall", sc);
        }
        catch (Exception e){
            System.out.println("Exception while parsing commands.");
        }
        Gameplay gameplay = Gameplay.getInstance();
        gameplay.getCurrentPlayer().setCardsOwned(new ArrayList<>(Arrays.asList(Card.INFANTRY,Card.INFANTRY)));
        int count = 0;
        for(Card c : gameplay.getCurrentPlayer().getCardsOwned()){
            if(c.equals(Card.INFANTRY)){
                count++;
            }
        }
        assertEquals(gameplay.getCurrentPlayer().getNumOfInfCard(), 2);
    }


    /**
     * This method is to test that the countries assigned to players are equally distributed.
     */
    @Test
    public void getCountriesOwned(){
        int countriesInMap = 0;
        Gameplay gameplay = Gameplay.getInstance();
        List<Continent> continents =  gameplay.getSelectedMap().getContinents();
        List<Player> players = gameplay.getPlayers();
        int totalPlayersInGame = gameplay.getPlayers().size();

        for(Continent c : continents){
            countriesInMap += c.getCountriesPresent().size();
        }

        int maxOwnedCountries = (int) Math.ceil((double) countriesInMap/totalPlayersInGame);
        System.out.println("\n==============RULES==============\nTotal countries in the map is : " + countriesInMap);
        System.out.println("Total players in game : " + players.size());
        System.out.println("Maximun country a player can own : " + maxOwnedCountries);

        for(Player p : players){
            System.out.println("Player : "+ p.getPlayerName() + " owns : " + p.getCountriesOwned().size() + " countries");
            assertTrue(p.getCountriesOwned().size() <= maxOwnedCountries);
        }
    }

    @Test
    public void getNumOfCavCard() {
        try {
            CommandController.parseCommand("placeall", sc);
        }
        catch (Exception e){
            System.out.println("Exception while parsing commands.");
        }
        Gameplay gameplay = Gameplay.getInstance();
        gameplay.getCurrentPlayer().setCardsOwned(new ArrayList<>(Arrays.asList(Card.CAVALRY,Card.CAVALRY, Card.CAVALRY)));
        int count = 0;
        for(Card c : gameplay.getCurrentPlayer().getCardsOwned()){
            if(c.equals(Card.CAVALRY)){
                count++;
            }
        }
        assertEquals(gameplay.getCurrentPlayer().getNumOfCavCard(), 3);
    }

    @Test
    public void getNumOfArtCard() {
        try {
            CommandController.parseCommand("placeall", sc);
        }
        catch (Exception e){
            System.out.println("Exception while parsing commands.");
        }
        Gameplay gameplay = Gameplay.getInstance();
        gameplay.getCurrentPlayer().setCardsOwned(new ArrayList<>(Arrays.asList(Card.ARTILLERY)));
        int count = 0;
        for(Card c : gameplay.getCurrentPlayer().getCardsOwned()){
            if(c.equals(Card.ARTILLERY)){
                count++;
            }
        }
        assertEquals(1, gameplay.getCurrentPlayer().getNumOfArtCard());
    }

    @Test
    public void reinforceArmy() {
        try{
            CommandController.parseCommand("placeall", sc);
            CommandController.parseCommand("exchangecards -none", sc);
            gameplay = Gameplay.getInstance();
            String countryName = gameplay.getCurrentPlayer().getCountriesOwned().get(0);
            int initialArmies = gameplay.getSelectedMap().searchCountry(countryName).getNoOfArmiesPresent();
            int armies = gameplay.getCurrentPlayer().getArmyCount();
            CommandController.parseCommand("reinforce " + countryName + " " + Integer.toString(armies), sc);
            int armiesAfterReinforce = gameplay.getSelectedMap().searchCountry(countryName).getNoOfArmiesPresent();
            assertTrue(initialArmies + armies == armiesAfterReinforce);
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }
    }

    @Test
    public void attack() {
        try{
            CommandController.parseCommand("placeall", sc);
            CommandController.parseCommand("exchangecards -none", sc);
            gameplay = Gameplay.getInstance();
            String countryName = gameplay.getCurrentPlayer().getCountriesOwned().get(0);
            int armies = gameplay.getCurrentPlayer().getArmyCount();
            CommandController.parseCommand("reinforce " + countryName + " " + Integer.toString(armies), sc);
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }

        boolean attackAvailable = false;
        Country country = new Country();
        String neighbor = "";
        gameplay = Gameplay.getInstance();
        for (String countryName : gameplay.getCurrentPlayer().getCountriesOwned()) {
            country = gameplay.getSelectedMap().searchCountry(countryName);
            if (country.getNoOfArmiesPresent() != 1) {
                for (String neighbor1 : country.getListOfNeighbours()) {
                    if (!gameplay.getCurrentPlayer().getCountriesOwned().contains(neighbor1)) {
                        Country neighborCountry = gameplay.getSelectedMap().searchCountry(neighbor1);
                        gameplay.addToViewLogger(countryName + " " + country.getNoOfArmiesPresent() + " → " + neighbor1 + " " + neighborCountry.getNoOfArmiesPresent());
                        neighbor = neighbor1;
                        attackAvailable = true;
                        break;
                    }
                }
                if(attackAvailable == true){
                    break;
                }
            }
        }
        try {
            Player currentPlayer = gameplay.getCurrentPlayer();
            country.setNoOfArmiesPresent(1);
            String attackerCountry = country.getCountryName();
            System.out.println("Neighbour is : " + neighbor);
            System.out.println(gameplay.getSelectedMap().searchCountry(neighbor).getCountryName());
            CommandController.parseCommand("attack " + attackerCountry + ". " + neighbor + " -allout", sc);
            int attackerArmyLeft = gameplay.getSelectedMap().searchCountry(attackerCountry).getNoOfArmiesPresent();
            int defenderArmyLeft = gameplay.getSelectedMap().searchCountry(neighbor).getNoOfArmiesPresent();
            System.out.println(attackerArmyLeft);
            System.out.println(defenderArmyLeft);
            assertTrue(attackerArmyLeft == 1 || defenderArmyLeft <=1);
        }
        catch (Exception e){
            System.out.println("Some exception occured");
        }

    }

    @Test
    public void checkAvailableAttack() {
        try{
            CommandController.parseCommand("placeall", sc);
            CommandController.parseCommand("exchangecards -none", sc);
            gameplay = Gameplay.getInstance();
            String countryName = gameplay.getCurrentPlayer().getCountriesOwned().get(0);
            int armies = gameplay.getCurrentPlayer().getArmyCount();
            CommandController.parseCommand("reinforce " + countryName + " " + Integer.toString(armies), sc);
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }
        assertTrue(gameplay.getCurrentPlayer().checkAvailableAttack());
    }

    @Test
    public void fortifyArmy() {
        gameplay = Gameplay.getInstance();
        try{
            CommandController.parseCommand("placeall", sc);
            CommandController.parseCommand("exchangecards -none", sc);
            gameplay = Gameplay.getInstance();
            String countryName = gameplay.getCurrentPlayer().getCountriesOwned().get(0);
            int armies = gameplay.getCurrentPlayer().getArmyCount();
            CommandController.parseCommand("reinforce " + countryName + " " + Integer.toString(armies), sc);
            CommandController.parseCommand("attack -noattack", sc);
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }


        Player p = gameplay.getCurrentPlayer();
        System.out.println(p.getCountriesOwned() + "@@@@@@@@");
        Country source = gameplay.getSelectedMap().searchCountry(p.getCountriesOwned().get(0));
        Country destination = null;
        int sourceArmies = source.getNoOfArmiesPresent();
        List<String> neighbours = source.getListOfNeighbours();
        System.out.println("Source country : " + source.getCountryName());
        System.out.println("Neighbours of source country :" + neighbours);
        if (neighbours.isEmpty()) {
            try {
                CommandController.parseCommand("fortify -none", sc);
            } catch (Exception e) {
                System.out.println("Fortify -none. Some exception occured");
            }
            assertEquals(source.getNoOfArmiesPresent(), sourceArmies);
        } else {
            for (String c : neighbours) {
                if(p.getCountriesOwned().contains(c)) {
                    destination = gameplay.getSelectedMap().searchCountry(c);
                    if(destination.getCountryName().equals(source.getCountryName())){
                        destination = null;
                    }
                    else{
                        break;
                    }
                }
            }
        }


        try {
            if(destination == null){
                CommandController.parseCommand("fortify -none", sc);
                System.out.println("Destination is null. fortify -none. Some exception occured");
                assertEquals(gameplay.getSelectedMap().searchCountry(source.getCountryName()).getNoOfArmiesPresent(), sourceArmies);
                return;
            }
            int destinationArmies = destination.getNoOfArmiesPresent();
            System.out.println("Source Country : " + source.getCountryName() + " armies before : " + source.getNoOfArmiesPresent());
            System.out.println("Destination country : " + destination.getCountryName()+ " armies before : " + destination.getNoOfArmiesPresent());

            String forifyCommand = "fortify " + source.getCountryName() + " " + destination.getCountryName() + " " + (sourceArmies - 1);
            System.out.println("fortify command : " + forifyCommand);
            CommandController.parseCommand(forifyCommand, sc);

            System.out.println("Source Armies after : " +  gameplay.getSelectedMap().searchCountry(source.getCountryName()).getNoOfArmiesPresent());
            System.out.println("Destination Armies After : " + gameplay.getSelectedMap().searchCountry(destination.getCountryName()).getNoOfArmiesPresent());
            assertTrue(source.getNoOfArmiesPresent() == 1 || source.getNoOfArmiesPresent() == sourceArmies);
            assertTrue(destination.getNoOfArmiesPresent() == 1 || destination.getNoOfArmiesPresent() == destinationArmies);
        } catch (Exception e) {
            System.out.println("Some exception occured.");
        }


    }
}