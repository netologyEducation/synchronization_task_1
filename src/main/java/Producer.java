import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

    private final int TIME_CREATE = 1000;

    private List<Car> availableCars = new ArrayList<>();

    AtomicInteger customersWaiting = new AtomicInteger(0);

    public synchronized void createCar() {

        while (customersWaiting.get() != 0) {
            try {
                Thread.sleep(TIME_CREATE);
                availableCars.add(new Car());
                notifyAll();
                customersWaiting.getAndDecrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void sellCar() {

        System.out.printf("Поток %s хочет купить автомобиль\n", Thread.currentThread().getName());
        customersWaiting.getAndIncrement();
        synchronized (this) {
            try {
                while (availableCars.isEmpty()) {
                    System.out.println("Автомобилей в наличии нет");
                    wait();
                }
                availableCars.remove(0);
                System.out.printf("Поток %s купил автомобиль\n", Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}