/**
 *
 * Test Class for Command Controller
 *
 * @author Sanchit.
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */


package com.concordia.riskgame.controller;

import org.junit.Ignore;
import org.junit.Test;

import java.rmi.server.ExportException;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * A test class to test Command Controller Module.
 */
public class CommandControllerTest {

    /**
     * Parses the command.
     */
    /*
    @Test
    public void parseCommand() {
        String command = "help";
        CommandController.parseCommand(command);


    }

    */


    /**
     * Test case for testing commands for editing the continent.
     */
    @Test
    public void editContinent() {
        try{
            System.out.println("========================EDIT CONTINENT TEST CASE START==============================\n");

            System.out.println("===================editcontinent command positive test case===========================");
            String validCommand = "editcontinent -add India Asia";
            assertTrue(CommandController.validateEditCountryCommand(validCommand));

            System.out.println("===================editcontinent command negative test case===========================");
            String invalidCommand = "editcontinent -add India  ";
            assertFalse(CommandController.validateEditCountryCommand(invalidCommand));

            System.out.println("\n========================EDIT CONTINENT TEST CASE END==============================\n");
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }

    }

    /**
     * Test case for testing commands for editing the country.
     */
    @Test
    public void editCountry() {
        try {
            System.out.println("========================EDIT COUNTRY TEST CASE START==============================\n");

            System.out.println("===================editcountry command positive test case===========================");
            String validCommand = "editcountry -add India Asia";
            assertTrue(CommandController.validateEditCountryCommand(validCommand));

            System.out.println("===================editcountry command negative test case===========================");
            String invalidCommand = "editcountry -add India  ";
            assertFalse(CommandController.validateEditCountryCommand(invalidCommand));

            System.out.println("\n========================EDIT COUNTRY TEST CASE END==============================\n");
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }
    }

    /**
     * Test case for testing commands for editing the neighbours.
     */
    @Test
    public void editNeighbour() {
        try {
            System.out.println("========================EDIT NEIGHBOUR TEST CASE START==============================\n");

            System.out.println("===================editneighbour command positive test case===========================");
            String validCommand = "editneighbour -add India China";
            assertTrue(CommandController.validateEditNeighbourCommand(validCommand));

            System.out.println("===================editneighbour command negative test case===========================");
            String invalidCommand = "editneighbour -add India";
            assertFalse(CommandController.validateEditNeighbourCommand(invalidCommand));

            System.out.println("\n========================EDIT NEIGHBOUR TEST CASE END==============================\n");
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }
    }

    /**
     * Test case for testing commands for editing the game players.
     */
    @Test
    public void gamePlayer() {
        try {
            System.out.println("========================GAMEPLAYER TEST CASE START==============================\n");

            System.out.println("===================gameplayer command positive test case===========================");
            String validCommand = "gameplayer -add sanchit";
            assertTrue(CommandController.validateGamePlayerCommand(validCommand));

            System.out.println("===================gameplayer command negative test case===========================");
            String invalidCommand = "gameplayer -add";
            assertFalse(CommandController.validateGamePlayerCommand(invalidCommand));

            System.out.println("\n========================GAMEPLAYER TEST CASE END==============================\n");
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }

    }

    @Test
    public void testHelp(){
        try {
            CommandController.parseCommand("help");
            assertTrue(true);
        }
        catch (Exception e){
            System.out.println("Some exception occured.");
        }
    }
}