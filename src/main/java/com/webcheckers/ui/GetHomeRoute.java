package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.CheckerGame.GameWinner;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
    static final String TITLE_KEY = "title";
    static final String TITLE = "Welcome!";
    static final String PLAYERLIST_KEY = "players";
    static final String PLAYERLIST_SIZE_KEY = "numPlayers";
    static final String VIEWFILE = "home.ftl";
    static final String FINISHEDGAMES_KEY = "game";
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter center;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine, GameCenter center) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.center = center;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");
        Session httpSession = request.session();
    
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
    

        if(current != null){
            if(current.isChallenged()){
                response.redirect(WebServer.GAME_URL);
            }
            //deleting a game after players have left
            if(center.getGameByID(current.getGameID()) != null){
                CheckerGame lastGame = center.getGameByID(current.getGameID()); 
                GameWinner win = lastGame.getWinner();
                if( (win == GameWinner.red && current == lastGame.getRedPlayer()) || (win == GameWinner.white && current == lastGame.getWhitePlayer()) ){
                    Player opponent = center.getGameByID(current.getGameID()).getOpponent(current);
                    opponent.setGameID(null);
                    current.setGameID(null);
                    opponent.setStateWaiting();
                    current.setStateWaiting();
                    center.removeGame(current.getGameID());
                    //httpSession.attribute("lastGame", lastGame);
                }
            }
            System.out.println(current.getName());
        }
        Map<String, Object> vm = new HashMap<>();

        //System.out.println(httpSession.attribute("lastGame"));
    
        if(request.queryParams("error") != null && request.queryParams("error").equals("783")){
            vm.put("error", "That Player is Unavailable");
        }

        vm.put(WebServer.PLAYER_SESSION_KEY, httpSession.attribute(WebServer.PLAYER_SESSION_KEY));
        vm.put(TITLE_KEY, TITLE);
        vm.put(PLAYERLIST_KEY, playerLobby.getPlayers());
        vm.put(FINISHEDGAMES_KEY, httpSession.attribute("lastGame"));
        vm.put(PLAYERLIST_SIZE_KEY, playerLobby.getPlayers().size()-2);
        return templateEngine.render(new ModelAndView(vm , VIEWFILE));
    }

}
