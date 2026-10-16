public class MarketingNotification extends Notification {
    private boolean lastSendSuccessful;

    public MarketingNotification(MessageSender sender) {
        super(sender);
    }

    public boolean wasLastSendSuccessful() { return lastSendSuccessful; }

    @Override
    public void send(String recipient, String message) {
        String body = "[MARKETING] " + message + " | Special offer!";
        lastSendSuccessful = sender != null && sender.isAvailable()
                && sender.sendMessage(recipient, "Marketing", body);
    }
}
