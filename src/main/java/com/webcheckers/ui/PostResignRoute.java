package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;

import com.webcheckers.model.Player;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.WebServer;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;

import spark.*;
import static spark.Spark.halt;

/**
 * The UI Controller to Post the Resign request.
 */
public class PostResignRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    private Gson gson;
    private GameCenter center;

    //private final PlayerLobby playerLobby;
    //private final TemplateEngine templateEngine;

    /**
     * Creates a new route for resignation
     * @param playerLobby the lobby storing player info
     * @param templateEngine The templete rendering engine for freemarker templates
     */
    public PostResignRoute(Gson gson, GameCenter center) {
        /*Objects.requireNonNull(playerLobby, "playerlobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;*/
        this.center = center;
        this.gson = gson;
        LOG.config("PostResignRoute is initialized.");
    }

    /**
     * Handle the request to resign
     *
     * @param request - the HTTP request
     * @param response - the HTTP response
     * @return the rendered HTML home page
     */
    public Object handle(Request request, Response response) {
        LOG.finer("PostResignRoute is invoked.");

        final Session session = request.session();
        
        Player current = session.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = center.getGameByID(current.getGameID());
        Player opponent = game.getOpponent(current);

        current.setStateWaiting();
        game.playerResigned(current);

        Message m = new Message("You have resigned", MessageType.info);

        return gson.toJson(m);

    }

}
