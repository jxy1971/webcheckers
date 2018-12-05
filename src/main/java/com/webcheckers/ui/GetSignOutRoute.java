package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;
import static spark.Spark.halt;

/**
 * Route for signing out of a username
 *
 */


public class GetSignOutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    
    private final PlayerLobby playerLobby;

    public static String TITLE = "Signing Out!";

    /**
     * Constructor for sign out route
     * @param playerLobby
     */
    public GetSignOutRoute(PlayerLobby playerLobby) {
        Objects.requireNonNull(playerLobby, "playerlobby must not be null");
        this.playerLobby = playerLobby;
        LOG.config("GetSignOutRoute is initialized.");
    }
    
    /**
     * handles the request for a player to sign out
     */
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignOutRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_KEY, TITLE);

        final Session session = request.session();

        //Removes player from the playerlobby 
        Player current = session.attribute(WebServer.PLAYER_SESSION_KEY);
        playerLobby.removePlayer(current.getName());
        
        session.attribute(WebServer.PLAYER_SESSION_KEY, null);
        
        response.redirect(WebServer.HOME_URL);
        //halt();
        return null;
    }

}
