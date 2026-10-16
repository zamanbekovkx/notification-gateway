public class SmsMessageSender implements MessageSender {
    public static final int MAX_LENGTH = 160;
    private boolean available;

    public SmsMessageSender() { this(true); }
    public SmsMessageSender(boolean available) { this.available = available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!available || body == null || body.length() > MAX_LENGTH) return false;
        System.out.printf("[SMS] To: %s | %s%n", recipient, body);
        return true;
    }

    @Override public boolean isAvailable() { return available; }
    @Override public String getChannelName() { return "SMS"; }
}
