package Task.Management.System.models.contracts;

import Task.Management.System.models.enums.VehicleType;

public interface Vehicle extends Commentable,Priceable  {

    int getWheels();

    VehicleType getType();

    String getMake();

    String getModel();

    void addComment(Comment comment);

    void removeComment(Comment comment);

}
