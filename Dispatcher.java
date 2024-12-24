
public interface Dispatcher {
    void markTaxiAvail(Taxi taxi);
    void placeOrder(Order order);
    void beginOrderAssig();
}