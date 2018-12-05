package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.*;


/**
 * Class that creates the lobby
 */
public class PlayerLobby {
    public final static String WHITEBOT = "Bot-White";
    public final static String REDBOT = "Bot-Red";

    private final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    private ArrayList<Player> players;

    public PlayerLobby(){
        players = new ArrayList<>();
    }

    /**
     * Add player into lobby
     * @param player - player
     */
    public void addPlayer(Player player){
        players.add(player);
        player.setStateWaiting();
    }

    /**
     * Get players in the lobby
     * @return players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * Checks if the username already exists
     * @param userName - name of user
     * @return true if is taken, false if it isn't
     */
    public boolean userNameExists(String userName)
    {
        for (Player player: players) {
            if(player.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets and returns a player based on its userName
     * @param name Name of the player
     * @return Player
     */
    public Player getPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Checks if username has special characters
     * @param name Name to be checked
     * @return if name has special characters in it
     */
    public boolean hasSpecialCharacter(String name){
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        return p.matcher(name).find();
    }

    /**
     * Removes a player from player list given name
     * @param name name of player
     */
    public void removePlayer(String name){
        players.remove(getPlayer(name));
    }
}
