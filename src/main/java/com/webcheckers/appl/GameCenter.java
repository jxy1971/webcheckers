package com.webcheckers.appl;

import com.webcheckers.model.BotGame;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * It holds the game and keeps all of the needed info of the game in one place
 */
public class GameCenter {
    private Map<String, CheckerGame> games;
    /**
     * Constructor for GameCenter class
     */
    public GameCenter() {
        games = new HashMap<>();
    }

    /**
     * creates a new game with 2 given players
     * @param player1 the first player
     * @param player2 the second player
     */
    public void createNewGame(Player player1, Player player2) {
        CheckerGame newGame = new CheckerGame(player1, player2);
        String id = player1.getName();
        games.put(id, newGame);
        player1.setGameID(id);
        player2.setGameID(id);
    }

    /**
     * creates a new game with 2 players, where 1 is a bot
     * @param player1 red player
     * @param player2 white player
     * @param real a reference to which player is the real player (not a bot)
     */
    public void createNewBotGame(Player player1, Player player2, Player real) {
        BotGame newGame = new BotGame(player1, player2);
        String id = real.getName();
        games.put(id, newGame);
        real.setGameID(id);

    }

    /**
     * gets a map of all games
     * @return the map of games
     */
    public Map<String,CheckerGame> getGames(){
        return this.games;
    }

    
    /**
     * Given an id, returns the associated game
     * @param gameID String id
     * @return CheckerGame 
     */
    public synchronized CheckerGame getGameByID(String gameID) {
        return games.get(gameID);
    }

    /**
     * given a gameID, removes the game from the map
     * @param gameID string game
     */
    public void removeGame(String gameID) {
        games.remove(gameID);
    }
}
