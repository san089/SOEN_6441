package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.view.TournamentView;

/**
 * This class is the tournament mode
 */

public class TournamentGame extends Thread{
    private ArrayList<String> mapFiles;
    private ArrayList<String> playerStrategies;
    private int gameId;
    private int numTurns;
    private ArrayList<String> tournamentResult;

    /**
     * Constructor, get command from command controller, parse everything from the command
     * @param mapFiles map list
     * @param playerStrategies player's strategy and also are player's name
     * @param gameId number of game
     * @param numTurns number of turn
     */
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

    /**
     * Run tournament mode in triple-nesting loop, outer loop control the number of game, second loop control the map,
     * third loop control the number of turn.
     */

    public void runTournament(){
        System.out.println("Starting tournament");
        int n = gameId;
        while (n != 0) {
            for (String mapPath : mapFiles){
                Gameplay.getInstance().setGameMode("Tournament");
                CommandController.parseCommand("loadmap " + mapPath);
                Gameplay.getInstance().getPlayerQueue().clear();
                Gameplay.getInstance().getPlayers().clear();
                Gameplay.getInstance().getRemovedPlayer().clear();
                addPlayer(Gameplay.getInstance());
                CommandController.parseCommand("populatecountries");
                CommandController.parseCommand("placeall");
                int t = numTurns;
                while (t != 0) {
                    CommandController.parseCommand("botplay");
                    if (Gameplay.getInstance().getCurrentPlayer().isWinner()) {
                        tournamentResult.add(Gameplay.getInstance().getCurrentPlayer().getPlayerName());
                        break;
                    }
                    t--;
                    if (t == 0) {
                        tournamentResult.add("DRAW");
                        System.out.println("DRAW");
                    }
                }
                CommandController.parseCommand("closephaseview");
            }
            n--;
        }

        System.out.println("Tournament Done!!!!");
        new TournamentView(tournamentResult, gameId, mapFiles.size());
    }

    /**
     * Tournament mode initialize player.
     * @param gameplay
     */

    private void addPlayer(Gameplay gameplay) {

        for (String strategy : playerStrategies){
            gameplay.addPlayer(strategy, CommandController.createPlayerStrategy(strategy));
        }
    }

}
