package com.webcheckers.ui;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;

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
import com.webcheckers.model.ViewMode;
import com.webcheckers.model.Move;



public class GetReplayRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());

    private Gson gson;
    private GameCenter center;
    private final TemplateEngine templateEngine;

    static final String TITLE_ATTR = "title";
    static final String VIEW_ATTR = "viewMode";
    static final String REDPLAYER_ATTR = "redPlayer";
    static final String WHITEPLAYER_ATTR = "whitePlayer";
    static final String ACTIVECOLOR_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String MESSAGE_ATTR = "message";
    static final String MODE_ATTR = "modeOptionsAsJSON";
    static final String NEWGAME_ATTR = "newGame";

    public GetReplayRoute(Gson gson, GameCenter center, final TemplateEngine templateEngine) {
        this.gson = gson;
        this.center = center;
        this.templateEngine = templateEngine;
    }


    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame newGame;
        if (!(current.isReplay())) {
            CheckerGame lastGame = httpSession.attribute("lastGame");
            newGame = new CheckerGame(lastGame.getRedPlayer(), lastGame.getWhitePlayer(), lastGame.getAllMoves(), lastGame.getPiecesTaken(), lastGame.getPieceMoved());
            httpSession.attribute(NEWGAME_ATTR, newGame);
        }
        else {
            newGame = httpSession.attribute(NEWGAME_ATTR);
        }
        
        current.setStateReplay();
        
        vm.put(WebServer.PLAYER_SESSION_KEY, current);
        vm.put(TITLE_ATTR, "Replay");
        vm.put(VIEW_ATTR, ViewMode.REPLAY);
        vm.put(REDPLAYER_ATTR, newGame.getRedPlayer());
        vm.put(WHITEPLAYER_ATTR, newGame.getWhitePlayer());
        vm.put(ACTIVECOLOR_ATTR, newGame.getActiveColor());
        
        // Checks which player is replaying, returns corresponding boardview
        if (current == newGame.getRedPlayer()) {
            vm.put(BOARD_ATTR, newGame.getRedBoard());
        }
        else {
            vm.put(BOARD_ATTR, newGame.getWhiteBoard());
        }
        
        Map<String, Object> modeOptions = new HashMap<>();
        modeOptions.put("hasNext", false);
        modeOptions.put("hasPrevious", false);
        if (newGame.getMoveCtr() < newGame.getAllMoves().size())
            modeOptions.put("hasNext", true);
        if (newGame.getMoveCtr() > 0)
            modeOptions.put("hasPrevious", true);

        vm.put(MODE_ATTR, gson.toJson(modeOptions));
                        
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
        
    }
}
