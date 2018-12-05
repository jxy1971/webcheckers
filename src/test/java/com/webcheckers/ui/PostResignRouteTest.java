package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.CheckerGame.GameWinner;
import com.webcheckers.model.Player;
import com.webcheckers.model.Board.Enums.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Unit Test for {@link PostResignRoute} component
 * 
 */
@Tag("UI-Tier")
public class PostResignRouteTest {

    /**
     * component under test
     */
    private PostResignRoute CuT;

    private GameCenter center;
    private Request request;
    private Session session;
    private Response response;
    private Gson gson;
    private Player current;
    private Player other;
    private CheckerGame game;

    @BeforeEach
    public void setup() {
        center = mock(GameCenter.class);
        gson = new Gson();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);

        current = new Player("Player1");
        other = new Player("player2");
        game = new CheckerGame(current, other);

        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        when(center.getGameByID(current.getGameID())).thenReturn(game);

        CuT = new PostResignRoute(gson, center);
    }

    /**
     * test that resigned player is now waiting in lobby
     */
    @Test  
    public void currentPlayerWaitingTest(){
        CuT.handle(request, response);
        assertTrue(current.isWaiting());
    }

    /**
     * test that opponent has won when player resigned
     */
    @Test
    public void opponentWon(){
        CuT.handle(request, response);
        assertTrue(game.getWinner() == GameWinner.white);
    }

    /**
     * check that it is the opponents turn after player resigns
     */
    @Test
    public void opponentTurn(){
        CuT.handle(request, response);
        assertTrue(game.isMyTurn(other));
        assertTrue(game.getActiveColor() == Color.WHITE);
    }


}