package Task.Management.System.models;

import Task.Management.System.models.contracts.Comment;
import Task.Management.System.models.contracts.Vehicle;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.ModelConstants.*;

public abstract class VehicleBase implements Vehicle {

    private final List<Comment> comments;
    private String make;
    private String model;
    private double price;

    public VehicleBase(String make, String model, double price) {
        setMake(make);
        setModel(model);
        setPrice(price);
        comments = new ArrayList<>();
    }

    @Override
    public String getMake() {
        return make;
    }

    private void setMake(String make) {
        ValidationHelpers.validateIntRange(make.length(), MAKE_NAME_LEN_MIN, MAKE_NAME_LEN_MAX, MAKE_NAME_LEN_ERR);
        this.make = make;
    }

    @Override
    public String getModel() {
        return model;
    }

    private void setModel(String model) {
        ValidationHelpers.validateIntRange(model.length(), MODEL_NAME_LEN_MIN, MODEL_NAME_LEN_MAX, MODEL_NAME_LEN_ERR);
        this.model = model;
    }

    @Override
    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        ValidationHelpers.validateDecimalRange(price, PRICE_VAL_MIN, PRICE_VAL_MAX, PRICE_VAL_ERR);
        this.price = price;
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public String toString() {
        return String.format(
                "Make: %s%n" + "Model: %s%n" + "Wheels: %d%n" + "Price: $%.0f",
                getMake(), getModel(), getWheels(), getPrice());
    }
}
