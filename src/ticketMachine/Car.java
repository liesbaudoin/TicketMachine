package ticketMachine;

import java.time.LocalDateTime;

// class for saving the attributes of a transaction
public class Car {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String licencePlate;
    private int paidAmount;

    public Car(LocalDateTime startTime, LocalDateTime endTime, String licencePlate, int paidAmount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.licencePlate = licencePlate;
        this.paidAmount = paidAmount;

    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public int getPaidAmount() {
        return paidAmount;
    }


}
