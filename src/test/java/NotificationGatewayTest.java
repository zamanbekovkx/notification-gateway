import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NotificationGatewayTest {

    @Test
    void successfulTelegramDelivery() {
        MessageSender telegram = new TelegramMessageSender(true);
        assertTrue(telegram.sendMessage("user", "Title", "Hello"));
    }

    @Test
    void unavailableProviderFails() {
        MessageSender telegram = new TelegramMessageSender(false);
        assertFalse(telegram.sendMessage("user", "Title", "Hello"));
    }

    @Test
    void urgentUsesFallback() {
        UrgentNotification n = new UrgentNotification(
                new TelegramMessageSender(false), new SmsMessageSender(true));
        n.send("+7701", "Help");
        assertTrue(n.wasLastSendSuccessful());
    }

    @Test
    void smsRejectsLongMessage() {
        MessageSender sms = new SmsMessageSender(true);
        assertFalse(sms.sendMessage("+7701", "", "x".repeat(161)));
    }

    @Test
    void smsAccepts160Characters() {
        MessageSender sms = new SmsMessageSender(true);
        assertTrue(sms.sendMessage("+7701", "", "x".repeat(160)));
    }

    @Test
    void encryptedMessageIsBase64() {
        EncryptedNotification n = new EncryptedNotification(new EmailMessageSender(true));
        n.send("a@b.com", "secret");
        String expected = Base64.getEncoder().encodeToString("secret".getBytes(StandardCharsets.UTF_8));
        assertEquals(expected, n.getLastEncryptedMessage());
        assertTrue(n.wasLastSendSuccessful());
    }

    @Test
    void batchCountsSuccessfulMessages() {
        ScheduledBatchNotification n = new ScheduledBatchNotification(
                new SmsMessageSender(true), List.of("1", "2", "3"));
        n.sendBatch("Hi");
        assertEquals(3, n.getSuccessful());
        assertEquals(0, n.getFailed());
    }

    @Test
    void batchCountsFailures() {
        ScheduledBatchNotification n = new ScheduledBatchNotification(
                new SmsMessageSender(false), List.of("1", "2"));
        n.sendBatch("Hi");
        assertEquals(0, n.getSuccessful());
        assertEquals(2, n.getFailed());
    }

    @Test
    void runtimeSenderSwitchingWorks() {
        UrgentNotification n = new UrgentNotification(new TelegramMessageSender(true));
        assertEquals("Telegram", n.getSender().getChannelName());
        n.setSender(new EmailMessageSender(true));
        assertEquals("Email", n.getSender().getChannelName());
    }

    @Test
    void pushSenderWorks() {
        MessageSender push = new PushNotificationSender(true);
        assertTrue(push.sendMessage("device", "Push", "Hello"));
    }

    @Test
    void marketingWorksWithExistingSender() {
        MarketingNotification n = new MarketingNotification(new EmailMessageSender(true));
        n.send("a@b.com", "Sale");
        assertTrue(n.wasLastSendSuccessful());
    }

    @Test
    void urgentFailsWhenPrimaryAndFallbackUnavailable() {
        UrgentNotification n = new UrgentNotification(
                new TelegramMessageSender(false), new SmsMessageSender(false));
        n.send("user", "Problem");
        assertFalse(n.wasLastSendSuccessful());
    }
}
