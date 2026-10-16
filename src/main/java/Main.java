import java.util.List;

public class Main {
    public static void main(String[] args) {
        MessageSender telegram = new TelegramMessageSender(true);
        MessageSender email = new EmailMessageSender(true);
        MessageSender sms = new SmsMessageSender(true);
        MessageSender push = new PushNotificationSender(true);

        System.out.println("\n1) UrgentNotification + Telegram");
        Notification urgent = new UrgentNotification(telegram, sms);
        urgent.send("@olzhas", "Server is down");

        System.out.println("\n2) EncryptedNotification + Email");
        Notification encrypted = new EncryptedNotification(email);
        encrypted.send("student@example.com", "Secret message");

        System.out.println("\n3) ScheduledBatchNotification + SMS");
        ScheduledBatchNotification batch = new ScheduledBatchNotification(
                sms, List.of("+77010000001", "+77010000002", "+77010000003"));
        batch.sendBatch("Class starts at 10:00");

        System.out.println("\n4) Runtime switching: Telegram -> Email");
        Notification runtime = new UrgentNotification(telegram, sms);
        runtime.send("user1", "First channel");
        runtime.setSender(email);
        runtime.send("user1@example.com", "Second channel");

        System.out.println("\n5) Telegram unavailable -> fallback SMS");
        MessageSender unavailableTelegram = new TelegramMessageSender(false);
        Notification fallback = new UrgentNotification(unavailableTelegram, sms);
        fallback.send("+77010000004", "Fallback test");

        System.out.println("\n6) MarketingNotification + PushNotificationSender");
        Notification marketing = new MarketingNotification(push);
        marketing.send("device-123", "20% discount today");
    }
}
