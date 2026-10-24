
public class BankService {

    public void deposit(Account account, long amount)
            throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        account.deposit(amount);
    }

    public void withdraw(Account account, long amount)
            throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        long balance = account.getBalance();

        if (!account.canWithdraw(amount)) {
            long shortfall = amount - balance;
            if (shortfall < 1) {
                shortfall = 1;
            }
            throw new InsufficientFundsException(shortfall);
        }

        account.withdraw(amount);
    }

    public void transfer(Account from, Account to, long amount)
            throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        try {
            withdraw(from, amount);
            to.deposit(amount);
        } catch (BankException e) {
            throw e;
        } finally {
            System.out.println("Transfer operation completed.");
        }
    }

    public Account findAccount(Account[] accounts, String accountNumber)
            throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account != null &&
                account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }

        throw new AccountNotFoundException(accountNumber);
    }
}
