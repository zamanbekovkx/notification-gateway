public class TelegramMessageSender implements MessageSender {
    private boolean available;

    public TelegramMessageSender() { this(true); }
    public TelegramMessageSender(boolean available) { this.available = available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!available) return false;
        System.out.printf("[Telegram] To: %s | %s | **%s**%n", recipient, title, body);
        return true;
    }

    @Override public boolean isAvailable() { return available; }
    @Override public String getChannelName() { return "Telegram"; }
}
