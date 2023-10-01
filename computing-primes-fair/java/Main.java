import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// 26355867

public class Main {
    private static final int MAX_INT = 500000000;
    private static final int CONCURRENCY = 10;
    private static AtomicInteger totalPrimeNumbers = new AtomicInteger(0);
    private static AtomicInteger currentNum = new AtomicInteger(2);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY);

        for (int i = 0; i < CONCURRENCY; i++) {
            final int threadNum = i;
            executor.execute(() -> doWork(String.valueOf(threadNum)));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        System.out.println("checking till " + MAX_INT + " found " + (totalPrimeNumbers.get() + 1) +
                " prime numbers. took " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void doWork(String name) {
        long start = System.currentTimeMillis();

        while (true) {
            int x = currentNum.getAndIncrement();
            if (x > MAX_INT) {
                break;
            }
            checkPrime(x);
        }

        System.out.println("thread " + name + " completed in " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void checkPrime(int num) {
        if ((num & 1) == 0) {
            return;
        }

        for (int i = 3; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return;
            }
        }

        totalPrimeNumbers.incrementAndGet();
    }
}
