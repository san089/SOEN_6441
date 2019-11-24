package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.controller.CommandController;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TournamentGameTest {
    private ArrayList<String> mapFiles = new ArrayList<>();
    private ArrayList<String> playerStrategies = new ArrayList<>();
    private int gameId;
    private int numTurns;
    private TournamentGame tournamentGame;
    @Before
    public void setUp() throws Exception {
        mapFiles.add("/home/hanford/Documents/SOEN6441/project/SOEN_6441/Maps/Valid_Maps/BigValidMap.map");
        playerStrategies.add("Cheater");
        playerStrategies.add("Aggressive");
        gameId = 1;
        numTurns = 2;
        tournamentGame = new TournamentGame(mapFiles, playerStrategies, gameId, numTurns);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMapFiles() {
        String expected = "/home/hanford/Documents/SOEN6441/project/SOEN_6441/Maps/Valid_Maps/BigValidMap.map";
        assertEquals(expected, tournamentGame.getMapFiles().get(0));
    }

    @Test
    public void setMapFiles() {
        String mapFile = "test";
        ArrayList<String> mapFiles = new ArrayList<>();
        mapFiles.add(mapFile);
        tournamentGame.setMapFiles(mapFiles);
        assertEquals(mapFile, tournamentGame.getMapFiles().get(0));
    }

    @Test
    public void getPlayerStrategies() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Cheater");
        expected.add("Aggressive");
        assertEquals(expected, tournamentGame.getPlayerStrategies());
    }

    @Test
    public void setPlayerStrategies() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Aggressive");
        expected.add("Cheater");
        tournamentGame.setPlayerStrategies(expected);
        assertEquals(expected, tournamentGame.getPlayerStrategies());
    }

    @Test
    public void getGameId() {
        int expected = 1;
        assertEquals(1, tournamentGame.getGameId());
    }

    @Test
    public void setGameId() {
        int expected = 2;
        tournamentGame.setGameId(expected);
        assertEquals(expected, tournamentGame.getGameId());
    }

    @Test
    public void getNumTurns() {
        int expected = 2;
        assertEquals(expected, tournamentGame.getNumTurns());
    }

    @Test
    public void setNumTurns() {
        int expected = 3;
        tournamentGame.setNumTurns(expected);
        assertEquals(expected, tournamentGame.getNumTurns());
    }

    @Test
    public void runTournament() {
        tournamentGame.run();
        assertEquals(1, tournamentGame.getTournamentResult().size());
    }
}