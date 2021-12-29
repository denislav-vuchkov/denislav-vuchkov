package Task.Management.System.utils;

import Task.Management.System.exceptions.InvalidNumberOfArguments;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.subcontracts.Nameable;

import java.util.List;

public class ValidationHelpers {

    public static final String ALREADY_EXIST = "%s with %d already exists in %s's list.";
    public static final String NOT_EXISTS = "%s with %d does not exist in %s's list.";

    public static final String NOT_IN_TEAM = "%s %s is not in team %s";
    public static final String ALREADY_IN_TEAM = "%s %s is already in team %s";

    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";

    public static void validateRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new InvalidUserInput(message);
        }
    }

    public static void validateCount(List<String> list, int expectedNumberOfParameters) {
        if (list.size() != expectedNumberOfParameters) {
            throw new InvalidNumberOfArguments(
                    String.format(INVALID_NUMBER_OF_ARGUMENTS, expectedNumberOfParameters, list.size()));
        }
    }


    public static <T extends Nameable> void entryNotAlreadyInList(T entry, List<T> list, String objectName) {
        String message = String.format(ALREADY_IN_TEAM, FormatHelpers.getType(entry), entry.getName(), objectName);
        entryNotAlreadyInList(entry, list, objectName, message);
    }

    public static <T extends Task> void entryNotAlreadyInList(T task, List<T> taskList, String objectName) {
        String message = String.format(ALREADY_EXIST, FormatHelpers.getType(task), task.getID(), objectName);
        entryNotAlreadyInList(task, taskList, objectName, message);
    }

    public static <T> void entryNotAlreadyInList(T element, List<T> list, String objectName, String message) {
        if (list.contains(element)) {
            throw new InvalidUserInput(message);
        }
    }

    public static <T extends Nameable> void entryExistInList(T entry, List<T> list, String objectName) {
        String message = String.format(NOT_IN_TEAM, FormatHelpers.getType(entry), entry.getName(), objectName);
        entryExistInList(entry, list, objectName, message);
    }

    public static <T extends Task> void entryExistInList(T task, List<T> taskList, String objectName) {
        String message = String.format(NOT_EXISTS, FormatHelpers.getType(task), task.getID(), objectName);
        entryExistInList(task, taskList, objectName, message);
    }

    public static <T> void entryExistInList(T element, List<T> list, String objectName, String message) {
        if (!list.contains(element)) {
            throw new InvalidUserInput(message);

        }
    }


}
