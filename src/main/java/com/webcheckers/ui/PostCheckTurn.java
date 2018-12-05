package com.webcheckers.ui;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Player;
import com.webcheckers.model.CheckerGame.GameWinner;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * This Route Class checks to see if the opponent has submitted their turn.
 * The HTTP response must return message with type of info and the
 * text of the message is either true if it's now this players turn
 * or false if the opponent is still taking their turn.
 *
 * If the opponent resigns the game then this Ajax calls must
 * return an info message with true; and when the
 * Game View is rendered it must inform this player
 * that their opponent resigned the game.
 */
public class PostCheckTurn implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private Gson gson;
    private GameCenter center;

    /**
     * constructor
     * @param gson
     * @param center
     */
    public PostCheckTurn(Gson gson, GameCenter center){
        this.gson = gson;
        this.center = center;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player current = session.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = center.getGameByID(current.getGameID());
        boolean isMyTurn = game.isMyTurn(current);
        GameWinner winner = game.getWinner();

        if(winner == GameWinner.ingame){
            Message turnMessage = new Message(Boolean.toString(isMyTurn), MessageType.info);
            return gson.toJson(turnMessage);
        }
        else{
            Message turnMessage = new Message("true", MessageType.info);
            return gson.toJson(turnMessage);
        }
        
    }
}
