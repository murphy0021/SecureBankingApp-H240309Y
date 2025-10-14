import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class User {
    private final String username;
    private final String passwordHash;
    private String fullName;
    private final Account account;
    
    public User(String username, String password, String fullName, Account account) {
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.fullName = fullName;
        this.account = account;
    }
    
    // Constructor for loading from file (password already hashed)
    public User(String username, String passwordHash, String fullName, Account account, boolean isHashed) {
        this.username = username;
        this.passwordHash = isHashed ? passwordHash : hashPassword(passwordHash);
        this.fullName = fullName;
        this.account = account;
    }
    
    // Secure password hashing using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found", e);
        }
    }
    
    public boolean verifyPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }
    
    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public Account getAccount() {
        return account;
    }
    
    // Setters with validation
    public void setFullName(String fullName) {
        if (fullName != null && !fullName.trim().isEmpty()) {
            this.fullName = fullName;
        }
    }
    
    @Override
    public String toString() {
        return "User{username='" + username + "', fullName='" + fullName + "'}";
    }
}