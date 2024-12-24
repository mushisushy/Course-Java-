public class myApp {
    public static void main(String[] args) {
        Dispatcher dispatcher = new myDispatcher();

        for (int i = 1; i <= 5; i++) {
            Taxi taxi = new myTaxi("Taxi-" + i, dispatcher);
            dispatcher.markTaxiAvail(taxi);
            ((myTaxi) taxi).start();
        }

        dispatcher.beginOrderAssig();
        for (int i = 1; i <= 27; i++) {
            dispatcher.placeOrder(new Order("Order-" + i));
        }
    }
}
