package Task.Management.System.models.teams.contracts.subcontracts;

public interface Nameable {

    int NAME_MIN_LENGTH = 5;
    int NAME_MAX_LENGTH = 15;

    String INVALID_NAME_MESSAGE = String.format(
            "Name must be between %d and %d symbols.",
            NAME_MIN_LENGTH,
            NAME_MAX_LENGTH);

    String getName();

}
