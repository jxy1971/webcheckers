package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

/**
 * Test NoPieces userstory
 */
public class NoPiecesTest {
    private Player redPlayer = mock(Player.class);
    private Player whitePlayer = mock(Player.class);
    private CheckerGame game = new CheckerGame(redPlayer,whitePlayer);

    /**
     * Test whiteHasPieces
     */
    @Test
    void testWhiteHasPieces(){
        assertTrue(game.whiteHasPieces());
        game.setNumWhite(0);
        assertFalse(game.whiteHasPieces());
    }

    /**
     * Test redHasPieces
     */
    @Test
    void testRedHasPieces(){
        assertTrue(game.redHasPieces());
        game.setNumRed(0);
        assertFalse(game.redHasPieces());
    }

    /**
     * Test who the winner is when someone has no pieces
     */
    @Test
    void testPlayerHasNoPieces(){
        game.setNumRed(0);
        game.playerNoPiecesLose();
        assertEquals(game.getWinner(), CheckerGame.GameWinner.white);
    }
}
