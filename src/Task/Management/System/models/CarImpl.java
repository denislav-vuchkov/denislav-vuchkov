package Task.Management.System.models;

import Task.Management.System.models.contracts.Car;
import Task.Management.System.models.enums.VehicleType;
import Task.Management.System.utils.ValidationHelpers;

import static java.lang.String.format;

public class CarImpl extends VehicleBase implements Car {

    public static final int CAR_WHEELS_NUMBER = 4;

    public static final int CAR_SEATS_MIN = 1;
    public static final int CAR_SEATS_MAX = 10;
    private static final String CAR_SEATS_ERR = format(
            "Seats must be between %d and %d!",
            CAR_SEATS_MIN,
            CAR_SEATS_MAX);

    private int seats;

    public CarImpl(String make, String model, double price, int seats) {
        super(make, model, price);
        setSeats(seats);
    }

    @Override
    public int getWheels() {
        return CAR_WHEELS_NUMBER;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }

    @Override
    public int getSeats() {
        return seats;
    }

    private void setSeats(int seats) {
        ValidationHelpers.validateIntRange(seats, CAR_SEATS_MIN, CAR_SEATS_MAX, CAR_SEATS_ERR);
        this.seats = seats;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().replace("Impl", ":") +
                System.lineSeparator() + super.toString() + System.lineSeparator() +
                "Seats: " + getSeats() + System.lineSeparator();
    }
}
