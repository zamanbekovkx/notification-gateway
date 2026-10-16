public class EmailMessageSender implements MessageSender {
    private boolean available;

    public EmailMessageSender() { this(true); }
    public EmailMessageSender(boolean available) { this.available = available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!available) return false;
        System.out.printf("[Email] To: %s%nSubject: %s%n%s%n-- Notification Gateway%n", recipient, title, body);
        return true;
    }

    @Override public boolean isAvailable() { return available; }
    @Override public String getChannelName() { return "Email"; }
}
