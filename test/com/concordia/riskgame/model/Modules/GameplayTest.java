package com.concordia.riskgame.model.Modules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class GameplayTest {
    Gameplay gamePlay;
    PrintStream console = null;
    ByteArrayOutputStream bytes = null;
    @Before
    public void setUp() throws Exception {
        gamePlay = Gameplay.getInstance();
        bytes = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void removePlayer() {
        String player = "test";

        String expected = "Player not found.";
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