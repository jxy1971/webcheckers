package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.webcheckers.model.CheckerGame.GameWinner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit Test for {@link BotGame} component
 * 
 */
@Tag("Model-Tier")
public class BotGameTest {

    /**
     * component under test
     */
    private BotGame CuT;

    Player red;
    Player white;

    /**
     * Setup for testing BotGame
     */
    @BeforeEach
    public void setup() {
        red = new Player("red-bot", true);
        white = new Player("white-player");
        CuT = new BotGame(red, white);
    }

    /**
     * Test if instance is a BotGame
     */
    @Test
    public void isBotGame(){
        assertTrue(CuT instanceof BotGame);
    }

    /**
     * Test if Red Bot submitted turn
     */
    @Test
    public void takeFirstTurn(){
        assertTrue(CuT.isMyTurn(white));
    }

    /**
     * Test red turn
     */
    @Test
    public void redTurn(){
        CuT.switchCurrentTurn();
        //red will make its move in switch current turn and 
        //it will be white players turn again
        assertFalse(CuT.isMyTurn(red));
    }

    /**
     * Test if a bot won a game
     */
    @Test
    public void testGameWon(){
        Player red = new Player("red", true);
        Player white = new Player("white", true);
        CuT = new BotGame(red, white);

        assertTrue(CuT.getWinner() != GameWinner.ingame);

    }

}