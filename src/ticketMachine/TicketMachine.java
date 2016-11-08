package ticketMachine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;

/**
 * class for managing the transactions
 */
public class TicketMachine extends Observable {
    private ArrayList<Car> cars = new ArrayList<Car>();
    private int fee = 10;

    public int getFee() {
        return fee;
    }

    /**
     * sets fee
     *
     * @param amount cent/minute
     */
    public void setFee(int amount) {
        fee = amount;
        setChanged();
        notifyObservers();
    }

    /**
     * adds car to the cars
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * gives a cars with cars that are paid for at this moment
     */
    public ArrayList<Car> giveCarsPaidFor() {
        ArrayList<Car> carsPaidFor = new ArrayList<Car>();
        for (Car c : cars) {
            if (c.getEndTime().isAfter(LocalDateTime.now())) {
                carsPaidFor.add(c);
            }
        }
        return carsPaidFor;
    }

    /**
     * gives the endTime of this car
     */
    public LocalDateTime giveEndTime(String licenceplate) throws CarNotInListException {
        LocalDateTime endTime = null;
        for (Car c : cars) {
            if (c.getLicencePlate().equals(licenceplate)) {
                endTime = c.getEndTime();
            }
        }
        if (endTime == null) {
            throw new CarNotInListException("a car With this licenceplate is not in the list");
        } else {
            return endTime;
        }
    }


    /**
     * starts transaction
     */
    public TransactionBuilder startTransaction() {
        TransactionBuilder tb = new TransactionBuilder(fee);
        return tb;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}
