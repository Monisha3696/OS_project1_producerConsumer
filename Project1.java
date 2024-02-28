public class ProducerConsumerExample {
    private static final int bSize = 20; // Buffer size
    private static final int items = 30; // Number of items to produce/consume
    private final int[] buffer = new int[bSize];
    private int incoming = 0;
    private int outgoing = 0;
    private int sum = 0;

    public static void main(String[] args) {
        ProducerConsumerExample example = new ProducerConsumerExample();
        Thread[] producers = new Thread[2];
        Thread[] consumers = new Thread[2];

        for (int i = 0; i < 2; i++) {
            producers[i] = new Thread(() -> example.producerFunction());
            consumers[i] = new Thread(() -> example.consumerFunction());
        }

        for (int i = 0; i < 2; i++) {
            producers[i].start();
            consumers[i].start();
        }

        try {
            for (int i = 0; i < 2; i++) {
                producers[i].join();
                consumers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nThe Sum Value is " + example.getSum() + "\n");
    }

    public synchronized void producerFunction() {
        for (int value = 1; value <= items; value++) {
            while ((incoming + 1) % bSize == outgoing) {
                try {
                    wait(); // Buffer full, wait for space
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            buffer[incoming] = value;
            incoming = (incoming + 1) % bSize;
            notifyAll(); // Notify other threads that there's new data
        }
    }

    public synchronized void consumerFunction() {
        for (int value = 1; value <= items; value++) {
            while (incoming == outgoing) {
                try {
                    wait(); // Buffer empty, wait for data
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            int pickedValue = buffer[outgoing];
            sum += pickedValue;
            outgoing = (outgoing + 1) % bSize;
            notifyAll(); // Notify other threads that there's space in the buffer
        }
    }

    public synchronized int getSum() {
        return sum;
    }
}
