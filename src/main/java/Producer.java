import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

    private final int TIME_CREATE = 1000;

    private List<Car> availableCars = new ArrayList<>();

    private AtomicInteger waitingCustomers = new AtomicInteger(0);

    public List<Car> getAvailableCars() {
        return availableCars;
    }

    public void createCar() {
        while (waitingCustomers.get() != 0){
        try {
            System.out.println("Машин для продажи нет. Запускаю производство.");
            Thread.sleep(TIME_CREATE);
            System.out.println("Авто готово к продаже.");
            synchronized (this){
            availableCars.add(new Car());
            notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }}

    public void sellCar() {
        waitingCustomers.getAndIncrement();
        try {
            synchronized (this) {
            while (availableCars.size() == 0) {
                    System.out.printf("Поток %s хотел купить автомобиль. Авто в продаже нет.\n", Thread.currentThread().getName());
                    wait();
                    waitingCustomers.getAndDecrement();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Поток %s купил машину\n", Thread.currentThread().getName());
        availableCars.remove(0);
    }
}