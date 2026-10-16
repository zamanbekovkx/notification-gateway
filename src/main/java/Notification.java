public abstract class Notification {
    protected MessageSender sender;

    protected Notification(MessageSender sender) {
        this.sender = sender;
    }

    public void setSender(MessageSender sender) {
        this.sender = sender;
    }

    public MessageSender getSender() {
        return sender;
    }

    public abstract void send(String recipient, String message);
}
