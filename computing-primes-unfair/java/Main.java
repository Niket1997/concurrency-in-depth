import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// 26355867

public class Main {
    private static final int CONCURRENCY = 10;
    private static final int MAX_INT = 500000000;
    private static AtomicInteger totalPrimeNumbers = new AtomicInteger(0);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY);

        int nStart = 2;
        int batchSize = (int) ((double) MAX_INT / (double) CONCURRENCY);

        for (int i = 0; i < CONCURRENCY; i++) {
            final int threadNum = i;
            final int numStart = nStart;
            final int numEnd = nStart + batchSize;
            executor.execute(() -> doWork(threadNum, numStart, numEnd));
            nStart = numEnd;
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        System.out.println("checking till " + MAX_INT + " found " + totalPrimeNumbers.get() + 1
        + " prime numbers. took " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void doWork(int threadNum, int nStart, int nEnd) {
        long start = System.currentTimeMillis();

        for (int i = nStart; i < nEnd; i++) {
            checkPrime(i);
        }

        System.out.println("thread " + threadNum + " [" + nStart + ", " + nEnd + "] completed in "+ (System.currentTimeMillis() - start) + "ms");
    }

    private static void checkPrime(int num) {
        if ((num & 1) == 0) {
            return;
        }

        for (int i = 3; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return;
        }

        totalPrimeNumbers.incrementAndGet();
    }
}