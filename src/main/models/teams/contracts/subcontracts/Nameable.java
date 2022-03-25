package main.models.teams.contracts.subcontracts;

public interface Nameable {

    int NAME_MIN_LEN = 5;
    int NAME_MAX_LEN = 15;

    String INVALID_NAME = String.format("Name must be between %d and %d symbols.", NAME_MIN_LEN, NAME_MAX_LEN);

    String getName();

}
