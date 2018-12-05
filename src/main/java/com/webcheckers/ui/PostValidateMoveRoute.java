package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.model.CheckerGame.GameWinner;

import spark.*;

import java.util.logging.Logger;

/**
 * This action submits a single move for a player to be validated.
 * The server must keep track of each proposed move for a single turn in the user's game state.
 * The response body must be a message that has type info if the move is valid or error if
 * it is invalid. The text of the message must tell the user why a move is invalid.
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private Gson gson;
    private GameCenter center;

    /**
     * constructor
     * @param gson
     * @param center
     */
    public PostValidateMoveRoute(Gson gson, GameCenter center)
    {
        this.gson = gson;
        this.center = center;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostValidateMoveRoute is invoked.");

        final Session session = request.session();
        Player currentPlayer = session.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = center.getGameByID(currentPlayer.getGameID());
        Message moveMessage;

        //if game is over
        if(game.getWinner() != GameWinner.ingame){
            moveMessage = new Message("Cannot move when game is over", MessageType.error);
            return gson.toJson(moveMessage);
        }

        if(!game.playerHasMoves())
        {
            String messageText;
            if(game.piecesBlocked) {
                messageText = "Your piece(s) are blocked and you must resign";
            }
            else
                messageText = "You are out of moves";

            moveMessage = new Message(messageText, MessageType.error);
            return gson.toJson(moveMessage);
        }

        //get move from gson
        final String moveMade = request.body();
        final Move move = gson.fromJson(moveMade, Move.class);

        // check if move is valid
        moveMessage = move.validateMove(game.getPossibleMoves(), game.jumpMoveAvailable());

        if(moveMessage.getType() == MessageType.info)
            game.pushMove(move);

        return gson.toJson(moveMessage);
    }

}
