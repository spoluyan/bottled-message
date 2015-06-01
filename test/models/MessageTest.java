package models;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import play.data.validation.Validation;
import play.test.UnitTest;

public class MessageTest extends UnitTest {
    @Before
    public void crearValidationErrors() {
        Validation.current().clear();
    }

    @Test
    public void testUUIDGeneration() {
        Message message = new Message(null, null, null, null, null, null);
        assertNotNull(message.uuid);
    }

    @Test
    public void testLongSenderName() {
        Message message = new Message(generateString(300), null, null, null, null, null);
        assertEquals(255, message.senderName.length());
    }

    @Test
    public void testLongSenderEmail() {
        Message message = new Message(null, generateString(300), null, null, null, null);
        assertEquals(255, message.senderEmail.length());
    }

    @Test
    public void testLongRecipientEmail() {
        Message message = new Message(null, null, generateString(300), null, null, null);
        assertEquals(255, message.recipientEmail.length());
    }

    @Test
    public void testLongText() {
        Message message = new Message(null, null, null, generateString(3000), null, null);
        assertEquals(2048, message.text.length());
    }

    @Test
    public void testImageLink() {
        Message message = new Message(null, null, null, null, generateString(3000), null);
        assertEquals(1024, message.imageLink.length());
    }

    @Test
    public void testVideoLink() {
        Message message = new Message(null, null, null, null, null, generateString(3000));
        assertEquals(1024, message.videoLink.length());
    }

    @Test
    public void testFindByUUID() {
        Message message = new Message(null, null, null, null, null, null).save();
        assertEquals(message, Message.findByUUID(message.uuid));
    }

    @Test
    public void testEmptySenderName() {
        Message message = new Message(null, "test@test.com", "test@test.com", null, "http://test.com",
                "http://test.com");
        message.validateAndSave();
        assertNull(message.id);
        assertNotEquals(0, Validation.current().errors());
        assertEquals("error-0", Validation.current().errors().get(0).getMessageKey());
    }

    @Test
    public void testEmptySenderEmail() {
        Message message = new Message("test", "", "test@test.com", null, "http://test.com", "http://test.com");
        message.validateAndSave();
        assertNull(message.id);
        assertNotEquals(0, Validation.current().errors());
        assertEquals("error-1", Validation.current().errors().get(0).getMessageKey());
    }

    @Test
    public void testInvalidSenderEmail() {
        Message message = new Message("test", "test@", "test@test.com", null, "http://test.com", "http://test.com");
        message.validateAndSave();
        assertNull(message.id);
        assertNotEquals(0, Validation.current().errors());
        assertEquals("error-2", Validation.current().errors().get(0).getMessageKey());
    }

    @Test
    public void testEmptyRecipientEmail() {
        Message message = new Message("test", "test@test.com", "", null, "http://test.com", "http://test.com");
        message.validateAndSave();
        assertNull(message.id);
        assertNotEquals(0, Validation.current().errors());
        assertEquals("error-3", Validation.current().errors().get(0).getMessageKey());
    }

    @Test
    public void testInvalidRecipientEmail() {
        Message message = new Message("test", "test@test.com", "test@", null, "http://test.com", "http://test.com");
        message.validateAndSave();
        assertNull(message.id);
        assertNotEquals(0, Validation.current().errors());
        assertEquals("error-4", Validation.current().errors().get(0).getMessageKey());
    }

    @Test
    public void testInvalidImageLink() {
        Message message = new Message("test", "test@test.com", "test@test.com", null, "http://test", "http://test.com");
        message.validateAndSave();
        assertNull(message.id);
        assertNotEquals(0, Validation.current().errors());
        assertEquals("error-5", Validation.current().errors().get(0).getMessageKey());
    }

    @Test
    public void testInvalidVideoLink() {
        Message message = new Message("test", "test@test.com", "test@test.com", null, "http://test.com", "http://test");
        message.validateAndSave();
        assertNull(message.id);
        assertNotEquals(0, Validation.current().errors());
        assertEquals("error-6", Validation.current().errors().get(0).getMessageKey());
    }

    private String generateString(int length) {
        char[] chars = new char[length];
        IntStream.range(0, length).forEach(i -> chars[i] = 'a');
        return new String(chars);
    }
}
