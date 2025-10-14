import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type; // DEPOSIT or WITHDRAWAL
    private final double amount;
    private final String description;
    private final LocalDateTime timestamp;
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }
    
    // Constructor for loading from file
    public Transaction(String type, double amount, String description, String timestamp) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
    }
    
    // Getters
    public String getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
        return timestamp.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("%-20s %-12s $%-9.2f %s",
                           getFormattedTimestamp(),
                           type,
                           amount,
                           description);
    }
    
    // For file persistence
    public String toFileString() {
        return String.format("%s|%.2f|%s|%s",
                           type,
                           amount,
                           description,
                           getFormattedTimestamp());
    }
}