package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
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
 * Unit Test for {@link PostValidateMoveRoute} component
 * 
 */
@Tag("UI-Tier")
public class PostValidateMoveTest {

    /**
     * component under test
     */
    private PostValidateMoveRoute CuT;

    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter center;
    private Gson gson;
    private Player current;
    private Player other;

    /**
     * Setup scenario before each test
     */
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

        CuT = new PostValidateMoveRoute(gson, center);
    }

    /**
     * Tests that a valid move returns true
     */
    @Test
    public void ValidMoveTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        Move mov = new Move(new Position(5,4), new Position(4,3));
        String movString = gson.toJson(mov);
        when(request.body()).thenReturn(movString);
        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());
        

        String json;
        try {
            json = (String) CuT.handle(request, response);
        } catch (Exception e) {
            return;
        }

        Message m = gson.fromJson(json, Message.class);

        assertEquals("Valid move", m.getText());
    }
    /**
     * tests when there are no moves left for a players turn
     */
    @Test
    public void noMovesLeftTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        Move mov = new Move(new Position(5,4), new Position(4,3));
        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.pushMove(mov);
        

        String json;
        try {
            json = (String) CuT.handle(request, response);
        } catch (Exception e) {
            return;
        }

        Message m = gson.fromJson(json, Message.class);

        assertEquals(MessageType.error, m.getType());
        assertEquals("You are out of moves", m.getText());
    }
}