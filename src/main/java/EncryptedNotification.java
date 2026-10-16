import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptedNotification extends Notification {
    private String lastEncryptedMessage;
    private boolean lastSendSuccessful;

    public EncryptedNotification(MessageSender sender) {
        super(sender);
    }

    public String encrypt(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));
    }

    public String getLastEncryptedMessage() { return lastEncryptedMessage; }
    public boolean wasLastSendSuccessful() { return lastSendSuccessful; }

    @Override
    public void send(String recipient, String message) {
        lastEncryptedMessage = encrypt(message);
        lastSendSuccessful = sender != null && sender.isAvailable()
                && sender.sendMessage(recipient, "Encrypted Notification", lastEncryptedMessage);
    }
}
