import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String accountNumber;
    private double balance;
    private final List<Transaction> transactionHistory;
    private static int accountCounter = 1000;
    
    public Account(double initialBalance) {
        this.accountNumber = generateAccountNumber();
        this.balance = Math.max(0, initialBalance); // Ensure non-negative balance
        this.transactionHistory = new ArrayList<>();
        if (initialBalance > 0) {
            transactionHistory.add(new Transaction("DEPOSIT", initialBalance, "Initial deposit"));
        }
    }
    
    // Constructor for loading from file
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }
    
    private synchronized String generateAccountNumber() {
        return "ACC" + String.format("%06d", accountCounter++);
    }
    
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("✗ Deposit amount must be positive.");
            return false;
        }
        
        if (amount > 1000000) {
            System.out.println("✗ Deposit amount exceeds maximum limit ($1,000,000).");
            return false;
        }
        
        balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount, "Deposit"));
        return true;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("✗ Withdrawal amount must be positive.");
            return false;
        }
        
        if (amount > balance) {
            System.out.println("✗ Insufficient funds. Current balance: $" + 
                             String.format("%.2f", balance));
            return false;
        }
        
        balance -= amount;
        transactionHistory.add(new Transaction("WITHDRAWAL", amount, "Withdrawal"));
        return true;
    }
    
    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println(String.format("%-20s %-12s %-10s %s", 
                          "Date & Time", "Type", "Amount", "Description"));
        System.out.println("----------------------------------------------------------------");
        
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // Return copy for security
    }
    
    // For file loading
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }
    
    public static void setAccountCounter(int counter) {
        accountCounter = Math.max(counter, accountCounter);
    }
}