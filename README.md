# Secure Banking Application

## Project Title
Secure Banking Application

## Description
A console-based banking application built with Java that demonstrates Object-Oriented Programming principles and secure coding practices. The application provides basic banking operations with secure data persistence.

## Student Details
- **Name:** Tanyaradza Gamba
- **Registration Number:** H240309Y

## Features Implemented

###  User Authentication
- Secure user registration and login system
- Password hashing using SHA-256 with salt
- Session management

###  Account Management
- Create new bank accounts
- View account balance with proper authorization
- Account ownership validation

###  Transaction Processing
- Deposit funds with validation
- Withdraw funds with balance checking
- Transaction history tracking

###  Data Persistence
- All data stored in text files
- Automatic loading on application start
- Real-time saving of changes
- Secure file-based storage for:
  - User credentials (users.txt)
  - Account details (accounts.txt)
  - Transaction history (transactions.txt)

###  Security Features
- Password hashing and salting
- Input validation
- Authorization checks for all operations
- Secure data serialization

###  Error Handling
- Comprehensive exception handling
- User-friendly error messages
- Data integrity checks

## Technical Implementation

### OOP Principles Demonstrated
- **Encapsulation:** All class fields are private with public getters/setters
- **Inheritance:** Ready for extension with different account types
- **Polymorphism:** Service classes can be extended for different implementations
- **Abstraction:** Clean separation between models, services, and utils

### Secure Coding Practices
- Password hashing with salt
- Input validation
- Principle of least privilege
- Secure file handling
- Error handling without information leakage

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/murphy0021/SecureBankingApp-H240309Y.git
