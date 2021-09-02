public class Producer extends Thread {

    private final int TIME_CREATE = 2000;

    public void createCar() {
        try {
            System.out.printf("Собираю автомобиль для %s\n", Thread.currentThread().getName());
            sleep(TIME_CREATE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            notify();
            Thread.currentThread().interrupt();
        }
    }

    public void sellCar() {

        boolean isBuy = false;

        System.out.printf("Поток %s хочет купить автомобиль\n", Thread.currentThread().getName());
        while (!isBuy) {
            System.out.println("Автомобилей в наличии нет");
            new Thread(this::createCar, Thread.currentThread().getName()).start();
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Поток %s купил автомобиль\n", Thread.currentThread().getName());
                isBuy = true;
            }
        }
    }

}