package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Player class
 */
public class Player {
    //name of player
    private String userName;
    //state of player in the lobby
    private PlayerState state;
    //id of the game player is in
    private String gameID;
    private ArrayList<Move> moves;
    //if this player is a bot
    private boolean isBot;
    
    /**
     * Player constructor
     * @param name - name of player
     */
    public Player(String name){
        userName = name;
        moves = new ArrayList<Move>();
        isBot = false;
    }

    /**
     * Bot constructor
     * @param name - name
     * @param bot - true for bot
     */
    public Player(String name, boolean bot){
        userName = name;
        moves = new ArrayList<Move>();
        isBot = bot;
    }

    /**
     * Get if player is a bot
     * @return boolean
     */
    public boolean isBot(){
        return isBot;
    }

    /**
     * Get name of the player
     * @return username
     */
    public String getName(){
        return userName;
    }

    /**
     * Sets the player state to WAITING
     */
    public void setStateWaiting(){ state = PlayerState.WAITING; }

    /**
     * Sets the player state to INGAME
     */
    public void setStateIngame(){ state = PlayerState.INGAME; }

    /**
     * Sets the player state to CHALLENGED
     */
    public void setStateChallenged(){ state = PlayerState.CHALLENGED; }

    /**
     * Sets the player state to REPLAY
     */
    public void setStateReplay(){ state = PlayerState.REPLAY; }
    
    /**
     * Checks whether or not the player is in a state of CHALLENGED
     * @return whether or not the player is in a state of CHALLENGED
     */
    public boolean isChallenged(){
        return state==PlayerState.CHALLENGED;
    }

    /**
     * Checks whether or no the player is in a state of WAITING
     * @return whether or no the player is in a state of WAITING
     */
    public boolean isWaiting(){
        return state == PlayerState.WAITING;
    }

    /**
     * Checks whether or no the player is in a state of INGAME
     * @return whether or no the player is in a state of INGAME
     */
    public boolean isInGame(){
        return state == PlayerState.INGAME;
    }

    /**
     * Checkers whether or not the player is in a state of REPLAY
     * @return whether or not the player i sin a state of REPLAY
     */
    public boolean isReplay() {
        return state == PlayerState.REPLAY;
    }
    
    /**
     * Returns the string version of the Player State, with spacing to even it out.
     * @return String version of the Player State
     */
    public String toString(){
        if(isChallenged()){
            return PlayerState.CHALLENGED.name();
        }
        else if(isWaiting()){
            return (PlayerState.WAITING.name()+"   ");
        }
        else if(isInGame()){
            return (PlayerState.INGAME.name()+"    ");
        }
        else if(isReplay()){
            return (PlayerState.REPLAY.name()+"    ");
        }
        return null;
    }

    /**
     * Sets a custom gameID to find games from GameCenter
     * @param gameID the string to assign
     */
    public void setGameID(String gameID)
    {
        this.gameID = gameID;
    }

    /**
     * gets the gameID
     * @return the gameID
     */
    public String getGameID()
    {
        return gameID;
    }
}
