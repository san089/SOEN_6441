package com.concordia.riskgame.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommandControllerTest {

    @Test
    public void parseCommand() {
        String command = "help";
        CommandController.parseCommand(command);


    }

    @Test
    public void editContinent() {
        String command = "editcontinent -add    asia 7 -remove   asa";
        String invalidCommand = "editcontinent -add abcd -remove asd";
        CommandController.parseCommand(command);
        CommandController.parseCommand(invalidCommand);
    }

    @Test
    public void editCountry() {
    }

    @Test
    public void editNeighbour() {
    }

    @Test
    public void gamePlayer() {
        String command = "gameplayer -add sanchit";
        CommandController.parseCommand(command);
        String invalidCommand = "gameplayer -add sanchit";
        CommandController.parseCommand(invalidCommand);
    }
}