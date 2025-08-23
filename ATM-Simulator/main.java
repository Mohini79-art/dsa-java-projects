
import java.util.*;

class Main {
    public static class Account {
        private String user_name;
        private int pin;
        private double balance;
        private List<String> transactionHistory;

        public Account(String user_name, int pin, double balance) {
            this.user_name = user_name;
            this.pin = pin;
            this.balance = balance;
            this.transactionHistory = new ArrayList<>();
        }

        public String getUsername() {
            return user_name;
        }

        public boolean pinAuthentication(int entered_pin) {
            if (pin == entered_pin) {
                System.out.println("Access Granted! You are logged in.");
                return true;
            } else {
                System.out.println("Invalid PIN! Access Denied.");
                return false;
            }
        }

        public void readyTransaction() {
            Scanner sc = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("\nSelect your transaction:");
                System.out.println("1 for Cash Withdrawal");
                System.out.println("2 for Cash Deposit");
                System.out.println("3 for Balance Check");
                System.out.println("4 to Exit");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Enter amount to withdraw:");
                        int withdraw = sc.nextInt();
                        if (balance >= withdraw) {
                            balance -= withdraw;
                            System.out.println("Your current balance after withdrawal: " + balance);
                            transactionHistory.add("Withdraw: " + withdraw);
                        } else {
                            System.out.println("Insufficient balance!");
                        }
                        break;

                    case 2:
                        System.out.println("Enter amount to deposit:");
                        int deposit = sc.nextInt();
                        balance += deposit;
                        System.out.println("Your current balance after deposit: " + balance);
                        transactionHistory.add("Deposit: " + deposit);
                        break;

                    case 3:
                        System.out.println("Your current balance is: " + balance);
                        break;

                    case 4:
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }

        public void checkTransactionHistory() {
            System.out.println("\nTransaction History:");
            if (transactionHistory.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                for (String t : transactionHistory) {
                    System.out.println(t);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account acc = new Account("Mohini", 1234, 10000.0);

        System.out.println("Username: " + acc.getUsername());
        System.out.println("Welcome to ATM. Enter your PIN:");
        int entered_pin = sc.nextInt();

        if (acc.pinAuthentication(entered_pin)) {
            acc.readyTransaction();
            acc.checkTransactionHistory();
        }
    }
}
