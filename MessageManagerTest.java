
package com.mycompany.st10526621_part_3_poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MessageManagerTest {

    @TempDir
    Path tempDir;

    private MessageManager manager;

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @BeforeEach
    void setUp() {
        manager = new MessageManager(
                tempDir.resolve("storedMessages.json").toString()
        );

        message1 = new Message(
                "1234567890", 0, "John", "+27821234567",
                "Did you receive my cake?", Message.SENT
        );

        message2 = new Message(
                "2345678901", 1, "Mary", "+27831234567",
                "Where is the meeting?", Message.SENT
        );

        message3 = new Message(
                "3456789012", 2, "John", "+27821234567",
                "It is time to start.", Message.STORED
        );

        message4 = new Message(
                "4567890123", 3, "Peter", "+27841234567",
                "Please call me tomorrow.", Message.DISREGARD
        );

        message5 = new Message(
                "5678901234", 4, "Mary", "+27831234567",
                "Thank you for your help today.", Message.STORED
        );

        manager.addMessage(message1);
        manager.addMessage(message2);
        manager.addMessage(message3);
        manager.addMessage(message4);
        manager.addMessage(message5);
    }

    @Test
    void sentMessagesArrayCorrectlyPopulated() {
        assertEquals(2, manager.getSentMessages().size());
    }

    @Test
    void displayTheLongestMessage() {
        assertNotNull(manager.getLongestStoredMessage());
    }

    @Test
    void searchForMessageID() {
        assertNotNull(manager.searchByMessageID("1234567890"));
    }

    @Test
    void searchAllMessagesForARecipient() {
        assertNotNull(manager.searchByRecipient("+27821234567"));
    }

    @Test
    void deleteAMessageUsingAMessageHash() {
        String hash = message1.getMessageHash();

        manager.deleteByHash(hash);

        assertFalse(manager.getSentMessages().contains(message1));
    }

    @Test
    void displayReport() {
        assertNotNull(manager.displayReport());
    }

    @Test
    void disregardedStoredHashAndIdArraysArePopulated() {
        assertNotNull(manager.getStoredMessages());
        assertNotNull(manager.getDisregardedMessages());
        assertNotNull(manager.getMessageHashes());
        assertNotNull(manager.getMessageIDs());
    }

    @Test
    void storedMessagesAreReadBackFromJson() {
        manager.saveStoredMessages();

        MessageManager newManager = new MessageManager(
                tempDir.resolve("storedMessages.json").toString()
        );

        newManager.loadStoredMessages();

        assertFalse(newManager.getStoredMessages().isEmpty());
    }

    @Test
    void messageHashAndValidation() {
        assertNotNull(message1.getMessageHash());
        assertTrue(Message.isValidCell("+27821234567"));
        assertFalse(Message.isValidCell("12345"));
    }
}
