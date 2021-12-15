package Task.Management.System.models;

import Task.Management.System.models.contracts.Truck;
import Task.Management.System.models.enums.VehicleType;
import Task.Management.System.utils.ValidationHelpers;

import static java.lang.String.format;

public class TruckImpl extends VehicleBase implements Truck {

    public static final int TRUCK_WHEELS_NUMBER = 8;

    public static final int WEIGHT_CAP_MIN = 1;
    public static final int WEIGHT_CAP_MAX = 100;
    private static final String WEIGHT_CAP_ERR = format(
            "Weight capacity must be between %d and %d!",
            WEIGHT_CAP_MIN,
            WEIGHT_CAP_MAX);

    private int weightCapacity;

    public TruckImpl(String make, String model, double price, int weightCapacity) {
        super(make, model, price);
        setWeightCapacity(weightCapacity);
    }

    @Override
    public int getWheels() {
        return TRUCK_WHEELS_NUMBER;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.TRUCK;
    }

    @Override
    public int getWeightCapacity() {
        return weightCapacity;
    }

    private void setWeightCapacity(int weightCapacity) {
        ValidationHelpers.validateIntRange(weightCapacity, WEIGHT_CAP_MIN, WEIGHT_CAP_MAX, WEIGHT_CAP_ERR);
        this.weightCapacity = weightCapacity;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName().replace("Impl", ":") +
                System.lineSeparator() + super.toString() + System.lineSeparator() +
                "Weight Capacity: " + getWeightCapacity() + "t" + System.lineSeparator();
    }
}
