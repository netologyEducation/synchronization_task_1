import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

    private final int TIME_CREATE = 2000;

    private AtomicInteger availableCars = new AtomicInteger(0);

    private AtomicInteger waitingCustomers = new AtomicInteger(0);

    public void createCar() {
            try {
                System.out.printf("Собираю автомобиль для %s\n", Thread.currentThread().getName());
                sleep(TIME_CREATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                availableCars.incrementAndGet();
                notify();
                Thread.currentThread().interrupt();
            }
        }

    public void sellCar() {

        boolean isBuy = false;

        waitingCustomers.incrementAndGet();
        System.out.printf("Поток %s хочет купить автомобиль\n", Thread.currentThread().getName());
        while (!isBuy) {
                System.out.println("Автомобилей в наличии нет");
                new Thread(this::createCar).start();
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitingCustomers.getAndDecrement();
                availableCars.getAndDecrement();
                System.out.printf("Поток %s купил автомобиль\n", Thread.currentThread().getName());
                isBuy = true;
            }
        }
    }

}