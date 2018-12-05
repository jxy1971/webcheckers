package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Unit Test for {@link GetSignOutRoute} component
 * 
 */
@Tag("UI-Tier")
public class GetSignOutRouteTest {

    /**
     * component under test
     */
    private GetSignOutRoute CuT;

    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private Player current;

    @BeforeEach
    public void setup() {
        playerLobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        current = new Player("Player1");

        CuT = new GetSignOutRoute(playerLobby);
    }

    /**
     * test to ensure signout
     */
    @Test
    public void signOutTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);

        CuT.handle(request, response);

        assertTrue(!playerLobby.userNameExists("Player1"));

    }


}