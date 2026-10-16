public class UrgentNotification extends Notification {
    private MessageSender fallbackSender;
    private boolean lastSendSuccessful;

    public UrgentNotification(MessageSender sender) {
        super(sender);
    }

    public UrgentNotification(MessageSender sender, MessageSender fallbackSender) {
        super(sender);
        this.fallbackSender = fallbackSender;
    }

    public void setFallbackSender(MessageSender fallbackSender) {
        this.fallbackSender = fallbackSender;
    }

    public boolean wasLastSendSuccessful() {
        return lastSendSuccessful;
    }

    @Override
    public void send(String recipient, String message) {
        String urgentBody = "[URGENT] " + message;
        lastSendSuccessful = false;

        if (sender != null && sender.isAvailable()) {
            lastSendSuccessful = sender.sendMessage(recipient, "Urgent Notification", urgentBody);
        }

        if (!lastSendSuccessful && fallbackSender != null && fallbackSender.isAvailable()) {
            System.out.println("Primary unavailable/failed. Using fallback: " + fallbackSender.getChannelName());
            lastSendSuccessful = fallbackSender.sendMessage(recipient, "Urgent Notification", urgentBody);
        }

        if (!lastSendSuccessful) {
            System.out.println("Urgent message could not be delivered.");
        }
    }
}
