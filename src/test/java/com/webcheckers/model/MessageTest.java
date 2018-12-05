package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.Board.*;

/**
 * Test Message
 */
public class MessageTest {

    private String s = "Test";
    private Message message = new Message(s, MessageType.info);

    /**
     * Test getText
     */
    @Test
    void testGetText() {
        assertEquals(message.getText(), s);
    }

    /**
     * Test getType
     */
    @Test
    void testGetType() {
        assertEquals(message.getType(), MessageType.info);
    }
    
}
