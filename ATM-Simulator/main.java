import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

// Main class
public class Main {

    // Account class
    public static class Account implements Serializable {
        private String username;
        private int pin;
        private double balance;
        private List<String> transactionHistory;
        private boolean isLocked;
        private int txnCounter = 0;

        public Account(String username, int pin, double balance) {
            this.username = username;
            this.pin = pin;
            this.balance = balance;
            this.transactionHistory = new ArrayList<>();
            this.isLocked = false;
        }

        // Getters & Setters
        public String getUsername() { return username; }
        public boolean isLocked() { return isLocked; }
        public void lockAccount() { isLocked = true; }
        public void unlockAccount() { isLocked = false; }

        public boolean pinAuthentication(int enteredPin) {
            return pin == enteredPin;
        }

        public void addTransaction(String type, double amount) {
            txnCounter++;
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String txnID = "TXN" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + txnCounter;
            String entry = timestamp + " | " + txnID + " | " + type + ": " + amount + " | Balance: " + balance;
            transactionHistory.add(entry);
            printSlip(type, amount, txnID, timestamp);
        }

        private void printSlip(String type, double amount, String txnID, String timestamp) {
            System.out.println("\n================== ATM RECEIPT ==================");
            System.out.println("🏦 MyBank ATM");
            System.out.println("ATM Location: Mathura Main Branch");
            System.out.println("Date/Time: " + timestamp);
            System.out.println("-----------------------------------------------");
            System.out.println("Account: XXXX" + username.substring(Math.max(username.length() - 4, 0)));
            System.out.println("Name: " + username);
            System.out.println("Transaction: " + type);
            System.out.println("Amount: " + amount);
            System.out.println("Balance: " + balance);
            System.out.println("Reference: " + txnID);
            System.out.println("-----------------------------------------------");
            System.out.println("Thank you for banking with us!");
            System.out.println("Do not share your PIN with anyone.");
            System.out.println("=================================================\n");
        }

        public void showTransactionHistory() {
            System.out.println("\n================ Transaction History for " + username + " ================");
            if (transactionHistory.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                for (String t : transactionHistory) {
                    System.out.println(t);
                }
            }
            System.out.println("=====================================================================");
        }

