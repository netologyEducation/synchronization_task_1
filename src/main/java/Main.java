public class Main {
    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer();
        ThreadGroup customerGroup = new ThreadGroup("Customers");
        ThreadGroup salesGroup = new ThreadGroup("Sellers");

        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(customerGroup, producer::sellCar, "Customer-" + i).start();
        }

        new Thread(salesGroup, producer::createCar, "Sellers").start();

    }
}
