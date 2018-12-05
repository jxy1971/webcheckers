package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import com.google.gson.Gson;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import java.util.logging.Logger;

import spark.Response;
import spark.Request;
import spark.Session;

public class GetReplayStopRouteTest {

    private GetReplayStopRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private Gson gson;
    private GameCenter center;
    private Player current;
    private Player other;

    @BeforeEach
    public void setup() {
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        current = new Player("Player1");
        other = new Player("Player2");
        center = new GameCenter();
        CuT = new GetReplayStopRoute(gson, center);
    }

    /**
     * test that player is waiting when not watching replay
     */
    @Test
    public void exitReplayTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        CuT.handle(request, response);
        assertTrue(current.isWaiting());
    }
}
