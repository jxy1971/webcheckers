package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.Player.*;

/**
 * Test Player
 */
public class PlayerTest {

    final String name = "Test";
    private Player p = new Player(name);
    Move m = new Move(new Position(1,1), new Position(2,2));

    /**
     * Test toString
     */
    @Test
    void testToString() {
        assertNull(p.toString());
        p.setStateWaiting();
        assertEquals(p.toString(), PlayerState.WAITING.name()+"   ");
        p.setStateIngame();
        assertEquals(p.toString(), PlayerState.INGAME.name()+"    ");
        p.setStateChallenged();
        assertEquals(p.toString(), PlayerState.CHALLENGED.name());
    }

    /**
     * Test isInGame
     */
    @Test
    void testIsInGame() {
        p.setStateIngame();
        assertEquals(p.isInGame(), true);
    }

    /**
     * Test making a bot
     */
    @Test
    void testBotConstructor(){
        Player bot = new Player("Bot1",true);
        assertEquals(bot.getName(),"Bot1");
        assertTrue(bot.isBot());
    }
}
