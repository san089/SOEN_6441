package com.concordia.riskgame.controller;

import org.junit.Test;

import static org.junit.Assert.*;

// TODO: Auto-generated Javadoc
public class CommandControllerTest {

    /**
     * Parses the command.
     */
    @Test
    public void parseCommand() {
        String command = "help";
        CommandController.parseCommand(command);


    }

    /**
     * Edits the continent.
     */
    @Test
    public void editContinent() {
        String command = "editcontinent -add    asia 7 -remove   asa";
        String invalidCommand = "editcontinent -add abcd -remove asd";
        CommandController.parseCommand(command);
        CommandController.parseCommand(invalidCommand);
    }

    /**
     * Edits the country.
     */
    @Test
    public void editCountry() {
    }

    /**
     * Edits the neighbour.
     */
    @Test
    public void editNeighbour() {
    }

    /**
     * Game player.
     */
    @Test
    public void gamePlayer() {
        String command = "gameplayer -add sanchit";
        CommandController.parseCommand(command);
        String invalidCommand = "gameplayer -add sanchit";
        CommandController.parseCommand(invalidCommand);
    }
}