package com.webcheckers.ui;

import static spark.Spark.halt;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.CheckerGame.GameWinner;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Player;
import com.webcheckers.model.ViewMode;
import com.webcheckers.model.Board.Enums.Color;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private final TemplateEngine templateEngine;
  private PlayerLobby playerLobby;
  private GameCenter center;

  // variables from game.ftl, used in VM
  // static final String CURRENTPLAYER_ATTR = "currentPlayer";
  static final String VIEW_ATTR = "viewMode";
  static final String REDPLAYER_ATTR = "redPlayer";
  static final String WHITEPLAYER_ATTR = "whitePlayer";
  static final String ACTIVECOLOR_ATTR = "activeColor";
  static final String MESSAGE_ATTR = "message";
  static final String TITLE_ATTR = "title";
  static final String BOARD_ATTR = "board";
  static final Message YOU_WIN = new Message("You Win.", MessageType.info);
  static final Message YOU_LOSE = new Message("You Lose.", MessageType.error);

  /**
   * Create the Spark Route (UI controller) for the {@code GET /} HTTP request.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetGameRoute(PlayerLobby playerLobby, GameCenter center, final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;
    this.center = center;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Game page.
   *
   * @param request  the HTTP request
   * @param response the HTTP response
   *
   * @return the rendered HTML for the Game page
   */
  @Override
  public Object handle(Request request, Response response) {
     LOG.finer("GetGameRoute is invoked.");
    Session httpSession = request.session();
    Map<String, Object> vm = new HashMap<>();

    // Gets current player
    Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
    vm.put(WebServer.PLAYER_SESSION_KEY, current);
    vm.put(TITLE_ATTR, "Game");

    // If player is receiving a game
    if (current.isChallenged()) {
      current.setStateIngame();
      httpSession.attribute("lastGame", this.center.getGameByID(current.getGameID()));
      vm.put(VIEW_ATTR, ViewMode.PLAY);
      vm.put(REDPLAYER_ATTR, this.center.getGameByID(current.getGameID()).getRedPlayer());
      vm.put(WHITEPLAYER_ATTR, current);
      vm.put(ACTIVECOLOR_ATTR, Color.RED);
      vm.put(BOARD_ATTR, this.center.getGameByID(current.getGameID()).getWhiteBoard());
    }

    // If player is challenging another person
    else if (current.isWaiting()) {
      String name = request.queryParams("id");
      Player opponent = playerLobby.getPlayer(name);
      if (opponent == null || !opponent.isWaiting()) {
        response.redirect(WebServer.HOME_URL + "?error=783");
        halt();
      }
      // If opponent is AI
      if (opponent.isBot()) {
        current.setStateIngame();
        // Challenging Red Bot
        if (opponent.getName().equals(playerLobby.REDBOT)) {
          this.center.createNewBotGame(opponent, current, current);
          httpSession.attribute("lastGame", this.center.getGameByID(current.getGameID()));
          vm.put(REDPLAYER_ATTR, opponent);
          vm.put(WHITEPLAYER_ATTR, current);
          vm.put(BOARD_ATTR, this.center.getGameByID(current.getGameID()).getWhiteBoard());
        } else { //Challenging White Bot
          this.center.createNewBotGame(current, opponent, current);
          httpSession.attribute("lastGame", this.center.getGameByID(current.getGameID()));
          vm.put(REDPLAYER_ATTR, current);
          vm.put(WHITEPLAYER_ATTR, opponent);
          vm.put(BOARD_ATTR, this.center.getGameByID(current.getGameID()).getRedBoard());
        }
        vm.put(VIEW_ATTR, ViewMode.PLAY);
        vm.put(ACTIVECOLOR_ATTR, Color.RED);

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      }

      current.setStateIngame();
      opponent.setStateChallenged();
      this.center.createNewGame(current, opponent);
      current.setGameID(current.getName());
      opponent.setGameID(current.getName());
      httpSession.attribute("lastGame", this.center.getGameByID(current.getGameID()));
      vm.put(VIEW_ATTR, ViewMode.PLAY);
      vm.put(REDPLAYER_ATTR, current);
      vm.put(WHITEPLAYER_ATTR, opponent);
      vm.put(ACTIVECOLOR_ATTR, Color.RED);
      vm.put(BOARD_ATTR, this.center.getGameByID(current.getGameID()).getRedBoard());
    }
    // both players are in a game together
    else {
      CheckerGame game = center.getGameByID(current.getGameID());
      vm.put(VIEW_ATTR, ViewMode.PLAY);
      vm.put(REDPLAYER_ATTR, game.getRedPlayer());
      vm.put(WHITEPLAYER_ATTR, game.getWhitePlayer());
      vm.put(ACTIVECOLOR_ATTR, game.getActiveColor());
      if (current == game.getRedPlayer())
        vm.put(BOARD_ATTR, game.getRedBoard());
      else
        vm.put(BOARD_ATTR, game.getWhiteBoard());
      if (game.getWinner() != GameWinner.ingame) {
        if(game.getWinner() == GameWinner.red){
          if(game.getRedPlayer() == current){
            vm.put(MESSAGE_ATTR, YOU_WIN);
          }
          else{
            vm.put(MESSAGE_ATTR, YOU_LOSE);
          }
        }
        else if(game.getWinner() == GameWinner.white){
          if(game.getWhitePlayer() == current){
            vm.put(MESSAGE_ATTR, YOU_WIN);
          }
          else{
            vm.put(MESSAGE_ATTR, YOU_LOSE);
          }
        }
      }
    }

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }

}
