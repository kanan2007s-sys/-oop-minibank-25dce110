
public class AccountWorker implements Runnable {

    private final Account account;
    private final int repetitions;
    private final long amount;

    public AccountWorker(Account account, int repetitions, long amount) {
        this.account = account;
        this.repetitions = repetitions;
        this.amount = amount;
    }

    @Override
    public void run() {
        for (int i = 0; i < repetitions; i++) {
            account.deposit(amount);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new CurrentAccount("Test", 0, 1000000);

        Thread t1 = new Thread(new AccountWorker(account, 1000, 10));
        Thread t2 = new Thread(new AccountWorker(account, 1000, 10));
        Thread t3 = new Thread(new AccountWorker(account, 1000, 10));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final balance: " + account.getBalance());
    }
}
