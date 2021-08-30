public class Main {
    public static void main(String[] args) {
        Producer producer = new Producer();
        ThreadGroup customerGroup = new ThreadGroup("Customer");
        ThreadGroup salesGroup = new ThreadGroup("seller");

        for (int i = 0; i < 10; i++) {
            new Thread(customerGroup, producer::sellCar, "Customer-" + i).start();
        }
        new Thread(salesGroup, producer::createCar, "Seller").start();
    }
}
