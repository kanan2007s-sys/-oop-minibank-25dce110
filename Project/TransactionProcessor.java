
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor implements AutoCloseable {

    private final ExecutorService executor =
        Executors.newFixedThreadPool(4);

    public void submit(Runnable transaction) {
        executor.submit(transaction);
    }

    public void stop() {
        executor.shutdown();
    }

    public void awaitCompletion() throws InterruptedException {
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }

    @Override
    public void close() {
        stop();

        try {
            awaitCompletion();
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Account account = new CurrentAccount("Kanan", 0, 1000000);

        try (TransactionProcessor processor = new TransactionProcessor()) {
            for (int i = 0; i < 100; i++) {
                processor.submit(() -> account.deposit(100));
            }
        }

        System.out.println("Final balance: " + account.getBalance());
    }
}
