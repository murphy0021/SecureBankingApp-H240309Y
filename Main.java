import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankingSystem system = new BankingSystem();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("===========================================");
        System.out.println("   SECURE BANKING APPLICATION");
        System.out.println("===========================================\n");
        
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = sc.nextLine().trim();
            
            switch (choice) {
                case "1" -> handleLogin(system, sc);
                case "2" -> handleAccountCreation(system, sc);
                case "3" -> {
                    System.out.println("\nThank you for using Secure Banking Application!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void handleLogin(BankingSystem system, Scanner sc) {
        System.out.print("\nEnter username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        User user = system.authenticateUser(username, password);
        if (user != null) {
            System.out.println("\n✓ Login successful! Welcome, " + user.getUsername());
            userMenu(system, user, sc);
        } else {
            System.out.println("\n✗ Invalid credentials. Please try again.");
        }
    }
    
    private static void handleAccountCreation(BankingSystem system, Scanner sc) {
        System.out.println("\n--- Create New Account ---");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();
        
        if (username.length() < 3) {
            System.out.println("✗ Username must be at least 3 characters.");
            return;
        }
        
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        if (password.length() < 6) {
            System.out.println("✗ Password must be at least 6 characters.");
            return;
        }
        
        System.out.print("Enter full name: ");
        String fullName = sc.nextLine().trim();
        
        System.out.print("Enter initial deposit amount: ");
        try {
            double initialDeposit = Double.parseDouble(sc.nextLine().trim());
            
            if (initialDeposit < 0) {
                System.out.println("✗ Initial deposit cannot be negative.");
                return;
            }
            
            if (system.createAccount(username, password, fullName, initialDeposit)) {
                System.out.println("\n✓ Account created successfully!");
            } else {
                System.out.println("\n✗ Username already exists. Please choose another.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid amount format.");
        }
    }
    
    private static void userMenu(BankingSystem system, User user, Scanner sc) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. View Transaction History");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            
            String choice = sc.nextLine().trim();
            
            switch (choice) {
                case "1" -> System.out.printf("\nCurrent Balance: $%.2f%n", user.getAccount().getBalance());
                case "2" -> handleDeposit(system, user, sc);
                case "3" -> handleWithdrawal(system, user, sc);
                case "4" -> user.getAccount().displayTransactionHistory();
                case "5" -> {
                    System.out.println("\nLogging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void handleDeposit(BankingSystem system, User user, Scanner sc) {
        System.out.print("\nEnter deposit amount: $");
        try {
            double amount = Double.parseDouble(sc.nextLine().trim());
            if (user.getAccount().deposit(amount)) {
                system.saveData();
                System.out.printf("✓ Successfully deposited $%.2f%n", amount);
                System.out.printf("New Balance: $%.2f%n", user.getAccount().getBalance());
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid amount format.");
        }
    }
    
    private static void handleWithdrawal(BankingSystem system, User user, Scanner sc) {
        System.out.print("\nEnter withdrawal amount: $");
        try {
            double amount = Double.parseDouble(sc.nextLine().trim());
            if (user.getAccount().withdraw(amount)) {
                system.saveData();
                System.out.printf("✓ Successfully withdrew $%.2f%n", amount);
                System.out.printf("New Balance: $%.2f%n", user.getAccount().getBalance());
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid amount format.");
        }
    }
}