        public void performTransactions(Scanner sc, HashMap<String, Account> accounts) {
            boolean logout = false;
            while (!logout) {
                System.out.println("\n================== ATM MENU ==================");
                System.out.println("1. 💸 Cash Withdrawal");
                System.out.println("2. 💰 Cash Deposit");
                System.out.println("3. 🏦 Balance Check");
                System.out.println("4. 🔁 Fund Transfer");
                System.out.println("5. 📜 Transaction History");
                System.out.println("6. 🚪 Logout");
                System.out.println("==============================================");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to withdraw: ");
                        double withdraw = sc.nextDouble();
                        sc.nextLine();
                        if (balance >= withdraw) {
                            balance -= withdraw;
                            System.out.println("💸 Withdrawal successful! Current balance: " + balance);
                            addTransaction("Withdraw", withdraw);
                        } else {
                            System.out.println("⚠️ Insufficient balance!");
                        }
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double deposit = sc.nextDouble();
                        sc.nextLine();
                        balance += deposit;
                        System.out.println("💰 Deposit successful! Current balance: " + balance);
                        addTransaction("Deposit", deposit);
                        break;

                    case 3:
                        System.out.println("🏦 Current balance: " + balance);
                        break;

                    case 4:
                        System.out.print("Enter recipient username: ");
                        String recipientName = sc.nextLine();
                        if (!accounts.containsKey(recipientName)) {
                            System.out.println("❌ Recipient not found!");
                            break;
                        }
                        Account recipient = accounts.get(recipientName);
                        System.out.print("Enter amount to transfer: ");
                        double amount = sc.nextDouble();
                        sc.nextLine();
                        if (balance >= amount) {
                            balance -= amount;
                            recipient.balance += amount;
                            System.out.println("✅ Transfer successful! Your new balance: " + balance);
                            addTransaction("Transfer to " + recipientName, amount);
                            recipient.addTransaction("Received from " + username, amount);
                        } else {
                            System.out.println("⚠️ Insufficient balance!");
                        }
                        break;

                    case 5:
                        showTransactionHistory();
                        break;

                    case 6:
                        logout = true;
                        System.out.println("🚪 Logging out...");
                        break;

                    default:
                        System.out.println("❌ Invalid choice!");
                }
            }
        }
    }

    // Load accounts from file
    public static HashMap<String, Account> loadAccounts() {
        try {
            FileInputStream fis = new FileInputStream("accounts.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, Account> accounts = (HashMap<String, Account>) ois.readObject();
            ois.close();
            fis.close();
            return accounts;
        } catch (Exception e) {
            return new HashMap<>(); // If file doesn't exist, return empty map
        }
    }

    // Save accounts to file
    public static void saveAccounts(HashMap<String, Account> accounts) {
        try {
            FileOutputStream fos = new FileOutputStream("accounts.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(accounts);
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Account> accounts = loadAccounts();

        // Add some accounts if empty
        if (accounts.isEmpty()) {
            accounts.put("Mohini", new Account("Mohini", 1234, 10000.0));
            accounts.put("Rohit", new Account("Rohit", 1111, 5000.0));
            accounts.put("Anjali", new Account("Anjali", 2222, 8000.0));
        }

        boolean atmRunning = true;

        while (atmRunning) {
            System.out.println("\n==========================================");
            System.out.println("       💻 WELCOME TO THE ATM 💻");
            System.out.println("==========================================");
            System.out.print("Enter your username (or type 'exit' to quit / 'admin' for admin login): ");
            String username = sc.nextLine();

            if (username.equalsIgnoreCase("exit")) {
                atmRunning = false;
                saveAccounts(accounts);
                System.out.println("👋 ATM shutting down. Goodbye!");
                break;
            }

            if (username.equalsIgnoreCase("admin")) {
                // Admin login
                System.out.print("Enter admin password: ");
                String pass = sc.nextLine();
                if (!pass.equals("admin123")) {
                    System.out.println("❌ Incorrect admin password!");
                    continue;
                }
                System.out.println("✅ Admin logged in!");
                boolean adminMenu = true;
                while (adminMenu) {
                    System.out.println("\n--- Admin Menu ---");
                    System.out.println("1. View all accounts");
                    System.out.println("2. Unlock account");
                    System.out.println("3. Exit Admin");
                    System.out.print("Enter choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println("\nUsername | Balance | Locked");
                            for (Account a : accounts.values()) {
                                System.out.println(a.getUsername() + " | " + a.balance + " | " + a.isLocked());
                            }
                            break;
                        case 2:
                            System.out.print("Enter username to unlock: ");
                            String unlockUser = sc.nextLine();
                            if (accounts.containsKey(unlockUser)) {
                                accounts.get(unlockUser).unlockAccount();
                                System.out.println("✅ Account unlocked!");
                            } else {
                                System.out.println("❌ User not found!");
                            }
                            break;
                        case 3:
                            adminMenu = false;
                            System.out.println("Exiting Admin...");
                            break;
                        default:
                            System.out.println("❌ Invalid choice!");
                    }
                }
                saveAccounts(accounts);
                continue;
            }

            if (!accounts.containsKey(username)) {
                System.out.println("❌ Username not found!");
                continue;
            }

            Account currentAccount = accounts.get(username);

            if (currentAccount.isLocked()) {
                System.out.println("⚠️ Your account is locked due to multiple wrong PIN attempts.");
                continue;
            }

            int attempts = 0;
            boolean authenticated = false;
            while (attempts < 3) {
                System.out.print("Enter your PIN: ");
                int enteredPin = sc.nextInt();
                sc.nextLine();
                if (currentAccount.pinAuthentication(enteredPin)) {
                    System.out.println("✅ Access Granted! Welcome " + username);
                    authenticated = true;
                    break;
                } else {
                    attempts++;
                    System.out.println("❌ Invalid PIN! Attempts left: " + (3 - attempts));
                }
            }

            if (!authenticated) {
                System.out.println("⚠️ Too many wrong attempts! Account locked.");
                currentAccount.lockAccount();
            } else {
                currentAccount.performTransactions(sc, accounts);
            }

            saveAccounts(accounts); // save after each user session
        }
    }
}

