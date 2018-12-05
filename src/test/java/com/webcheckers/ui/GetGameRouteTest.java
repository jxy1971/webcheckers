package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.CheckerGame.GameWinner;
import com.webcheckers.model.Player;
import com.webcheckers.model.ViewMode;
import com.webcheckers.model.Board.Enums.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit Test for {@link GetGameRoute} component
 * 
 */
@Tag("UI-Tier")
public class GetGameRouteTest {

    /**
     * component under test
     */
    private GetGameRoute CuT;

    private PlayerLobby lobby;
    private GameCenter center;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private Player current;
    private Player other;
    private Player bot;
    private CheckerGame game;

    @BeforeEach
    public void setup() {
        lobby = new PlayerLobby();
        center = new GameCenter();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        current = new Player("Player1");
        other = new Player("Player2");
        lobby.addPlayer(current);
        lobby.addPlayer(other);
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        when(request.queryParams("id")).thenReturn("Player2");
        
        CuT = new GetGameRoute(lobby, center, engine);
    }

    /**
     * test that page is sent
     */
    @Test
    public void viewModelBase(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, "Game");
        testHelper.assertViewModelAttribute(WebServer.PLAYER_SESSION_KEY, current);
    }

    /**
     * test that player gets redirected to game
     */
    @Test
    public void playerReceiveGame(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        center.createNewGame(other, current);
        current.setGameID(other.getName());
        other.setGameID(other.getName());
        other.setStateIngame();
        current.setStateChallenged();

        CuT.handle(request, response);

        assertTrue(current.isInGame());

        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, ViewMode.PLAY);
        testHelper.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, other);//center.getGameByID(current.getGameID()).getRedPlayer());
        testHelper.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, current);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);
        //testHelper.assertViewModelAttribute(GetGameRoute.BOARD_ATTR, center.getGameByID(current.getGameID()).getRedBoard());
    }

    /**
     * test that a player can challenge another player
     */
    @Test
    public void playerChallengesPlayerObjects(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        current.setStateWaiting();
        other.setStateWaiting();

        CuT.handle(request, response);

        assertTrue(current.isInGame());
        assertTrue(other.isChallenged());

        assertTrue(current.getGameID().equals(current.getName()));
        assertTrue(other.getGameID().equals(current.getName()));

        assertTrue(center.getGameByID(current.getGameID()).getWhitePlayer() == other);
    }

    /**
     * test for the view/model when a player challenges another player
     */
    @Test
    public void playerChallengesViewModel(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        current.setStateWaiting();
        other.setStateWaiting();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, ViewMode.PLAY);
        testHelper.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, current);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, other);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);
        //testHelper.assertViewModelAttribute(GetGameRoute.BOARD_ATTR, center.getGameByID(current.getGameID()).getRedBoard());
    }

    /**
     * checking view/model for red player
     */
    @Test
    public void playersInGameRed(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        center.createNewGame(current, other);
        current.setGameID(current.getName());
        other.setGameID(current.getName());
        current.setStateIngame();
        other.setStateIngame();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, ViewMode.PLAY);
        testHelper.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, current);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, other);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);


    }

    /**
     * checking view/model for white player
     */
    @Test
    public void playersInGameWhite(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        center.createNewGame(other, current);
        current.setGameID(other.getName());
        other.setGameID(other.getName());
        current.setStateIngame();
        other.setStateIngame();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, ViewMode.PLAY);
        testHelper.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, other);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, current);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);
    }

    /**
     * Test when challenging white bot
     */
    @Test
    public void whiteBotOpponentPlayerInGame(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("id")).thenReturn("Bot-Red");
        bot = new Player("Bot-Red", true);
        lobby.addPlayer(bot);

        current.setStateWaiting();

        CuT.handle(request, response);

        assertTrue(current.isInGame());
    }

    /**
     * Second test for coverage on ViewModel against white bot
     */
    @Test
    public void whiteBotVMTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("id")).thenReturn(PlayerLobby.WHITEBOT);
        bot = new Player(PlayerLobby.WHITEBOT, true);
        lobby.addPlayer(bot);

        current.setStateWaiting();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, current);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, bot);
        

    }

    /**
     * Test when challenging red bot
     */
    @Test
    public void redBotOpponentPlayerInGame(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("id")).thenReturn("Bot-Red");
        bot = new Player("Bot-Red", true);
        lobby.addPlayer(bot);

        current.setStateWaiting();

        CuT.handle(request, response);

        assertTrue(current.isInGame());
    }

    /**
     * test for when red player wins
     */
    @Test
    public void redWinner(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.setWinner(GameWinner.red);
        current.setStateIngame();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.MESSAGE_ATTR, GetGameRoute.YOU_WIN);
    }

    /**
     * test for when red loses
     */
    @Test
    public void redLoser(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        center.createNewGame(current, other);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.setWinner(GameWinner.white);
        current.setStateIngame();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.MESSAGE_ATTR, GetGameRoute.YOU_LOSE);
    }

    /**
     * test for when white loses
     */
    @Test
    public void whiteLoser(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        center.createNewGame(other, current);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.setWinner(GameWinner.red);
        current.setStateIngame();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.MESSAGE_ATTR, GetGameRoute.YOU_LOSE);
    }

    /**
     * test for when white wins
     */
    @Test
    public void whiteWinner(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        center.createNewGame(other, current);
        CheckerGame game = center.getGameByID(current.getGameID());
        game.setWinner(GameWinner.white);
        current.setStateIngame();

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetGameRoute.MESSAGE_ATTR, GetGameRoute.YOU_WIN);
    }

}