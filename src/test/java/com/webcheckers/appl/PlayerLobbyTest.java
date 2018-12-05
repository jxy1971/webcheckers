package com.webcheckers.appl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import com.webcheckers.model.Player;

/**
 * Tests PlayerLobby
 */
public class PlayerLobbyTest {
   private PlayerLobby lobby = new PlayerLobby();
   private Player Matt = new Player("Matt");

    /**
     * Tests the addplayer function, checks if size is 0, adds a
     * player, then check if size is 1
     */
    @Test
    void testAddPlayer(){
        assertEquals(lobby.getPlayers().size(),0);
        lobby.addPlayer(new Player("Kevin"));
        assertEquals(lobby.getPlayers().size(),1);
    }

    /**
     * Tests if username already exists,adds a player, and checks
     * if that name exists
     */
    @Test
    void testUserNameExists(){
        assertFalse(lobby.userNameExists("Kevin"));
        lobby.addPlayer(new Player("Kevin"));
        lobby.addPlayer(new Player("Matt"));
        assertTrue(lobby.userNameExists("Matt"));
    }

    /**
     * Tests if username doesn't exist
     */
    @Test
    void testGetPlayer(){
        assertFalse(lobby.userNameExists("Kevin"));
        assertNull(lobby.getPlayer("Kevin"));
        lobby.addPlayer(new Player("Kevin"));
        lobby.addPlayer(Matt);
        assertEquals(lobby.getPlayer("Matt"), Matt);
    }

    /**
     * Tests if username has special characters
     */
    @Test
    void testHasSpecialCharacters(){
        assertFalse(lobby.hasSpecialCharacter("Kevin"));
        assertTrue(lobby.hasSpecialCharacter("K3v!n"));
    }

    /**
     * Tests to remove a player
     */
    @Test
    void testRemovePlayer(){
        lobby.addPlayer(new Player("Kevin"));
        assertEquals(lobby.getPlayers().size(),1);
        lobby.removePlayer("Kevin");
        assertEquals(lobby.getPlayers().size(),0);
    }
    
}
