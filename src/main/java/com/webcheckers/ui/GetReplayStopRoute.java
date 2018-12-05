package com.webcheckers.ui;

import com.google.gson.Gson;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import java.util.logging.Logger;

import spark.*;

public class GetReplayStopRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetReplayStopRoute.class.getName());
    private Gson gson;
    private GameCenter center;

    public GetReplayStopRoute(Gson gson, GameCenter center) {
        this.center = center;
        this.gson = gson;
    }

    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();

        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        current.setStateWaiting();
        response.redirect(WebServer.HOME_URL);
        
        return null;
    }
    
}
