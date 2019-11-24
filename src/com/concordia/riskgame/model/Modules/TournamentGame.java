package com.concordia.riskgame.model.Modules;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.utilities.MapTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TournamentGame extends Thread{
    private ArrayList<String> mapFiles;
    private ArrayList<String> playerStrategies;
    private int gameId;
    private int numTurns;
    private ArrayList<String> tournamentResult;


    public TournamentGame(ArrayList<String> mapFiles, ArrayList<String> playerStrategies, int gameId, int numTurns){
        this.mapFiles = mapFiles;
        this.playerStrategies = playerStrategies;
        this.gameId = gameId;
        this.numTurns = numTurns;
        this.tournamentResult = new ArrayList<>();
    }

    public ArrayList<String> getTournamentResult(){
        return tournamentResult;
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
        int n = gameId;
        while (n != 0) {
            for (String mapPath : mapFiles){
                Gameplay gameplay = Gameplay.getInstance();
                gameplay.setGameMode("Tournament");
                CommandController.parseCommand("loadmap " + mapPath);
                gameplay.getPlayerQueue().clear();
                gameplay.getPlayers().clear();
                gameplay.getRemovedPlayer().clear();
                addPlayer(gameplay);
                CommandController.parseCommand("populatecountries");
                CommandController.parseCommand("placeall");
                int t = numTurns;
                while (t != 0) {
                    CommandController.parseCommand("botplay");
                    if (gameplay.getCurrentPlayer().isWinner()) {
                        tournamentResult.add(gameplay.getCurrentPlayer().getPlayerName());
                        break;
                    }
                    t--;
                    if (t == 0) {
                        tournamentResult.add("DRAW");
                        System.out.println("DRAW");
                    }
                }
            }
            n--;
        }
        System.out.println("Tournament Done!!!!");
    }

    private void addPlayer(Gameplay gameplay) {

        for (String strategy : playerStrategies){
            gameplay.addPlayer(strategy, CommandController.createPlayerStrategy(strategy));
        }
    }

}
