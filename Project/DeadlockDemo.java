
public class DeadlockDemo {

    public static void transfer(Account first, Account second, long amount) {
        Account lower = first.getAccountNumber()
                .compareTo(second.getAccountNumber()) < 0 ? first : second;

        Account higher = lower == first ? second : first;

        synchronized (lower) {
            synchronized (higher) {
                if (first.canWithdraw(amount)) {
                    first.withdraw(amount);
                    second.deposit(amount);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new CurrentAccount("A", 1000, 1000);
        Account b = new CurrentAccount("B", 1000, 1000);

        Thread t1 = new Thread(() -> transfer(a, b, 100));
        Thread t2 = new Thread(() -> transfer(b, a, 100));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(a);
        System.out.println(b);
    }
}
