package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.sound.sampled.SourceDataLine;

import static org.mockito.ArgumentMatchers.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.CheckerGame.GameWinner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.ModelAndView;

/**
 * Unit Test for {@link GetHomeRoute} component
 * 
 */
@Tag("UI-Tier")
public class GetHomeRouteTest {

    /**
     * component under test
     */
    private GetHomeRoute CuT;

    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter center;

    @BeforeEach
    public void setup() {
        lobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        center = mock(GameCenter.class);

        CuT = new GetHomeRoute(lobby, engine, center);
    }

    @Test  
    public void SignedOut() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(null);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_KEY, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYERLIST_SIZE_KEY, lobby.getPlayers().size()-2);//dont count bots
        testHelper.assertViewModelAttribute(WebServer.PLAYER_SESSION_KEY, null);

        testHelper.assertViewName(GetHomeRoute.VIEWFILE);
        

    }

    /**
     * test that players get signed in successfully
     */
    @Test
    public void SignedIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        Player player1 = new Player("PlayerA");
        Player player2 = new Player("PlayerB");
        lobby.addPlayer(player1);
        lobby.addPlayer(player2);
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(lobby.getPlayer("PlayerA"));

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(WebServer.PLAYER_SESSION_KEY, session.attribute(WebServer.PLAYER_SESSION_KEY));
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_KEY, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYERLIST_SIZE_KEY, 0);//this may be an issue
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYERLIST_KEY, lobby.getPlayers());
    }

    /**
     * test that finished games are removed from gamecenter when players done
     */
    @Test
    public void removeGame() {
        Player current = new Player("Player1");
        Player other = new Player("Player2");
        center = new GameCenter();
        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.setWinner(GameWinner.red);
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);

        CuT.handle(request, response);

        assertEquals(center.getGameByID(current.getGameID()).getOpponent(current), other);

    }



}