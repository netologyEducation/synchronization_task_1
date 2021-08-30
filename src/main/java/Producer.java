import java.util.ArrayList;
import java.util.List;

public class Producer extends Thread {

    private final int TIME_CREATE = 1000;

    private List<Car> availableCars = new ArrayList<>();

    private volatile int waitingCustomers = 0;

    public List<Car> getAvailableCars() {
        return availableCars;
    }

    public void createCar() {
        while (waitingCustomers != 0){
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
        ++waitingCustomers;
        try {
            while (availableCars.size() == 0) {
                synchronized (this) {
                    System.out.printf("Поток %s хотел купить автомобиль. Авто в продаже нет.\n", Thread.currentThread().getName());
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Поток %s купил машину\n", Thread.currentThread().getName());
        --waitingCustomers;
        availableCars.remove(0);
    }
}