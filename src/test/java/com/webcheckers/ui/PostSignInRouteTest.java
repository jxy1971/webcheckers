package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.ModelAndView;

/**
 * Unit Test for {@link PostSignInRoute} component
 * 
 */
@Tag("UI-Tier")
public class PostSignInRouteTest {

    /**
     * component under test
     */
    private PostSignInRoute CuT;

    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        playerLobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        CuT = new PostSignInRoute(playerLobby, engine);
    }

    /**
     * fail when user inputs an ivalid username
     */
    @Test
    public void baseTestFail(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("username")).thenReturn("Player1!");

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_KEY, PostSignInRoute.TITLE);

    }

    /**
     * test for username fail when too long
     */
    @Test
    public void lengthFail(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("username")).thenReturn("Playerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(PostSignInRoute.FAIL_KEY, "Please keep your username 25 characters long or shorter.");
    }

    /**
     * test for failing when a username is taken
     */
    @Test
    public void usernameTakenTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        playerLobby.addPlayer(new Player("Player"));
        when(request.queryParams("username")).thenReturn("Player");

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(PostSignInRoute.FAIL_KEY, "Username taken.");
    }

    /**
     * test for passing when a username is valid
     */
    @Test
    public void workingUserNameTest(){
        when(request.queryParams("username")).thenReturn("Player");

        CuT.handle(request, response);

        assertTrue(playerLobby.userNameExists("Player"));
        
    }

    /**
     * test for failing when username is empty
     */
    @Test
    public void noUsernameTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("username")).thenReturn("");

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(PostSignInRoute.FAIL_KEY, "Please enter a valid username");
    }

}