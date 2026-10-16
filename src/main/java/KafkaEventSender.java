public class KafkaEventSender implements MessageSender {
    private boolean available;

    public KafkaEventSender() { this(true); }
    public KafkaEventSender(boolean available) { this.available = available; }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!available) return false;
        System.out.printf("[Kafka] event={recipient:'%s', title:'%s', body:'%s'}%n", recipient, title, body);
        return true;
    }

    @Override public boolean isAvailable() { return available; }
    @Override public String getChannelName() { return "Kafka"; }
}
