package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the SignIn page.
 */
public class GetSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    
    public static final String VIEWFILE = "signin.ftl";
    public static final String TITLE = "Sign In";

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET/} HTTP request
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        //validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetSignInRoute is initialized.");

    }

    /**
     * Render the WebCheckers SignIn page.
     *
     * @param request - the HTTP request
     * @param response - the HTTP response
     * @return the rendered HTML for the SignIn page
     */
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_KEY, TITLE);
        return templateEngine.render(new ModelAndView(vm, VIEWFILE));
    }
    
}
