public class Consumer implements Runnable {

    private final int id;
    private final int itemCount;
    private final Manager manager;
    private final Thread thread;

    public Consumer(int id, int itemCount, Manager manager) {
        this.id = id;
        this.itemCount = itemCount;
        this.manager = manager;
        System.out.printf("[Consumer %d] Created. Will consume %d units of product.%n", id, itemCount);
        thread = new Thread(this, "Consumer-" + id);
        thread.start();
    }

    public Thread getThread() { return thread; }

    @Override
    public void run() {
        for (int i = 0; i < itemCount; i++) {
            try {
                manager.empty.acquire();
                manager.access.acquire();

                String item = manager.storage.remove(0);
                System.out.printf("[Consumer %d] Taken:  %-14s | Storage: %d%n",
                        id, item, manager.storage.size());

                manager.access.release();
                manager.full.release();

                // Thread.sleep((long) (Math.random() * 600 + 200));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.printf("[Consumer %d] Finished work.%n", id);
    }
}