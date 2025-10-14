import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BankingSystem {
    private final Map<String, User> users;
    private static final String USERS_FILE = "users.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    
    public BankingSystem() {
        users = new HashMap<>();
        loadData();
    }
    
    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.verifyPassword(password)) {
            return user;
        }
        return null;
    }
    
    public boolean createAccount(String username, String password, String fullName, double initialDeposit) {
        if (users.containsKey(username)) {
            return false;
        }
        
        Account account = new Account(initialDeposit);
        User user = new User(username, password, fullName, account);
        users.put(username, user);
        saveData();
        return true;
    }
    
    public void saveData() {
        saveUsers();
        saveAccounts();
        saveTransactions();
    }
    
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                writer.write(String.format("%s|%s|%s|%s%n",
                           user.getUsername(),
                           user.getPasswordHash(),
                           user.getFullName(),
                           user.getAccount().getAccountNumber()));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (User user : users.values()) {
                Account account = user.getAccount();
                writer.write(String.format("%s|%s|%.2f%n",
                           user.getUsername(),
                           account.getAccountNumber(),
                           account.getBalance()));
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
    
    private void saveTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (User user : users.values()) {
                Account account = user.getAccount();
                for (Transaction transaction : account.getTransactionHistory()) {
                    writer.write(String.format("%s|%s%n",
                               account.getAccountNumber(),
                               transaction.toFileString()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }
    
    private void loadData() {
        Map<String, Account> accounts = loadAccounts();
        loadTransactions(accounts);
        loadUsers(accounts);
    }
    
    private void loadUsers(Map<String, Account> accounts) {
        File file = new File(USERS_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String username = parts[0];
                    String passwordHash = parts[1];
                    String fullName = parts[2];
                    String accountNumber = parts[3];
                    
                    Account account = accounts.get(accountNumber);
                    if (account != null) {
                        User user = new User(username, passwordHash, fullName, account, true);
                        users.put(username, user);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }
    
    private Map<String, Account> loadAccounts() {
        Map<String, Account> accounts = new HashMap<>();
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) return accounts;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxAccountNum = 1000;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String accountNumber = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    
                    Account account = new Account(accountNumber, balance);
                    accounts.put(accountNumber, account);
                    
                    // Extract account number for counter update
                    try {
                        int accNum = Integer.parseInt(accountNumber.substring(3));
                        maxAccountNum = Math.max(maxAccountNum, accNum);
                    } catch (NumberFormatException e) {
                        // Skip if account number format is invalid
                    }
                }
            }
            Account.setAccountCounter(maxAccountNum + 1);
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }
    
    private void loadTransactions(Map<String, Account> accounts) {
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String accountNumber = parts[0];
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    String description = parts[3];
                    String timestamp = parts[4];
                    
                    Account account = accounts.get(accountNumber);
                    if (account != null) {
                        Transaction transaction = new Transaction(type, amount, description, timestamp);
                        account.addTransaction(transaction);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
    }
}