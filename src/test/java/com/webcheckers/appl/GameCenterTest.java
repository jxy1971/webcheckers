package com.webcheckers.appl;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests GameCenter
 */
public class GameCenterTest {
    private GameCenter gameCenter = new GameCenter();
    private Player red = new Player("Red");
    private Player white = new Player("White");

    /**
     * Tests createNewGame
     */
    @Test
    void testCreateNewGame(){
        gameCenter.createNewGame(red,white);
        assertFalse(gameCenter.getGames().isEmpty());
        assertTrue(gameCenter.getGames().get("Red") instanceof CheckerGame);
    }

    /**
     * Tests getGameByID
     */
    @Test
    void testGetGameById(){
        gameCenter.createNewGame(red,white);
        assertNotNull(gameCenter.getGameByID("Red"));
    }

    /**
     * Tests removeGame
     */
    @Test
    void testRemoveGame(){
        gameCenter.removeGame("White");
        assertEquals(gameCenter.getGames().size(),0);
    }
}
