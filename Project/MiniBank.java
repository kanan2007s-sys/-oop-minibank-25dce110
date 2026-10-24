
import java.util.Scanner;

public class MiniBank {

    record BankInfo(String name, String branch) {}

    enum MenuOption {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
    }

    private static Account[] accounts = new Account[100];
    private static int accountCount = 0;

    private static Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    private static void showAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }

        for (int i = 0; i < accountCount; i++) {
            System.out.println(accounts[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankInfo bank = new BankInfo("MiniBank", "CHARUSAT");

        System.out.println("-----------------------------");
        System.out.println(bank.name() + " - " + bank.branch());
        System.out.println("-----------------------------");

        while (true) {
            System.out.println("\n1. Open account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View accounts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Enter a valid number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            MenuOption option;

            switch (choice) {
                case 1 -> option = MenuOption.OPEN_ACCOUNT;
                case 2 -> option = MenuOption.DEPOSIT;
                case 3 -> option = MenuOption.WITHDRAW;
                case 4 -> option = MenuOption.TRANSFER;
                case 6 -> option = MenuOption.EXIT;
                case 5 -> {
                    showAccounts();
                    continue;
                }
                default -> {
                    System.out.println("Invalid choice.");
                    continue;
                }
            }

            switch (option) {
                case OPEN_ACCOUNT -> {
                    if (accountCount >= accounts.length) {
                        System.out.println("Account limit reached.");
                        break;
                    }

                    System.out.print("Enter owner name: ");
                    String name = sc.nextLine();

                    System.out.println("1. Savings  2. Current  3. Fixed Deposit");
                    System.out.print("Choose account type: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid account type.");
                        sc.nextLine();
                        break;
                    }

                    int type = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter initial balance: ");
                    if (!sc.hasNextLong()) {
                        System.out.println("Invalid balance.");
                        sc.nextLine();
                        break;
                    }

                    long balance = sc.nextLong();
                    sc.nextLine();

                    if (balance < 0) {
                        System.out.println("Balance cannot be negative.");
                        break;
                    }

                    Account account;

                    if (type == 1) {
                        System.out.print("Enter minimum balance: ");
                        long minimum = sc.nextLong();
                        sc.nextLine();
                        account = new SavingsAccount(name, balance, minimum);
                    } else if (type == 2) {
                        System.out.print("Enter overdraft limit: ");
                        long limit = sc.nextLong();
                        sc.nextLine();
                        account = new CurrentAccount(name, balance, limit);
                    } else if (type == 3) {
                        account = new FixedDepositAccount(name, balance);
                    } else {
                        System.out.println("Invalid account type.");
                        break;
                    }

                    accounts[accountCount++] = account;
                    System.out.println("Account created successfully.");
                    System.out.println(account);
                }

                case DEPOSIT -> {
                    System.out.print("Enter account number: ");
                    String number = sc.nextLine();
                    Account account = findAccount(number);

                    if (account == null) {
                        System.out.println("Account not found.");
                        break;
                    }

                    System.out.print("Enter amount: ");
                    if (!sc.hasNextLong()) {
                        System.out.println("Invalid amount.");
                        sc.nextLine();
                        break;
                    }

                    long amount = sc.nextLong();
                    sc.nextLine();

                    if (amount <= 0) {
                        System.out.println("Amount must be positive.");
                    } else {
                        account.deposit(amount);
                        System.out.println("Deposit successful.");
                        System.out.println(account);
                    }
                }

                case WITHDRAW -> {
                    System.out.print("Enter account number: ");
                    String number = sc.nextLine();
                    Account account = findAccount(number);

                    if (account == null) {
                        System.out.println("Account not found.");
                        break;
                    }

                    System.out.print("Enter amount: ");
                    if (!sc.hasNextLong()) {
                        System.out.println("Invalid amount.");
                        sc.nextLine();
                        break;
                    }

                    long amount = sc.nextLong();
                    sc.nextLine();

                    if (account.withdraw(amount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Withdrawal denied.");
                    }
                    System.out.println(account);
                }

                case TRANSFER -> {
                    System.out.print("Enter source account number: ");
                    Account from = findAccount(sc.nextLine());

                    System.out.print("Enter destination account number: ");
                    Account to = findAccount(sc.nextLine());

                    if (from == null || to == null) {
                        System.out.println("Account not found.");
                        break;
                    }

                    System.out.print("Enter amount: ");
                    if (!sc.hasNextLong()) {
                        System.out.println("Invalid amount.");
                        sc.nextLine();
                        break;
                    }

                    long amount = sc.nextLong();
                    sc.nextLine();

                    if (from == to || amount <= 0) {
                        System.out.println("Invalid transfer.");
                    } else if (from.withdraw(amount)) {
                        to.deposit(amount);
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer denied.");
                    }
                }

                case EXIT -> {
                    System.out.println("Thank you for using MiniBank.");
                    sc.close();
                    return;
                }
            }
        }
    }
}
