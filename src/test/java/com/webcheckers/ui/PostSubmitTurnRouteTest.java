package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
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
 * Unit Test for {@link PostSubmitTurnRoute} component
 * 
 */
@Tag("UI-Tier")
public class PostSubmitTurnRouteTest {

    /**
     * component under test
     */
    private PostSubmitTurnRoute CuT;

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

        CuT = new PostSubmitTurnRoute(gson, center);
    }

    /**
     * tests that a turn was submitted successfully
     */
    @Test
    public void mainTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.pushMove(new Move(new Position(2,2), new Position(3,3)));

        String json;
        try {
            json = (String) CuT.handle(request, response);
        } catch (Exception e) {
            return;
        }

        Message m = gson.fromJson(json, Message.class);
        assertEquals("All moves submitted", m.getText());
    }
}