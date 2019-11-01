package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.controller.CommandController;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class PlayerTest {

    @Before
    public void setup(){
        Scanner sc = new Scanner(System.in);
        CommandController.parseCommand("loadmap D:\\SOEN_6441\\Maps\\Valid_Maps\\SmallValidMap.map", sc);
        CommandController.parseCommand("gameplayer -add Sanchit -add Sucheta", sc);
        CommandController.parseCommand("populatecountries", sc);
        CommandController.parseCommand("showphases", sc);
    }

    @Test
    public void getNumOfInfCard() {


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
    }

    @Test
    public void getNumOfArtCard() {
    }

    @Test
    public void reinforceArmy() {
    }

    @Test
    public void attack() {
    }

    @Test
    public void checkAvailableAttack() {
    }

    @Test
    public void fortifyArmy() {
    }
}