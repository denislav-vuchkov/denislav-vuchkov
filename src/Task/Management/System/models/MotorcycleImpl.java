package Task.Management.System.models;

import Task.Management.System.models.contracts.Motorcycle;
import Task.Management.System.models.enums.VehicleType;
import Task.Management.System.utils.ValidationHelpers;

import static java.lang.String.format;

public class MotorcycleImpl extends VehicleBase implements Motorcycle {

    public static final int MOTORCYCLE_WHEELS_NUMBER = 2;

    public static final int CATEGORY_LEN_MIN = 3;
    public static final int CATEGORY_LEN_MAX = 10;
    private static final String CATEGORY_LEN_ERR = format(
            "Category must be between %d and %d characters long!",
            CATEGORY_LEN_MIN,
            CATEGORY_LEN_MAX);

    private String category;

    public MotorcycleImpl(String make, String model, double price, String category) {
        super(make, model, price);
        setCategory(category);
    }

    @Override
    public int getWheels() {
        return MOTORCYCLE_WHEELS_NUMBER;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.MOTORCYCLE;
    }

    @Override
    public String getCategory() {
        return category;
    }

    private void setCategory(String category) {
        ValidationHelpers.validateIntRange(category.length(), CATEGORY_LEN_MIN, CATEGORY_LEN_MAX, CATEGORY_LEN_ERR);
        this.category = category;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().replace("Impl", ":") +
                System.lineSeparator() + super.toString() + System.lineSeparator() +
                "Category: " + getCategory() + System.lineSeparator();
    }
}
