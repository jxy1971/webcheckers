package com.webcheckers.ui;

import java.util.logging.Logger;
import java.util.ArrayList;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Message;

public class PostReplayNextTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostReplayNextTurnRoute.class.getName());
    static final String NEWGAME_ATTR = "newGame";
    
    private Gson gson;
    private GameCenter center;

    public PostReplayNextTurnRoute(Gson gson, GameCenter center) {
        this.gson = gson;
        this.center = center;
    }
    
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame newGame = httpSession.attribute(NEWGAME_ATTR);
        Message ret = newGame.nextTurn();
        return gson.toJson(ret);
        
    }
    
}
