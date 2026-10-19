import java.util.Scanner;

public class MiniBank {

    record BankInfo(String name, String branch) {}

    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("Minibank", "CHARUSAT");

        Customer c1 = new Customer(
                "Riya",
                "riya@gmail.com",
                "9876543210"
        );

        Customer.Address address =
                new Customer.Address(
                        "CHARUSAT Road",
                        "Changa",
                        "388421"
                );

        c1.setAddress(address);

        Customer c2 = c1.clone();

        System.out.println("Customer ID: " + c1.getCustomerId());
        System.out.println("Customer Name: " + c1.getName());
        System.out.println("City: " + c1.getAddress().getCity());
        System.out.println("Clone Customer ID: " + c2.getCustomerId());


        Account[] accounts = new Account[3];

        accounts[0] =
                new SavingsAccount("Riya", 10000, 2000);

        accounts[1] =
                new CurrentAccount("Rahul", 5000, 10000);

        accounts[2] =
                new FixedDepositAccount("Kanan", 20000);


        System.out.println("\n--- Account Details ---");

        for (Account account : accounts) {

            System.out.println(account);

            System.out.println(
                    "Interest Rate: " +
                    account.interestRate() +
                    "%"
            );

            System.out.println(
                    "Can withdraw 5000: " +
                    account.canWithdraw(5000)
            );

            System.out.println();
        }


        System.out.println("--- Withdrawal Test ---");

        System.out.println(
                "Savings withdrawal: " +
                accounts[0].withdraw(5000)
        );

        System.out.println(
                "Current withdrawal: " +
                accounts[1].withdraw(10000)
        );

        System.out.println(
                "Fixed Deposit withdrawal: " +
                accounts[2].withdraw(5000)
        );


        System.out.println("\n--- instanceof Test ---");

        for (Account account : accounts) {

            if (account instanceof SavingsAccount) {
                System.out.println(
                        account.getOwnerName() +
                        " is a Savings Account"
                );
            }

            if (account instanceof CurrentAccount) {
                System.out.println(
                        account.getOwnerName() +
                        " is a Current Account"
                );
            }

            if (account instanceof FixedDepositAccount) {
                System.out.println(
                        account.getOwnerName() +
                        " is a Fixed Deposit Account"
                );
            }
        }


        System.out.println("\n--- Bank ---");

        System.out.println("-----------------------------");
        System.out.println(bank.name() + "-" + bank.branch());
        System.out.println("-----------------------------");


        while (true) {

            System.out.println("\n1.Open account.");
            System.out.println("2.Deposit.");
            System.out.println("3.Withdraw.");
            System.out.println("4.Transfer.");
            System.out.println("5.Exit.");

            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();

            MenuOption option;

            switch (choice) {

                case 1:
                    option = MenuOption.OPEN_ACCOUNT;
                    break;

                case 2:
                    option = MenuOption.DEPOSIT;
                    break;

                case 3:
                    option = MenuOption.WITHDRAW;
                    break;

                case 4:
                    option = MenuOption.TRANSFER;
                    break;

                case 5:
                    option = MenuOption.EXIT;
                    break;

                default:
                    option = null;
            }

            if (option == null) {
                System.out.println("Invalid input choice.");
                continue;
            }

            switch (option) {

                case OPEN_ACCOUNT ->
                        System.out.println("Open account");

                case DEPOSIT ->
                        System.out.println("Deposit");

                case WITHDRAW ->
                        System.out.println("Withdraw");

                case TRANSFER ->
                        System.out.println("Transfer");

                case EXIT -> {
                    System.out.println(
                            "Thank you for using MiniBank."
                    );
                    sc.close();
                    return;
                }
            }
        }
    }
}