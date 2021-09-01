public class Main {
    public static void main(String[] args) {
        Producer producer = new Producer();
        ThreadGroup customerGroup = new ThreadGroup("Customers");
        ThreadGroup salesGroup = new ThreadGroup("Sellers");

        for (int i = 0; i < 10; i++) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            new Thread(customerGroup, producer::sellCar, "Customer-" + i).start();
        }

//        new Thread(salesGroup, producer::createCar, "Sellers").start();

    }
}
