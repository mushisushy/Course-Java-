import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class myDispatcher implements Dispatcher {
    private final BlockingQueue<Taxi> idleTaxis = new LinkedBlockingQueue<>();
    private final BlockingQueue<Order> ordersToDo = new LinkedBlockingQueue<>();

    @Override
    public void markTaxiAvail(Taxi taxi) {
        try {
            idleTaxis.put(taxi);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted!");
        }
    }

    @Override
    public void placeOrder(Order order) {
        try {
            ordersToDo.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted!");
        }
    }

    @Override
    public void beginOrderAssig() {
        new Thread(() -> {
            while (true) {
                try {
                    Order order = ordersToDo.take();
                    Taxi taxi = idleTaxis.take();
                    if (taxi instanceof myTaxi) {
                        ((myTaxi) taxi).assignOrder(order);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupted!");
                }
            }
        }).start();
    }
}
