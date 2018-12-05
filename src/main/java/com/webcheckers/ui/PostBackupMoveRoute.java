package com.webcheckers.ui;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * route to undo the last move
 */
public class PostBackupMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private Gson gson;
    private GameCenter center;

    public PostBackupMoveRoute(Gson gson, GameCenter center) {
        this.gson = gson;
        this.center = center;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        Player current = session.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = center.getGameByID(current.getGameID());
        Message message;
        // if no moves made
        if (game.getMoveStack().isEmpty()) {
            message = new Message("No moves have been made", MessageType.error);
        } else {
            Move last = game.getMoveStack().pop();
            
            //success
            message = new Message("One move undone.", MessageType.info);
        }
        return gson.toJson(message);
    }
}
