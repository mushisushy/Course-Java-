import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class myTaxi extends Thread implements Taxi {

    private final int timeSeed = ThreadLocalRandom.current().nextInt(1000, 6000);
    private final String idTaxi;
    private final Dispatcher dispatcher;
    private final BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public myTaxi(String idTaxi, Dispatcher dispatcher) {
        this.idTaxi = idTaxi;
        this.dispatcher = dispatcher;
    }

    @Override
    public void executeOrder(Order order) {
        try {
            System.out.println("Taxi " + idTaxi + " is executing order: " + order);
            Thread.sleep(timeSeed);
            order.setStatus(OrderStatus.COMPLETE);
            System.out.println("Taxi " + idTaxi + " completed order: " + order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void informAvailability() {
        dispatcher.markTaxiAvail(this);
    }

    @Override
    public void run() {
        System.out.println("Taxi " + idTaxi + " is ready for orders! \uD83D\uDE95");
        while (true) {
            try {
                Order order = orderQueue.take();
                executeOrder(order);
                informAvailability();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted!");
            }
        }
    }

    public void assignOrder(Order order) {
        try {
            orderQueue.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted!");
        }
    }
}
