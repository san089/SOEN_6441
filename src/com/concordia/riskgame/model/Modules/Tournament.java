package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.List;

public class Tournament extends Thread{
    private ArrayList<String> mapFiles;
    private ArrayList<String> playerStrategies;
    private int gameId;
    private int numTurns;

    public Tournament(ArrayList<String> mapFiles, ArrayList<String> playerStrategies, int gameId, int numTurns){
        this.mapFiles = mapFiles;
        this.playerStrategies = playerStrategies;
        this.gameId = gameId;
        this.numTurns = numTurns;
    }

    public ArrayList<String> getMapFiles() {
        return mapFiles;
    }

    public void setMapFiles(ArrayList<String> mapFiles) {
        this.mapFiles = mapFiles;
    }

    public ArrayList<String> getPlayerStrategies() {
        return playerStrategies;
    }

    public void setPlayerStrategies(ArrayList<String> playerStrategies) {
        this.playerStrategies = playerStrategies;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

    public void run(){
        runTournament();
    }

    public void runTournament(){
        System.out.println("Starting tournament");
    }
}
