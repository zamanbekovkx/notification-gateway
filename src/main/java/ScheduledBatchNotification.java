import java.util.ArrayList;
import java.util.List;

public class ScheduledBatchNotification extends Notification {
    private final List<String> recipients = new ArrayList<>();
    private int successful;
    private int failed;

    public ScheduledBatchNotification(MessageSender sender) {
        super(sender);
    }

    public ScheduledBatchNotification(MessageSender sender, List<String> recipients) {
        super(sender);
        this.recipients.addAll(recipients);
    }

    public void addRecipient(String recipient) { recipients.add(recipient); }
    public List<String> getRecipients() { return List.copyOf(recipients); }
    public int getSuccessful() { return successful; }
    public int getFailed() { return failed; }

    public void sendBatch(String message) {
        successful = 0;
        failed = 0;
        for (String recipient : recipients) {
            boolean ok = sender != null && sender.isAvailable()
                    && sender.sendMessage(recipient, "Scheduled Batch", message);
            if (ok) successful++; else failed++;
        }
        printSummary();
    }

    private void printSummary() {
        System.out.println("Total: " + recipients.size());
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
    }

    @Override
    public void send(String recipient, String message) {
        if (recipient != null && !recipient.isBlank()) recipients.add(recipient);
        sendBatch(message);
    }
}
