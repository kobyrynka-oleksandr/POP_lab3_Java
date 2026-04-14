import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int STORAGE_SIZE = 5;
    private static final int TOTAL_ITEMS = 20;
    private static final int NUM_PRODUCERS = 3;
    private static final int NUM_CONSUMERS = 2;

    public static void main(String[] args) throws InterruptedException {

        System.out.printf("Storage: %d | Total products: %d | Producers: %d | Consumers: %d%n%n",
                STORAGE_SIZE, TOTAL_ITEMS, NUM_PRODUCERS, NUM_CONSUMERS);

        Manager manager = new Manager(STORAGE_SIZE);

        int[] producerShares = distribute(TOTAL_ITEMS, NUM_PRODUCERS);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUM_PRODUCERS; i++) {
            threads.add(new Producer(i + 1, producerShares[i], manager).getThread());
        }

        int[] consumerShares = distribute(TOTAL_ITEMS, NUM_CONSUMERS);
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            threads.add(new Consumer(i + 1, consumerShares[i], manager).getThread());
        }
    }

    private static int[] distribute(int total, int parts) {
        int[] shares = new int[parts];
        int base = total / parts;
        int remainder = total % parts;
        for (int i = 0; i < parts; i++) {
            shares[i] = base + (i < remainder ? 1 : 0);
        }
        return shares;
    }
}