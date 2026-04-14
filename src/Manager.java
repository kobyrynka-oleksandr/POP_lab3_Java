import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Manager {

    public final Semaphore access;
    public final Semaphore full;
    public final Semaphore empty;
    public final ArrayList<String> storage = new ArrayList<>();

    public Manager(int storageSize) {
        access = new Semaphore(1);
        full = new Semaphore(storageSize);
        empty = new Semaphore(0);
    }
}