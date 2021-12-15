package Task.Management.System.models;

import Task.Management.System.models.contracts.Comment;
import Task.Management.System.models.contracts.User;
import Task.Management.System.models.contracts.Vehicle;
import Task.Management.System.models.enums.UserRole;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class UserImpl implements User {

    public static final String USERNAME_REGEX_PATTERN = "^[A-Za-z0-9]+$";
    public static final String USERNAME_PATTERN_ERR = "Username contains invalid symbols!";
    public static final int USERNAME_LEN_MIN = 2;
    public static final int USERNAME_LEN_MAX = 20;
    public static final String USERNAME_LEN_ERR = format(
            "Username must be between %d and %d characters long!",
            USERNAME_LEN_MIN,
            USERNAME_LEN_MAX);

    public static final int LASTNAME_LEN_MIN = 2;
    public static final int LASTNAME_LEN_MAX = 20;
    public static final String LASTNAME_LEN_ERR = format(
            "Lastname must be between %s and %s characters long!",
            LASTNAME_LEN_MIN,
            LASTNAME_LEN_MAX);

    public static final int FIRSTNAME_LEN_MIN = 2;
    public static final int FIRSTNAME_LEN_MAX = 20;
    public static final String FIRSTNAME_LEN_ERR = format(
            "Firstname must be between %s and %s characters long!",
            FIRSTNAME_LEN_MIN,
            FIRSTNAME_LEN_MAX);

    public static final String PASSWORD_REGEX_PATTERN = "^[A-Za-z0-9@*_-]+$";
    public static final String PASSWORD_PATTERN_ERR = "Password contains invalid symbols!";
    public static final int PASSWORD_LEN_MIN = 5;
    public static final int PASSWORD_LEN_MAX = 30;
    public static final String PASSWORD_LEN_ERR = format(
            "Password must be between %s and %s characters long!",
            PASSWORD_LEN_MIN,
            PASSWORD_LEN_MAX);

    private static final int VIP_MAX_VEHICLES_TO_ADD = 5;
    private static final int NORMAL_ROLE_VEHICLE_LIMIT = 5;

    private final static String NOT_AN_VIP_USER_VEHICLES_ADD = "You are not VIP and cannot add more than %d vehicles!";
    private final static String ADMIN_CANNOT_ADD_VEHICLES = "You are an admin and therefore cannot add vehicles!";
    private static final String YOU_ARE_NOT_THE_AUTHOR = "You are not the author of the comment you are trying to remove!";

    private final static String USER_TO_STRING = "Username: %s, FullName: %s %s, Role: %s";
    private final static String USER_HEADER = "--USER %s--";
    private final static String NO_VEHICLES_HEADER = "--NO VEHICLES--";
    private final UserRole role;
    private final List<Vehicle> vehicles;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    public UserImpl(String username, String firstName, String lastName, String password, UserRole role) {
        setUsername(username);
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        this.role = role;
        vehicles = new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        ValidationHelpers.validateIntRange(username.length(), USERNAME_LEN_MIN, USERNAME_LEN_MAX, USERNAME_LEN_ERR);
        ValidationHelpers.validatePattern(username, USERNAME_REGEX_PATTERN, USERNAME_PATTERN_ERR);
        this.username = username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        ValidationHelpers.validateIntRange(firstName.length(), FIRSTNAME_LEN_MIN, FIRSTNAME_LEN_MAX, FIRSTNAME_LEN_ERR);
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        ValidationHelpers.validateIntRange(lastName.length(), LASTNAME_LEN_MIN, LASTNAME_LEN_MAX, LASTNAME_LEN_ERR);
        this.lastName = lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        ValidationHelpers.validateIntRange(password.length(), PASSWORD_LEN_MIN, PASSWORD_LEN_MAX, PASSWORD_LEN_ERR);
        ValidationHelpers.validatePattern(password, PASSWORD_REGEX_PATTERN, PASSWORD_PATTERN_ERR);
        this.password = password;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    @Override
    public boolean isAdmin() {
        return getRole() == UserRole.ADMIN;
    }

    @Override
    public String toString() {
        return String.format(USER_TO_STRING, getUsername(), getFirstName(), getLastName(), getRole()) + System.lineSeparator();
    }

    @Override
    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        if (isAdmin()) {
            throw new IllegalArgumentException(ADMIN_CANNOT_ADD_VEHICLES);
        }
        if (getRole().equals(UserRole.NORMAL) && vehicles.size() == 5) {
            throw new IllegalArgumentException(String.format(NOT_AN_VIP_USER_VEHICLES_ADD, NORMAL_ROLE_VEHICLE_LIMIT));
        }
        vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    @Override
    public void addComment(Comment commentToAdd, Vehicle vehicleToAddComment) {
        vehicleToAddComment.addComment(commentToAdd);
    }

    @Override
    public void removeComment(Comment commentToRemove, Vehicle vehicleToRemoveComment) {
        if (!commentToRemove.getAuthor().equals(getUsername())) {
            throw new IllegalArgumentException(YOU_ARE_NOT_THE_AUTHOR);
        }
        vehicleToRemoveComment.removeComment(commentToRemove);
    }

    @Override
    public String printVehicles() {
        StringBuilder output = new StringBuilder(
                String.format(USER_HEADER, getUsername())).append(System.lineSeparator());

        if (vehicles.isEmpty()) {
            output.append(NO_VEHICLES_HEADER).append(System.lineSeparator());

        } else {
            for (int i = 0; i < vehicles.size(); i++) {
                Vehicle v = vehicles.get(i);
                output.append(String.format("%d. %s", (i + 1), v));

                if (v.getComments().isEmpty()) {
                    output.append(ModelConstants.NO_COMMENTS_HEADER).append(System.lineSeparator());

                } else {
                    output.append(ModelConstants.COMMENTS_HEADER).append(System.lineSeparator());
                    v.getComments().forEach(comment -> output.append(comment.toString()));
                    output.append(ModelConstants.COMMENTS_HEADER).append(System.lineSeparator());

                }
            }

        }
        return output.toString().trim();
    }
}
