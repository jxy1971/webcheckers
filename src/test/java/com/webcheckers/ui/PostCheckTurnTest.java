package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.CheckerGame.GameWinner;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit Test for {@link PostCheckTurn} component
 * 
 */
@Tag("UI-Tier")
public class PostCheckTurnTest {

    /**
     * component under test
     */
    private PostCheckTurn CuT;

    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter center;
    private Gson gson;
    private Player current;
    private Player other;

    @BeforeEach
    public void setup() {
        lobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        current = new Player("Player1");
        other = new Player("Player2");
        center = new GameCenter();

        CuT = new PostCheckTurn(gson, center);
    }

    /**
     * basic test, checks if it is players turn
     */
    @Test
    public void baseTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());

        String json;
        try {
            json = (String) CuT.handle(request, response);
        } catch (Exception e) {
            return;
        }

        Message m = gson.fromJson(json, Message.class);

        assertEquals(true, game.isMyTurn(current));
        assertEquals(GameWinner.ingame, game.getWinner());

    }

    /**
     * test for returnign false when other player is taking turn
     */
    @Test
    public void ingameTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());

        String json;
        try {
            json = (String) CuT.handle(request, response);
        } catch (Exception e) {
            return;
        }

        Message m = gson.fromJson(json, Message.class);

        assertEquals("true", m.getText());

    }

    @Test
    public void notIngameTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.setWinner(GameWinner.red);

        String json;
        try {
            json = (String) CuT.handle(request, response);
        } catch (Exception e) {
            return;
        }

        Message m = gson.fromJson(json, Message.class);

        assertEquals("true", m.getText());

    }

}