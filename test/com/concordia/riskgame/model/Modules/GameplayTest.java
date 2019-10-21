package com.concordia.riskgame.model.Modules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Test class for testing GamePlayer class
 */
public class GameplayTest {
    Gameplay gamePlay;
    PrintStream console = null;
    ByteArrayOutputStream bytes = null;
    @Before
    public void setUp() throws Exception {
        gamePlay = Gameplay.getInstance();
        gamePlay.addPlayer("player1");
        bytes = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void removeNonexistantPlayer() {
        String player = "test";
        String expected = "Player not found.";
        assertEquals(expected, gamePlay.removePlayer(player));
    }
    
    @Test
    public void removeExistantPlayer() {
        String player = "player1";
        String expected = "Player player1 removed from the game.";
        assertEquals(expected, gamePlay.removePlayer(player));
    }



    @Test
    public void validateStartupInputs() {
       String expected =  "Please select a valid map for current game.";
        Map map = new Map();
        assertEquals(expected, gamePlay.validateStartupInputs());
        gamePlay.setSelectedMap(map);
        assertNotEquals(expected, gamePlay.validateStartupInputs());

    }
}