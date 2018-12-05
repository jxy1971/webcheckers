package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.model.CheckerGame.GameWinner;

import spark.*;

import java.util.logging.Logger;

/**
 * This action submits the player's turn. The Submit turn button will
 * only be active when the Game View is in the Stable Turn state.
 *
 * The response body must be a message that has type info if the turn, as a whole,
 * is valid and the server has processed the turn. At this point the GameView stops
 * and refreshes the page by re-requesting the GET /game URL.
 *
 * The server must return an error message if the turn is invalid with a
 * text message explaining why the turn is invalid. For example, if the player
 * takes a single move turn but that player has a jump turn somewhere else that
 * single move is not a valid turn. The player needs to take the jump instead;
 * so the GameView returns to the Stable Turn state and the player must then backup
 * the single move and take a jump turn. A similar situation arises after a player
 * makes a jump move and the moved piece is in position
 * to make another jump which must be made.
 */
public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private Gson gson;
    private GameCenter center;

    /**
     * constructor
     * @param gson
     * @param center
     */
    public PostSubmitTurnRoute(Gson gson, GameCenter center)
    {
        this.gson = gson;
        this.center = center;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostSubmitTurnRoute is invoked.");

        final Session session = request.session();
        Player currentPlayer = session.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = center.getGameByID(currentPlayer.getGameID());
        Message submitMessage = game.submitTurn();
        return gson.toJson(submitMessage);
    }
}
