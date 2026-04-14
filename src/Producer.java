public class Producer implements Runnable {

    private final int id;
    private final int itemCount;
    private final Manager manager;
    private final Thread thread;

    public Producer(int id, int itemCount, Manager manager) {
        this.id = id;
        this.itemCount = itemCount;
        this.manager = manager;
        System.out.printf("[Producer %d] Created. Will produce %d units of product.%n", id, itemCount);
        thread = new Thread(this, "Producer-" + id);
        thread.start();
    }

    public Thread getThread() { return thread; }

    @Override
    public void run() {
        for (int i = 0; i < itemCount; i++) {
            try {
                manager.full.acquire();
                manager.access.acquire();

                String item = "P" + id + "-item" + i;
                manager.storage.add(item);
                System.out.printf("[Producer %d] Added: %-14s | Storage: %d%n",
                        id, item, manager.storage.size());

                manager.access.release();
                manager.empty.release();

                // Thread.sleep((long) (Math.random() * 600 + 200));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.printf("[Producer %d] Finished work.%n", id);
    }
}