package ticketMachine;

import java.time.LocalDateTime;

/**
 * class for building a transaction
 */
public class TransactionBuilder {

    private LocalDateTime startTime = LocalDateTime.now();
    private String licencePlate;
    private int paidAmount;
    private int fee;

    /*
     * constructs a new Transactionbuilder
     *
     * @param fee Parking fee cent/ minute
     */
    public TransactionBuilder(int fee) {
        this.fee = fee;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    /*
     * increases paidAmount by adding or removing money aidAmount will never
     * be less then zero
     *
     * @param amount in cents
     */
    public void adjustMoney(int amount) {
        if (paidAmount + amount < 0) {
            return;
        } else {
            this.paidAmount = paidAmount + amount;
        }

    }

    public LocalDateTime calculateEndTime() {
        return this.startTime.plusMinutes(this.paidAmount / this.fee);
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    /**
     * puts attributes of this object in a new car object
     */
    public Car finishTransaction() {
       Car car = new Car(this.startTime, calculateEndTime(), this.licencePlate, this.paidAmount);
        return car;
    }

}