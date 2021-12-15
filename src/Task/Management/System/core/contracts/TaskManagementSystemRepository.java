package Task.Management.System.core.contracts;

import Task.Management.System.models.contracts.*;
import Task.Management.System.models.enums.UserRole;

import java.util.List;

public interface TaskManagementSystemRepository {

    List<User> getUsers();

    User getLoggedInUser();

    void addUser(User userToAdd);

    User findUserByUsername(String username);

    Car createCar(String make, String model, double price, int seats);

    Motorcycle createMotorcycle(String make, String model, double price, String category);

    Truck createTruck(String make, String model, double price, int weightCapacity);

    User createUser(String username, String firstName, String lastName, String password, UserRole userRole);

    Comment createComment(String content, String author);

    boolean hasLoggedInUser();

    void login(User user);

    void logout();
}
