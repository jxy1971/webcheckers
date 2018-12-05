package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;
import static spark.Spark.halt;

/**
 * The UI Controller to POST a sign in request to the SignIn page.
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    public final static String TITLE = "Sign In!";

    public final static String FAIL_KEY = "signInFail";

    /**
     * Creates a new route for signin
     * @param playerLobby the lobby storing player info
     * @param templateEngine The templete rendering engine for freemarker templates
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "playerlobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        LOG.config("PostSignInRoute is initialized.");
    }

    /**
     * Handle the request to sign in
     *
     * @param request - the HTTP request
     * @param response - the HTTP response
     * @return the rendered HTML home page
     */
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignInRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_KEY, TITLE);

        final Session session = request.session();


        //retrieves username
        final String username = request.queryParams("username");

        //checks if username length > 0
        if (username.length() > 0) {
            if(username.length()>25){
                vm.put(FAIL_KEY,"Please keep your username 25 characters long or shorter.");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            }
            //checks if username exists
            if (this.playerLobby.userNameExists(username)) {
                vm.put(FAIL_KEY, "Username taken.");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            } else if (this.playerLobby.hasSpecialCharacter(username)) {
                vm.put(FAIL_KEY, "Please use alphanumeric characters.");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            } else {
                Player player = new Player(username);
                this.playerLobby.addPlayer(player);
                session.attribute(WebServer.PLAYER_SESSION_KEY, player);
                //vm.put(GetHomeRoute.TITLE_KEY, GetHomeRoute.TITLE);
                //vm.put("players", playerLobby.getPlayers());
                response.redirect(WebServer.HOME_URL);
                //halt();
                return null;
                //return templateEngine.render(new ModelAndView(vm , "home.ftl"));
            }
        }

        else {
            vm.put(FAIL_KEY, "Please enter a valid username");
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
        

    }

}
